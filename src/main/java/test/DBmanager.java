package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.lang.JoseException;

public class DBmanager {

	public DBmanager() {}
	
	//DB conection constants
	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_CONNECTION = "jdbc:mysql://193.137.7.39:3306/ei2_201718";
	private static final String DB_USER = "ei2_201718";
	private static final String DB_PASSWORD = "password";

	//DB conection methods
	public static void selectRecordsFromTable(String login, String passwd) throws SQLException, MalformedClaimException, JoseException {

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String selectSQL = "SELECT count(*) FROM UTILIZADORES_BEEP WHERE (EMAIL = ? AND PASSWORD = ?)";

		try {
			dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement(selectSQL);
			preparedStatement.setString(1,login);
			preparedStatement.setString(2, passwd);

			// execute select SQL statement
			ResultSet rs = preparedStatement.executeQuery();
			System.out.println("User Check Query executed");

			//if user exists create a JWT token!
			if (rs.next()) {
				System.out.println("user does exist! creating the token...");
				String userPassReceived=login+passwd;
				String tokenReceived= Security.generateToken(userPassReceived);
				Security.validateToken(tokenReceived);
			}

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}

	}

	public static Connection getDBConnection() {

		Connection dbConnection = null;

		try {

			Class.forName(DB_DRIVER);

		} catch (ClassNotFoundException e) {

			System.out.println(e.getMessage());

		}

		try {

			dbConnection = DriverManager.getConnection(
					DB_CONNECTION, DB_USER,DB_PASSWORD);
			return dbConnection;

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}

		return dbConnection;

	}

}
