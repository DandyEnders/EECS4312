package account;

import enums.UserPosition;
import manager.CredentialManager;

public class System extends UserAccount {

	CredentialManager userDB;
	
	public System(String id, String pwd, String name, String phoneNumber) {
		super(id, pwd, name, phoneNumber);
		
		this.userDB = CredentialManager.getInstance();
	}
	
	public void addUser (String id,
						String pwd,
						String name,
						UserPosition position,
						String phoneNumber) {
		userDB.addUser(id, pwd, name, position, phoneNumber);
	}
	
	public void removeUser (String id) {
		userDB.deleteUser(id);
	}
	
	public String getUsers() {
		return userDB.getUsers();
	}
}
