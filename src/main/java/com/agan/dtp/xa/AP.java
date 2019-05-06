package com.agan.dtp.xa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AP {

    public Connection getRmAccountConn(){

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://47.106.128.80:5555/xa_account", "root", "lsb18");
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Connection getRmRedConn(){
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://47.106.128.80:5555/xa_red_account", "root", "lsb18");
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}

