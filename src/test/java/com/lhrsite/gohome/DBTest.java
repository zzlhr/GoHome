package com.lhrsite.gohome;


import java.sql.SQLException;

public class DBTest {
    public static void main(String[] args) {
//        try {
//            System.out.println(DB.getTables());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }


        System.out.println(DB.tableNameToJavaClassName("article_comment"));

    }

}
