package app.db;

import java.util.ArrayList;
import java.util.List;

import app.entities.MenuItem;
import app.entities.MenuItem.ItemType;
import app.interfaces.EntityStorable;

public class MenuItemData extends Data {
  public MenuItemData() {
    super("data/menu_items.dat");
  }

  protected List<EntityStorable> seedList() {
    List<EntityStorable> menuItems = new ArrayList<EntityStorable>();
    menuItems.add(new MenuItem("Burger", 6, ItemType.ENTREE));
    menuItems.add(new MenuItem("Steak", 10, ItemType.ENTREE));
    menuItems.add(new MenuItem("Pasta", 5, ItemType.ENTREE));
    menuItems.add(new MenuItem("Fried Rice", 4, ItemType.ENTREE));
    menuItems.add(new MenuItem("Soup of the Day", 3, ItemType.SIDES));
    menuItems.add(new MenuItem("Salad", 4.50, ItemType.SIDES));
    menuItems.add(new MenuItem("Fries", 3.39, ItemType.SIDES));
    menuItems.add(new MenuItem("Coleslaw", 4, ItemType.SIDES));
    menuItems.add(new MenuItem("Coca Cola", 2, ItemType.DRINKS));
    menuItems.add(new MenuItem("Beer", 2, ItemType.DRINKS));
    menuItems.add(new MenuItem("Wine", 2, ItemType.DRINKS));
    menuItems.add(new MenuItem("Water", 1, ItemType.DRINKS));
    menuItems.add(new MenuItem("Tiramisu", 2, ItemType.DESSERTS));
    menuItems.add(new MenuItem("Cheesecake", 2, ItemType.DESSERTS));
    menuItems.add(new MenuItem("Chocolate Cake", 2, ItemType.DESSERTS));
    menuItems.add(new MenuItem("Ice Cream", 2, ItemType.DESSERTS));
    return menuItems;
  }
}
