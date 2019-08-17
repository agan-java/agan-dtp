package com.agan.dtp.xa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * @author 阿甘
 * @see https://study.163.com/course/courseMain.htm?courseId=1209367806&share=2&shareId=1016671292
 * @version 1.0
 * 注：如有任何疑问欢迎加入QQ群977438372 进行讨论
 */
public class AP {

    public Connection getRmAccountConn(){

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.0.138:3307/xa_account?characterEncoding=utf8&useSSL=false&autoReconnect=true", "root", "agan");
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Connection getRmRedConn(){
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.0.138:3308/xa_red_account?characterEncoding=utf8&useSSL=false&autoReconnect=true", "root", "agan");
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}

