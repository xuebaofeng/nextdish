package bf.nextdish;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import static bf.nextdish.NextdishApplication.getConnection;

public class InsertSms {

    private static Connection connection;

    public static void main(String[] args) throws Exception {
        getConnection();
        String sms = "before 09/29 , a total of $182.17";

        double amount = 534.42;
        amount = Double.parseDouble(sms.split("\\$")[1].split(",")[0]);
        String date = new SimpleDateFormat("yyyy").format(new Date()) + sms.split("before ")[1].split(" ")[0].replace("/", "");
        System.out.println(amount);
        System.out.println(date);
        insertSms(amount, date);
    }


    private static void insertSms(double amount, String date) throws Exception {

        Connection c = getConnection();
        PreparedStatement stmt = c.prepareStatement("INSERT INTO payment_sms (amount, date) VALUES(?, ?);");
        int index=0;
        stmt.setObject(++index, amount);
        stmt.setObject(++index, date);

        stmt.execute();
        stmt.close();
    }


}
