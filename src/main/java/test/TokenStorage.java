package test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class TokenStorage {

	public static LinkedHashMap<String,String> keyTokenList = new LinkedHashMap<String,String>();
	
	public TokenStorage() {
		
	}
	
	//metodo para inserir no HashMap
	public static void saveToken(String key, String token) {
		keyTokenList.put(key, token);
	}
	
	
	//segundo o prof temos de ter um array com token como key e user como value. o TTL já é verificado no validate
}
