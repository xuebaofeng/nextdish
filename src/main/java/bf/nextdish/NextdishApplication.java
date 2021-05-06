package bf.nextdish;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class NextdishApplication {

    private static Connection connection;

    public static void main(String[] args) throws Exception {
        System.out.println("test");
        getConnection();
        Document doc = PaymentFetcher.urlToDoc();
        Elements list = doc.select("#credit-table > tbody");
        for (Element row : list.select("tr")) {
            System.out.println(row.text());
            Elements tds = row.select("td");
            String[] columns = toColumns(tds);

            if (exist(columns))
                continue;
            insertPayment(columns);
        }
    }

    private static String[] toColumns(Elements tds) {
        String[] columns = new String[tds.size()];
        int index = 0;
        for (Element td : tds) {
            columns[index++] = td.text();
        }
        return columns;
    }

    private static void insertPayment(String[] columns) throws Exception {
        Connection c = getConnection();
        PreparedStatement stmt = c.prepareStatement("INSERT INTO payment (date, note, amount, status) VALUES(?, ?, ?, ?)");
        int index = 0;
        for (String column : columns) {
            if (column.startsWith("$")) {
                stmt.setDouble(++index, Double.parseDouble(column.substring(1)));
                continue;
            }
            if(column.equals("已付款"))
                column = "Paid";
            stmt.setObject(++index, column);
        }
        stmt.execute();
        stmt.close();
    }

    private static Connection getConnection() throws Exception {
        Class.forName("org.sqlite.JDBC");
        if (connection != null)
            return connection;

        connection = DriverManager.getConnection("jdbc:sqlite:nextdish.db");
        return connection;
    }

    static boolean exist(String[] columns) throws Exception {
        Connection c = getConnection();
        PreparedStatement stmt = c.prepareStatement("select count(*) from payment p  where p.date =? and p.note =?");
        int index = 0;
        stmt.setObject(++index, columns[0]);
        stmt.setObject(++index, columns[1]);
        ResultSet resultSet = stmt.executeQuery();
        int count = resultSet.getInt(1);

        stmt.close();
        return count >= 1;
    }
}
