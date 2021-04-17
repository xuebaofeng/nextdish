package bf.nextdish;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;

public class NextdishApplication {

	public static void main(String[] args) throws Exception {
		System.out.println("test");
		getConnection();
		List<String> lines = Files.readAllLines(Path.of("payment.txt"));
		for (String line : lines) {
			if(line.startsWith("Date"))
				continue;
			String[] columns = line.split("\\t");
			for (String column : columns) {
				System.out.println(column);
			}
			insertPayment(columns);
		}
	}

	private static Connection getConnection() throws Exception {
		Class.forName("org.sqlite.JDBC");
		return DriverManager.getConnection("jdbc:sqlite:nextdish.db");
	}
	static void insertPayment(String[] columns) throws Exception {
		Connection c = getConnection();
		PreparedStatement stmt = c.prepareStatement("INSERT INTO payment (date, note, amount, status) VALUES(?, ?, ?, ?)");
		int index = 0;
		for (String column : columns) {
			if(column.startsWith("$")){
				stmt.setDouble(++index, Double.parseDouble(column.substring(1)));
				continue;
			}
			stmt.setObject(++index, column);
		}
		stmt.execute();
		stmt.close();
		c.close();
	}
}
