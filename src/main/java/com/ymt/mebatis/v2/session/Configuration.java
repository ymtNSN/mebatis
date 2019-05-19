package com.ymt.mebatis.v2.session;

import com.ymt.mebatis.v2.TestMybatis;
import com.ymt.mebatis.v2.annotation.Entity;
import com.ymt.mebatis.v2.annotation.Select;
import com.ymt.mebatis.v2.binding.MapperRegistry;
import com.ymt.mebatis.v2.executor.CachingExecutor;
import com.ymt.mebatis.v2.executor.Executor;
import com.ymt.mebatis.v2.executor.SimpleExecutor;
import com.ymt.mebatis.v2.plugin.IntercepterChain;
import com.ymt.mebatis.v2.plugin.Interceptor;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @Description TODO
 * @Author yangmingtian
 * @Date 2019/5/15
 */
public class Configuration {
    public static final ResourceBundle sqlMappings; //SQL映射关系配置，使用注解时不用重复配置
    public static final ResourceBundle properties; //全局配置
    public static final MapperRegistry MAPPER_REGISTRY = new MapperRegistry();//维护接口与工厂类关系
    public static final Map<String, String> mappedStatements = new HashMap<>();// 维护接口方法与SQL关系

    private IntercepterChain intercepterChain = new IntercepterChain();//插件
    private List<Class<?>> mappList = new ArrayList<Class<?>>();//所有Mapper接口
    private List<String> classPaths = new ArrayList<String>();//类所有文件

    static {
        sqlMappings = ResourceBundle.getBundle("sql");
        properties = ResourceBundle.getBundle("mybatis");
    }

    /**
     * 初始化时解析全局配置文件
     */
    public Configuration() {
        // 在properties和注解中重复配置SQL会覆盖
        // 1、解析sql.properties
        for (String key : sqlMappings.keySet()) {
            Class mapper = null;
            String statement = null;
            String pojoString = null;
            Class pojo = null;
            statement = sqlMappings.getString(key).split("--")[0];
            pojoString = sqlMappings.getString(key).split("--")[1];

            try {
                mapper = Class.forName(key.substring(0, key.lastIndexOf(".")));
                pojo = Class.forName(pojoString);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            // 接口与返回实体的实体关系
            MAPPER_REGISTRY.addMapper(mapper, pojo);
            // 接口方法与sql关系
            mappedStatements.put(key, statement);
        }

        // 2、解析mapper接口配置，扫描注册
        String mapperPath = properties.getString("mapper.path");
        scanPackage(mapperPath);
        for (Class<?> mapper : mappList) {
            parsingClass(mapper);
        }
        // 3、解析插件，可配置多个
        String pluginPathValue = properties.getString("plugin.path");
        String[] pluginPaths = pluginPathValue.split(",");
        if (pluginPaths != null) {
            // 将插件添加到interceptorChain中
            for (String plugin : pluginPaths) {
                Interceptor interceptor = null;
                try {
                    interceptor = (Interceptor) Class.forName(plugin).newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                intercepterChain.addInterceptor(interceptor);
            }
        }
    }

    /**
     * 解析Mapper接口上配置的注解（Sql语句）
     *
     * @param mapper
     */
    private void parsingClass(Class<?> mapper) {
        // 解析类上的注解
        if (mapper.isAnnotationPresent(Entity.class)) {
            for (Annotation annotation : mapper.getAnnotations()) {
                if (annotation.annotationType().equals(Entity.class)) {
                    // 注册接口与实体类的映射关系
                    MAPPER_REGISTRY.addMapper(mapper, ((Entity) annotation).value());
                }
            }
        }

        //解析方法上的注解
        Method[] methods = mapper.getMethods();
        for (Method method : methods) {
            // 解析@Select注解的语句
            if (method.isAnnotationPresent(Select.class)) {
                for (Annotation annotation : method.getDeclaredAnnotations()) {
                    if (annotation.annotationType().equals(Select.class)) {
                        // 注册接口类型+方法名和sql语句的映射关系
                        String statement = method.getDeclaringClass().getName() + "." + method.getName();
                        mappedStatements.put(statement, ((Select) annotation).value());
                    }
                }
            }
        }
    }

    /**
     * 根据全局配置文件的Mapper接口路径，扫描所有接口
     *
     * @param mapperPath
     */
    private void scanPackage(String mapperPath) {
        String classPath = TestMybatis.class.getResource("/").getPath();
        mapperPath = mapperPath.replace(".", File.separator);
        String mainPath = classPath + mapperPath;
        doPath(new File(mainPath));
        for (String className : classPaths) {
            className = className.replace(classPath.replace("/", "\\").replaceFirst("\\\\", ""), "").replace("\\", ".").replace(".class", "");
            Class<?> clazz = null;
            try {
                clazz = Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (clazz.isInterface()) {
                mappList.add(clazz);
            }
        }
    }

    /**
     * 获取文件或文件夹下所有类.class
     *
     * @param file
     */
    private void doPath(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f1 : files) {
                doPath(f1);
            }
        } else {
            if (file.getName().endsWith(".class")) {
                classPaths.add(file.getPath());
            }
        }
    }


    public String getMapperStatement(String id) {
        return mappedStatements.get(id);
    }

    /**
     * 创建执行器，当开启缓存时使用缓存装饰
     * 当配置插件时，使用插件代理
     *
     * @return
     */
    public Executor newExecutor() {
        Executor executor = null;
        if (properties.getString("cache.enabled").equals("true")) {
            executor = new CachingExecutor(new SimpleExecutor());
        } else {
            executor = new SimpleExecutor();
        }

        if (intercepterChain.hasPlugin()) {
            return (Executor) intercepterChain.pluginAll(executor);
        }
        return executor;
    }

    public <T> T getMapper(Class<T> clazz, DefaultSqlSession sqlSession) {
        return MAPPER_REGISTRY.getMapper(clazz, sqlSession);
    }

    public boolean hasStatement(String statementId) {
        return mappedStatements.containsKey(statementId);
    }
}
