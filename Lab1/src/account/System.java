package account;

import enums.OrderStatus;
import enums.UserPosition;
import items.FoodOrder;
import manager.CredentialManager;
import manager.OrderManager;

public class System extends UserAccount {

	CredentialManager userDB;
	OrderManager orderDB;
	
	public System(String id, String pwd, String name, String phoneNumber) {
		super(id, pwd, name, phoneNumber);
		
		this.userDB = CredentialManager.getInstance();
		this.orderDB = OrderManager.getInstance();
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
	
	public FoodOrder getAnOrder(String orderID) {
		return orderDB.getOrder(orderID);
	}
	
	public void changeOrderStatus(String orderID, OrderStatus status) {
		orderDB.getOrder(orderID).status = status;
	}
}
