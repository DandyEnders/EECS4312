package account;

import java.util.List;

import enums.FoodSize;
import enums.OrderStatus;
import items.ShoppingCart;
import manager.OrderManager;

public class Client extends UserAccount {
	
	public ShoppingCart myCart;
	public OrderManager orderDB;
	public String orderID;
	
	public Client(String id, String pwd, String name, String phoneNumber) {
		super(id, pwd, name, phoneNumber);
		
		this.myCart = new ShoppingCart(this);
		orderID = null;
		this.orderDB = OrderManager.getInstance();
	}
	
	public String getMenuByCategories(){
		return myCart.getMenuByCategories().toString();
	}
	
	public void chooseFood(String foodName, FoodSize size, int quantity) {
		if (quantity > 0) {
			for (int i = 1; i <= quantity; i++) {
				myCart.addItem(foodName, size);
			}
		}
		
	}
	
	public void removeFood(String foodName, FoodSize size) {
		myCart.deleteItem(foodName, size);
	}
	
	public String getCurrentCartItems() {
		return myCart.getCurrentItems();
	}
	
	public String getFoodInfo(String foodName, FoodSize size) {
		return myCart.getFoodInfo(foodName, size);
	}
	
	public double getTotal() {
		return myCart.getTotal();
	}
	
	public void placeOrder(String deliverAddress) {
		orderID =  myCart.placeOrder(deliverAddress);
	}
	
	public void payOrder(double amount) {
		if (orderDB.getOrder(orderID).getTotal() <= amount) {
			orderDB.requestPayment(id);
		}
	}
	
	public void cancelOrder() {
		if (orderDB.getOrder(orderID).status == OrderStatus.PAID) {
			orderDB.requestCancelOrder(orderID);
		}
	}
	
	public OrderStatus checkDeliveryStatus() {
		return orderDB.checkOrderStatus(orderID);
	}
	
	public void newOrder() {
		this.myCart = new ShoppingCart(this);
		orderID = null;
		this.orderDB = OrderManager.getInstance();
	}
}