package service;

import manager.OrderManager;

public class PaymentService {
	
	OrderManager orderDB;
	
	private static PaymentService INSTANCE;

	private PaymentService() {
	}
	
	public static PaymentService getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new PaymentService();
		}
		
		return INSTANCE;
	}
	
	public boolean requestPurchase(String id) {
		return true;
	}

	public void successfulPurchase(String id) {
		orderDB = OrderManager.getInstance();
		orderDB.payOrder(id);
	}
	
	public boolean refundRequst(String id) {
		return true;
	}
	
	public void successfulRefund(String id) {
		orderDB = OrderManager.getInstance();
		orderDB.cancelOrder(id);
	}
}
	
