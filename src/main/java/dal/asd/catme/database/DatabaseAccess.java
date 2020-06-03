package dal.asd.catme.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DatabaseAccess {
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	private int result;
	@Value("${spring.database.test.url}")
	String url;
	@Value("${spring.database.test.user}")
	String user;
	@Value("${spring.database.test.password}")
	String password;
	public Connection getConnection()  {
		try {
//			Class.forName("com.mysql.jdbc.driver");
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			connection = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public ResultSet executeQuery(String query) {
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}

			return resultSet;
	}

	public int executeUpdate(String query) {
		try {
			statement = connection.createStatement();
			result = statement.executeUpdate(query);
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
			return result;
	}

}
