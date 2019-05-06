package com.agan.dtp.xa;

import com.mysql.jdbc.jdbc2.optional.MysqlXAConnection;
import com.mysql.jdbc.jdbc2.optional.MysqlXid;

import javax.sql.XAConnection;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TM {

    public  void execute(Connection accountConn,Connection redConn) throws SQLException {
        //true表示打印XA语句,，用于调试
        boolean logXaCommands = true;
        // 获得资源管理器操作接口实例 RM1
        XAConnection xaConn1 = new MysqlXAConnection((com.mysql.jdbc.ConnectionImpl) accountConn, logXaCommands);
        XAResource rm1 = xaConn1.getXAResource();

        // 获得资源管理器操作接口实例 RM2
        XAConnection xaConn2 = new MysqlXAConnection((com.mysql.jdbc.ConnectionImpl) redConn, logXaCommands);
        XAResource rm2 = xaConn2.getXAResource();
        // AP请求TM执行一个分布式事务，TM生成全局事务id
        byte[] globalid = "agan12345".getBytes();
        int formatId = 1;
        try {
            // ==============分别执行RM1和RM2上的事务分支====================
            // TM 把rm1上的事务分支id，注册到全局事务id
            byte[] bqual1 = "b00001".getBytes();
            Xid xid1 = new MysqlXid(globalid, bqual1, formatId);
            // start...end  开始和结束rm1本地事务
            rm1.start(xid1, XAResource.TMNOFLAGS);
            //模拟购买一件物品，用余额100元
            String sql="update capital_account set balance_amount=balance_amount-100 where user_id=1";
            PreparedStatement ps1 = accountConn.prepareStatement(sql);
            ps1.execute();
            rm1.end(xid1, XAResource.TMSUCCESS);

            // TM 把rm2上的事务分支id，注册到全局事务id
            byte[] bqual2 = "b00002".getBytes();
            Xid xid2 = new MysqlXid(globalid, bqual2, formatId);
            // start...end  开始和结束rm2本地事务
            rm2.start(xid2, XAResource.TMNOFLAGS);
            //模拟购买一件物品，用红包8元
            sql="update red_packet_account set balance_amount=balance_amount-8 where user_id=1";
            PreparedStatement ps2 = redConn.prepareStatement(sql);
            ps2.execute();
            rm2.end(xid2, XAResource.TMSUCCESS);
            // ===================两阶段提交================================
            // phase1：询问所有的RM 准备提交事务分支
            int rm1_prepare = rm1.prepare(xid1);
            int rm2_prepare = rm2.prepare(xid2);
            // phase2：提交所有事务分支
            boolean onePhase = false; //TM判断有2个事务分支，所以不能优化为一阶段提交
            if (rm1_prepare == XAResource.XA_OK
                    && rm2_prepare == XAResource.XA_OK
            ) {//所有事务分支都prepare成功，提交所有事务分支
                rm1.commit(xid1, onePhase);
                rm2.commit(xid2, onePhase);
            } else {//如果有事务分支没有成功，则回滚
                rm1.rollback(xid1);
                rm1.rollback(xid2);
            }
        } catch (XAException e) {
            // 如果出现异常，也要进行回滚
            e.printStackTrace();
        }
    }
}
