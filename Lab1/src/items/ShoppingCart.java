package items;

import java.util.List;

import account.Client;
import enums.FoodSize;
import manager.MenuManager;
import manager.OrderManager;

public class ShoppingCart {

	public OrderManager orderDB;
	public MenuManager menuDB;
	public Client cartOwner;
	public FoodOrder currentOrder;
	
	public ShoppingCart(Client owner) {
		this.orderDB = OrderManager.getInstance();
		this.menuDB = MenuManager.getInstance();
		this.cartOwner = owner;
		this.currentOrder = new FoodOrder("-1", owner, null);
	}
	
	public void addItem(String name, FoodSize size) {
		currentOrder.addFoodItem(name, size);
	}
	
	public void deleteItem(String name, FoodSize size) {
		currentOrder.removeFoodItem(name, size);
	}
	
	public boolean hasItem(String name) {
		return currentOrder.hasFoodItem(name);
	}
	
	public FoodItem getMenuItem(String name, FoodSize size) {
		return currentOrder.getMenuItem(name, size);
	}
	
	public List<String> getMenuByCategories(){
		return menuDB.getMenuByCategories();
	}

	public String getFoodInfo(String foodName, FoodSize size) {
		String result = "";
		FoodItem tempItem = menuDB.getFooditem(foodName);
		
		result += "ingredients: " + tempItem.ingredients + "\n";
		result += "allergens: " + tempItem.allergens + "\n";
		result += "dimensions: " + tempItem.dimensions + "\n";
		result += "subjectCategory: " + tempItem.subjectCategory + "\n";
		result += "nutrition: " + tempItem.nutrition;
		return result;
	}

	public String getCurrentItems() {
		String result = "";
		List<String> nameQuantityList = currentOrder.getFoodNameQuantityList();
		
		for (String entry: nameQuantityList) {
			result += entry + "\n";
		}
		
		return result;
	}
	
	public String placeOrder(String deliverAddress) {
		String result;
		this.currentOrder.deliverAddress = deliverAddress;
		result = orderDB.addOrder(currentOrder);
		
		this.currentOrder = new FoodOrder("-1", this.cartOwner, null);
		return result;
	}

	public double getTotal() {
		return this.currentOrder.getTotal();
	}
}
