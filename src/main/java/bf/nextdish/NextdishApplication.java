package bf.nextdish;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class NextdishApplication {

    private static Connection connection;

    public static void main(String[] args) throws Exception {
        System.out.println("test");
        getConnection();
        List<String> lines = Files.readAllLines(Path.of("payment.txt"));
        for (String line : lines) {
            if (line.startsWith("Date") || line.startsWith("日期"))
                continue;
            String[] columns = line.split("\\t");
            for (String column : columns) {
                System.out.println(column);
            }
            if (exist(columns))
                continue;
            insertPayment(columns);
        }
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
            stmt.setObject(++index, column);
        }
        stmt.execute();
        stmt.close();
        c.close();
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
