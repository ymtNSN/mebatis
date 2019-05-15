package com.ymt.mebatis.v1;

import com.ymt.mebatis.v1.mapper.RoleMapper;
import com.ymt.mebatis.v1.mapper.RolesEntity;

/**
 * @Description TODO
 * @Author yangmingtian
 * @Date 2019/5/15
 */
public class MebatisBoot {
    public static void main(String[] args) {
        MySqlSession sqlSession = new MySqlSession(new MyConfiguration(), new MyExecutor());
//        RolesEntity role = sqlSession.selectOne("com.ymt.mebatis.v1.mapper.RoleMapper.selectById", 2);
        RoleMapper mapper = sqlSession.getMapper(RoleMapper.class);
        RolesEntity role = mapper.selectById(2);
        System.out.println(role);
    }
}
