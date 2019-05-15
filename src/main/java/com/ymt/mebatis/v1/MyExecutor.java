package com.ymt.mebatis.v1;

import com.ymt.mebatis.v1.mapper.RolesEntity;

import java.sql.*;

/**
 * @Description TODO
 * @Author yangmingtian
 * @Date 2019/5/15
 */
public class MyExecutor {

    public <T> T query(String sql, Object paramater) {
        Connection conn = null;
        Statement stmt = null;
        RolesEntity entity = new RolesEntity();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://rm-uf6q7l04721ze2mbfro.mysql.rds.aliyuncs.com:3306/change_test?useSSL=false&serverTimezone=UTC"
                    , "change_dev", "xP6FQ4ajw79W");
            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(String.format(sql, paramater));

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int resource_id = rs.getInt("resource_id");
                String resource_type = rs.getString("resource_type");
                Timestamp created_at = rs.getTimestamp("created_at");
                Timestamp update_at = rs.getTimestamp("updated_at");
                entity.setId(id);
                entity.setName(name);
                entity.setResourceId(resource_id);
                entity.setResourceType(resource_type);
                entity.setCreatedAt(created_at);
                entity.setUpdatedAt(update_at);
            }
            System.out.println(entity);

            rs.close();
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return (T) entity;
    }
}