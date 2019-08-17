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
/**
 * @author 阿甘
 * @see https://study.163.com/course/courseMain.htm?courseId=1209367806&share=2&shareId=1016671292
 * @version 1.0
 * 注：如有任何疑问欢迎加入QQ群977438372 进行讨论
 */
public class TM {

    public  void execute(Connection accountConn,Connection redConn) throws SQLException {
        //打印XA的事务日志 true 代表打印
        boolean logXaCommands = true;
        //获取RM1 的接口实例
        XAConnection xaConn1 = new MysqlXAConnection((com.mysql.jdbc.ConnectionImpl) accountConn, logXaCommands);
        XAResource rm1 = xaConn1.getXAResource();

        //获取RM2 的接口实例
        XAConnection xaConn2 = new MysqlXAConnection((com.mysql.jdbc.ConnectionImpl) redConn, logXaCommands);
        XAResource rm2 = xaConn2.getXAResource();


        //生成一个全局事务ID
        byte[] globalid = "agan12345".getBytes();
        int formatId = 1;
        try {
            //TM 把rm1的事务分支id，注册到全局事务ID
            byte[] bqual1 = "b00001".getBytes();
            Xid xid1 = new MysqlXid(globalid, bqual1, formatId);
            //start...end 开始 结束 rm1的本地事务
            rm1.start(xid1, XAResource.TMNOFLAGS);
            //模拟购物买一个物品，用余额支付90㡰
            String sql="update capital_account set balance_amount=balance_amount-90 where user_id=1";
            PreparedStatement ps1 = accountConn.prepareStatement(sql);
            ps1.execute();
            rm1.end(xid1, XAResource.TMSUCCESS);


            //TM 把rm2的事务分支id，注册到全局事务ID
            byte[] bqual2 = "b00002".getBytes();
            Xid xid2 = new MysqlXid(globalid, bqual2, formatId);
            //start...end 开始 结束 rm2的本地事务
            rm2.start(xid2, XAResource.TMNOFLAGS);
            //模拟购物一个物品，用红包支付10元。
            sql="update red_packet_account set balance_amount=balance_amount-10 where user_id=1";
            PreparedStatement ps2 = redConn.prepareStatement(sql);
            ps2.execute();
            rm2.end(xid2, XAResource.TMSUCCESS);

            //2阶段提交中得第一个阶段：准备提交
            int rm1_prepare = rm1.prepare(xid1);
            int rm2_prepare = rm2.prepare(xid2);

            //2阶段提交中得第二个阶段：真正提交
            if (rm1_prepare == XAResource.XA_OK && rm2_prepare == XAResource.XA_OK) {

                boolean onePhase = false;
                rm1.commit(xid1, onePhase);//提交事务
                rm2.commit(xid2, onePhase);//提交事务
            } else {//全部回滚
                rm1.rollback(xid1);
                rm1.rollback(xid2);
            }
        } catch (XAException e) {
            // 如果出现异常，也要进行回滚
            e.printStackTrace();
        }
    }
}
