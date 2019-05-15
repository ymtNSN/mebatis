package com.ymt.mebatis.v2.session;

import com.ymt.mebatis.v2.binding.MapperRegistry;
import com.ymt.mebatis.v2.executor.Executor;
import com.ymt.mebatis.v2.plugin.IntercepterChain;

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
                mapper = Class.forName(key.substring(0,key.lastIndexOf(".")));
                pojo = Class.forName(pojoString);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }

    }


    public Executor newExecutor() {
        return null;
    }
}
