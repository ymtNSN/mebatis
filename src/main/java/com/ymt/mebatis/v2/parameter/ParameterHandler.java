package com.ymt.mebatis.v2.parameter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Description: TODO
 * @Author: yangmingtian
 * @Date: 2019/5/19
 */
public class ParameterHandler {
    private PreparedStatement preparedStatement;

    public ParameterHandler(PreparedStatement preparedStatement) {
        this.preparedStatement = preparedStatement;
    }

    /**
     * 从方法中获取参数，遍历设置SQL中的？占位符
     *
     * @param parameters
     */
    public void setParameters(Object[] parameters) {
        try {
            for (int i = 0; i < parameters.length; i++) {
                int k = i + 1;
                if (parameters[i] instanceof Integer) {
                    preparedStatement.setInt(k, (Integer) parameters[i]);
                } else if (parameters[i] instanceof Long) {
                    preparedStatement.setLong(k, (Long) parameters[i]);
                } else if (parameters[i] instanceof String) {
                    preparedStatement.setString(k, String.valueOf(parameters[i]));
                } else if (parameters[i] instanceof Boolean) {
                    preparedStatement.setBoolean(k, (Boolean) parameters[i]);
                } else {
                    preparedStatement.setString(k, String.valueOf(parameters[i]));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
