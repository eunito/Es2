package test;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;

import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.lang.JoseException;
import org.json.JSONArray;
import org.json.JSONObject;

@Path("/auth")
public class Controller {
	@Context HttpServletRequest req; 


	//DB conection constants
	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_CONNECTION = "jdbc:mysql://193.137.7.39:3306/ei2_201718";
	private static final String DB_USER = "ei2_201718";
	private static final String DB_PASSWORD = "password";


	//DB conection methods
	private static void selectRecordsFromTable(String login, String passwd) throws SQLException, MalformedClaimException, JoseException {

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

	private static Connection getDBConnection() {

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

	//method for JWT token request



	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response clientCredentials(String dados) throws SQLException, MalformedClaimException, JoseException {
		System.out.println("dados recebidos no post: "+dados);
		JSONObject newDados = new JSONObject(dados);
		String login = (String) newDados.get("LOGIN");
		String password = (String) newDados.get("PASSWORD");
		String input = login+"/"+password; 

		selectRecordsFromTable(login,password);

		return Response.status(200).entity(input.toString()).build();
	}

	private Response getClientDetails(HttpServletRequest req, Integer idClient) {

		JSONObject clients = new JSONObject();


		switch(idClient) {
		case 1 : 
			clients.put("id", idClient);
			clients.put("name", "José das Couves");
			clients.put("address", "Viseu");
			clients.put("ssn", "211888999");
			clients.put("job", "ajudante");
			break;
		case 2 : 
			clients.put("id", idClient);
			clients.put("name", "Maria da Sé");
			clients.put("address", "Porto");
			break;
		case 3 : 
			String s = "<data><id>3</id><name>João dos Coices</name><address>Alentejo</address></data>";
			return Response.status(200).entity(s).build();
		default:
			return Response.status(404).entity("Not found").build();
		}

		return Response.status(200).entity(clients.toString()).build();
	}
}