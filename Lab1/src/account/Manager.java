package account;

import enums.FoodSize;
import items.FoodOrder;
import manager.MenuManager;
import manager.OrderManager;

public class Manager extends UserAccount {

	MenuManager menuDB;
	OrderManager orderDB;
	
	public Manager(String id, String pwd, String name, String phoneNumber) {
		super(id, pwd, name, phoneNumber);
		
		this.menuDB = MenuManager.getInstance();
		this.orderDB = OrderManager.getInstance();
	}
	
	public String getOrderInfo() {
		return orderDB.getOrderInfo();
	}
	
	public String getMenuByCategories() {
		return menuDB.getMenuByCategories().toString();
	}
	
	public String addOrder(UserAccount orderedBy,
						String deliverAddress) {
		return orderDB.addOrder(new FoodOrder("-1", orderedBy, deliverAddress));
	}
	
	public void changeOrderedPerson(String orderID, UserAccount newAccount) {
		orderDB.getOrder(orderID).orderedBy = newAccount;
	}
	
	public void addFood(String orderID, String foodName, FoodSize size) {
		orderDB.getOrder(orderID).addFoodItem(foodName, size);
	}
	
	public void removeFood(String orderID, String foodName, FoodSize size) {
		orderDB.getOrder(orderID).removeFoodItem(foodName, size);
	}
	
	public void changeDeliveryAddress(String orderID, String newAddress) {
		orderDB.getOrder(orderID).deliverAddress = newAddress;
	}
	
	public void addFoodMenu(String name,
							double price,
							String ingredients,
							String allergens,
							String dimensions,
							String subjectCategory,
							String nutrition) {
		menuDB.addFoodMenu(name, price, ingredients, allergens, dimensions, subjectCategory, nutrition);
	}
	
	public String getFoodInfo(String name) {
		return menuDB.getFooditem(name).getInfo();
	}
	
	public void removeFoodMenu(String name) {
		menuDB.removeFoodMenu(name);
	}
	
	public void updateFoodIngredients(String name, String ingredients) {
		menuDB.updateFoodIngredients(name, ingredients);
	}
	
	public void updateFoodAllergens(String name, String allergens) {
		menuDB.updateFoodAllergens(name, allergens);
	}
	
	public void updateFoodDimensions(String name, String dimensions) {
		menuDB.updateFoodDimensions(name, dimensions);
	}
	
	public void updateFoodSubjectCategory(String name, String subjectCategory) {
		menuDB.updateFoodSubjectCategory(name, subjectCategory);
	}
	
	public void updateFoodNutrition(String name, String nutrition) {
		menuDB.updateFoodNutrition(name, nutrition);
	}
	
}
