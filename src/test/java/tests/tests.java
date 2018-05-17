package tests;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;

import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.lang.JoseException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.*;


import test.Application;
import test.DBmanager;
import test.Security;

public class tests {
	private static Thread t;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		t = new Thread()
		{
			public void run(){
				try {
					Application.main(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		t.start();
		Thread.sleep(3000);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		t.interrupt();
	}


	//Testes WB


	//Testes BB
	@Test
	@DisplayName("Teste ao selectRecords - OK")
	public void test_OK_selectRecordsFromTable() throws MalformedClaimException, SQLException, JoseException {
		assertEquals(1,DBmanager.selectRecordsFromTable("Amaro.Miguel@gmail.com","D3C1455E3BBC48408904FCC0A0A1DBAB"));
	}

	@Test
	@DisplayName("Teste ao selectRecords - NOK")
	public void test_NOK_selectRecordsFromTable() throws MalformedClaimException, SQLException, JoseException {
		assertEquals(0,DBmanager.selectRecordsFromTable("ricardo@sapo.pt","D3C1455E3BBC48408904FCC0A0A1DBAB"));
	}

	@Test
	@DisplayName("Teste ao selectRecords - null string")
	public void test_NOK_nulls_selectRecordsFromTable() throws MalformedClaimException, SQLException, JoseException {
		assertEquals(0,DBmanager.selectRecordsFromTable(null,"D3C1455E3BBC48408904FCC0A0A1DBAB"));
	}


	@Test
	@DisplayName("Teste ao selectRecords - NOK1")
	public void test_NOK1_selectRecordsFromTable() throws MalformedClaimException, SQLException, JoseException {
		assertEquals(0,DBmanager.selectRecordsFromTable("Amaro.Miguel@gmail.com","D3C1455"));
	}	


	@Test
	@DisplayName("Teste ao selectRecords - string null")
	public void test_NOK_null1_selectRecordsFromTable() throws MalformedClaimException, SQLException, JoseException {
		assertEquals(0,DBmanager.selectRecordsFromTable("Amaro.Miguel@gmail.com",null));
	}

	//teste ao generateToken
	@Test
	@DisplayName("Teste ao generateToken - OK")
	public void test_OK_generateToken() throws MalformedClaimException, JoseException {
		assertTrue(Security.generateToken("teste") instanceof String);
	}

	//teste ao generateToken com um null
	@Test
	@DisplayName("Teste ao generateToken - NOK")
	public void test_NOK_null_generateToken() throws MalformedClaimException, JoseException {
		assertTrue(Security.generateToken(null) instanceof String);

	}

	//como é que fazemos o teste BB à conecao qd n há variaveis de entrada?
	// BB n da para fazer mas temos de testar a constring, user e pass validos e invalidos

	//teste BB ao validate
	
	//teste BB ao checkUserToken
	
	//teste BB ao saveToken
}
