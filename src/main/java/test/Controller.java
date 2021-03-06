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

	
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response clientCredentials(String dados) throws SQLException, MalformedClaimException, JoseException {
		System.out.println("dados recebidos no post: "+dados);
		JSONObject newDados = new JSONObject(dados);
		String login = (String) newDados.get("LOGIN");
		String password = (String) newDados.get("PASSWORD");
		String input = login+"/"+password; 

		DBmanager dbm = new DBmanager();
		dbm.selectRecordsFromTable(login,password);

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