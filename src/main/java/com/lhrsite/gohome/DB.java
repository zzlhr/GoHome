package com.lhrsite.gohome;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DB {
    private static Connection connection;
    private static String url;

    static {
        url = "jdbc:mysql://{{host}}:{{port}}/{{database}}?useUnicode=true&characterEncoding=UTF-8";
        String host = Config.getValue("db.host") == null ? "" : Config.getValue("db.host");
        String port = Config.getValue("db.port") == null ? "" : Config.getValue("db.port");
        String database = Config.getValue("db.database");
        if (database == null) {
            throw new RuntimeException("未设置database");
        }
        url = url.replace("{{host}}", host)
                .replace("{{port}}", port)
                .replace("{{database}}", database);
        String username = Config.getValue("db.username");
        String password = Config.getValue("db.password");
        if (username == null || password == null) {
            throw new RuntimeException("数据库用户名或密码为空");
        }
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException("连接数据库失败");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("找不到mysql驱动");
        }
    }

    public static List<String> getTables() throws SQLException {
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        ResultSet tables = databaseMetaData.getTables(null, null, "%", null);
        ArrayList<String> tablesList = new ArrayList<String>();
        while (tables.next()) {
            tablesList.add(tables.getString("TABLE_NAME"));
        }
        return tablesList;
    }

    /**
     * 表明转换为java类名
     *
     * @param tableName 表明
     * @return java类名
     */
    // TODO: 考虑到特殊符号(非英文)处理起来不太好用，比如中文占用两个字节，处理字节方式就不好用了
    // TODO: 如果表名结尾有_就无法处理了
    public static String tableNameToJavaClassName(String tableName) {
        // 首字母大写
        String initial = tableName.substring(0, 1);
        tableName = initial.toUpperCase() + tableName.substring(1);
        // 下划线后一位大写
        String className = "";

        char[] tableNameChars = tableName.toCharArray();
        for (int i = 0; i < tableNameChars.length; i++) {
            if (tableNameChars[i] == "_".toCharArray()[0]) {
                // 如果当前字符是下划线，将后一位转换为大写
                String upCharStr = String.valueOf(tableNameChars[i + 1]).toUpperCase();
                tableNameChars[i + 1] = upCharStr.toCharArray()[0];
                continue;
            }
            className += String.valueOf(tableNameChars[i]);
        }
        return className;
    }




}
