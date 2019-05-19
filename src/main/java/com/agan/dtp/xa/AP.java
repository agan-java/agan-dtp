package com.agan.dtp.xa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

