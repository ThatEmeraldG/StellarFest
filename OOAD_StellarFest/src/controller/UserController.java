package controller;

import java.util.Random;

import utils.SQLConnect;

public class UserController {
	private static SQLConnect connect = SQLConnect.getInstance(); 
	static Random rand = new Random();
	
	public static void register(String username, String email, String pass, String role) {
//		insert data 
		int num1 = rand.nextInt(10);
		int num2 = rand.nextInt(10);
		int num3 = rand.nextInt(10);
		String id = "UID" + num1 + num2 + num3; 
			
		String query = String.format("INSERT INTO User VALUES ('%s', '%s', '%s', '%s', '%s')",
				id, username, email, pass, role); 
		connect.execUpdate(query);
	}
}
