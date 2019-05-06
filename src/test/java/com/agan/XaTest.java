import com.agan.dtp.xa.AP;
import com.agan.dtp.xa.TM;
import org.junit.Test;

import java.sql.SQLException;

public class XaTest {

    AP ap=new AP();
    TM tm=new TM();


    @Test
    public void test(){
        try {
            tm.execute(ap.getRmAccountConn(),ap.getRmRedConn());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
