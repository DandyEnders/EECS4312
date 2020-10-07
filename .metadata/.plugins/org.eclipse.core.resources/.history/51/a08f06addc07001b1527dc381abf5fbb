package account;

public class UserAccount {

	public String id;
	public String pwd;
	public String name;
	public String phoneNumber;
	public boolean isOnline;
	
	public UserAccount (String id, String pwd, String name, String phoneNumber) {
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.isOnline = false;
	}
	
	public boolean login(String id, String pwd) {
		if (this.id.equals(id) && this.pwd.equals(pwd)) {
			isOnline = true;
		}
		
		return isOnline;
	}

	public boolean logoff() {
		isOnline = false;
		return isOnline == false;
	}
}
