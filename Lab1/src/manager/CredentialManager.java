package manager;

import java.util.Hashtable;

import account.Client;
import account.Manager;
import account.System;
import account.UserAccount;
import enums.UserPosition;

public class CredentialManager {

	Hashtable<String, UserAccount> userAccountDB;
	
	private static CredentialManager INSTANCE;
	
	private CredentialManager() {
		this.userAccountDB = new Hashtable<String, UserAccount>();
	}

	public static CredentialManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new CredentialManager();
		}
		
		return INSTANCE;
	}
	
	public void addUser(String id,
						String pwd,
						String name,
						UserPosition position,
						String phoneNumber
						) {
		UserAccount temp;
		if (position.equals(UserPosition.CLIENT)) {
			temp = new Client(id, pwd, name, phoneNumber);
		} else if (position.equals(UserPosition.MANAGER)) {
			temp = new Manager(id, pwd, name, phoneNumber);
		} else {
			temp = new System(id, pwd, name, phoneNumber);
		}
		userAccountDB.put(id, temp);
	}
	
	public boolean hasUser (String id) {
		return userAccountDB.containsKey(id);
	}
	
	public void deleteUser (String id) {
		userAccountDB.remove(id);
	}
	
	public String getUsers () {
		return userAccountDB.toString();
	}
	
	public UserAccount getOwner(String id) {
		return userAccountDB.get(id);
	}
	
	public UserAccount getAccount (	String id,
									String pwd
								) {
		UserAccount temp = userAccountDB.get(id);
		
		if (temp.pwd.equals(pwd)) {
			return temp;
		}
		return null;
	}
	
	
	
	
}
