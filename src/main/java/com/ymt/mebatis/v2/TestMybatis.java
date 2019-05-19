package com.ymt.mebatis.v2;

import com.ymt.mebatis.v2.mapper.RolesEntity;
import com.ymt.mebatis.v2.mapper.RolesMapper;
import com.ymt.mebatis.v2.session.DefaultSqlSession;
import com.ymt.mebatis.v2.session.SqlSessionFactory;

/**
 * @Description TODO
 * @Author yangmingtian
 * @Date 2019/5/15
 */
public class TestMybatis {

    public static void main(String[] args) {
        SqlSessionFactory factory = new SqlSessionFactory();
        DefaultSqlSession sqlSession = factory.build().openSqlSession();
        RolesMapper mapper = sqlSession.getMapper(RolesMapper.class);
        RolesEntity rolesEntity = mapper.selectById(1);
        System.out.println("第一次查询：" + rolesEntity);
        System.out.println();
        rolesEntity = mapper.selectById(1);
        System.out.println("第二次查询：" + rolesEntity);

    }
}
