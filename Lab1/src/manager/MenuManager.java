package manager;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import enums.FoodSize;
import items.FoodItem;

public class MenuManager {
	
	public Hashtable<String, FoodItem> menuDB;
	private int lastID = 0;
	
	private String fetchNextID() {
		int result = lastID;
		lastID += 1;
		return "" + result;
	}
	
	private static MenuManager INSTANCE;

	private MenuManager() {
		this.menuDB = new Hashtable<String, FoodItem>();
	}
	
	public static MenuManager getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new MenuManager();
		}
		
		return INSTANCE;
	}
	
	

	public void addFoodMenu(String name,
							double price,
							String ingredients,
							String allergens,
							String dimensions,
							String subjectCategory,
							String nutrition
							) {
		String id = fetchNextID();
		FoodItem temp = new FoodItem(id, name, price, FoodSize.MEDIUM, 1, ingredients, allergens, 
				dimensions, subjectCategory, nutrition);
		
		menuDB.put(id, temp);
	}
	
	public boolean hasFoodItem(String name) {
		boolean result = false;
		for (String key: menuDB.keySet()) {
			if (menuDB.get(key).name.equals(name)){
				result = true;
			}
		}
		
		return result;
	}
	
	public FoodItem getFooditem(String name) {
		FoodItem temp = null;
		
		for (String key: menuDB.keySet()) {
			if (menuDB.get(key).name.equals(name)) {
				temp = menuDB.get(key);
			}
		}
		

		FoodItem result = new FoodItem(temp.id, temp.name, temp.price, FoodSize.MEDIUM, 1, temp.ingredients, 
				temp.allergens, temp.dimensions, temp.subjectCategory, temp.nutrition);

		
		return result;
	}
	
	public List<String> getFoodNameList() {
		List<String> result = new ArrayList<String>();
		for (String key: menuDB.keySet()) {
			result.add(menuDB.get(key).name);
		}
		
		return result;
	}
	
	public void removeFoodMenu(String name) {
		String tempKey = "";
		for (String key: menuDB.keySet()) {
			if (menuDB.get(key).name.equals(name)) {
				tempKey = key;
			}
		}
		menuDB.remove(tempKey);
	}
	
	public List<String> getMenuByCategories(){
		List<String> result = new ArrayList<String>();
		Hashtable<String, String> itemByCategories = new Hashtable<String, String>();
		
		for (String key: menuDB.keySet()) {
			String category = menuDB.get(key).subjectCategory;
			String itemString = menuDB.get(key).name;
			
			if (!itemByCategories.containsKey(category)) {
				itemByCategories.put(category, category + ":" + itemString);
			} else {
				itemByCategories.put(category, itemByCategories.get(category) + ", " + itemString);
			}
		}
		
		for (String key: itemByCategories.keySet()) {
			result.add(itemByCategories.get(key));
		}
		
		
		return result;
	}
	
	public void updateFoodIngredients(String name, String ingredients) {
		for (String key: menuDB.keySet()) {
			if (menuDB.get(key).name.equals(name)) {
				menuDB.get(key).ingredients = ingredients;
			}
		}
	}
	
	public void updateFoodAllergens(String name, String allergens) {
		for (String key: menuDB.keySet()) {
			if (menuDB.get(key).name.equals(name)) {
				menuDB.get(key).allergens = allergens;
			}
		}
	}
	
	public void updateFoodDimensions(String name, String dimensions) {
		for (String key: menuDB.keySet()) {
			if (menuDB.get(key).name.equals(name)) {
				menuDB.get(key).dimensions = dimensions;
			}
		}
	}
	
	public void updateFoodSubjectCategory(String name, String subjectCategory) {
		for (String key: menuDB.keySet()) {
			if (menuDB.get(key).name.equals(name)) {
				menuDB.get(key).subjectCategory = subjectCategory;
			}
		}
	}
	
	public void updateFoodNutrition(String name, String nutrition) {
		for (String key: menuDB.keySet()) {
			if (menuDB.get(key).name.equals(name)) {
				menuDB.get(key).nutrition = nutrition;
			}
		}
	}
}
