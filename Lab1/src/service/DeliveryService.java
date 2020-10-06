package service;

import manager.OrderManager;

public class DeliveryService {
	
	OrderManager orderDB;
	
	public static double deliveryPrice = 1.00;

	private static DeliveryService INSTANCE;

	private DeliveryService() {
	}
	
	public static DeliveryService getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DeliveryService();
		}
		
		return INSTANCE;
	}

	public boolean requestDelivery(String orderID) {
		return true;
	}
	
	public boolean sendDlievery(String orderID) {
		orderDB = OrderManager.getInstance();
		orderDB.deliverOrder(orderID);
		return true;
	}
	
	public void signDelivery(String orderID) {
		orderDB = OrderManager.getInstance();
		orderDB.signOrder(orderID);
	}
}
