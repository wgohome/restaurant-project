package app.boundaries;

import java.util.TreeMap;

import app.controllers.MenuItemController;
import app.entities.MenuItem;
import app.entities.MenuItem.ItemType;
import app.interfaces.EntityStorable;
import app.utilities.ChoicePicker;

public class MenuItemBoundary extends Boundary {
  public MenuItemBoundary() {
    super(new MenuItemController());
    entityName = "Menu Item";
  }

  protected EntityStorable entityCreator() {
    String name;
    double price;
    ItemType type;

    System.out.println("Enter item name: ");
    name = sc.nextLine();

    System.out.println("Enter price: ");
    price = sc.nextDouble(); sc.nextLine();

    TreeMap<Integer, String> options = new TreeMap<Integer, String>();
    for (int i = 0; i < ItemType.values().length; i++)
      options.put(i + 1, ItemType.values()[i].name());
    ChoicePicker typePicker = new ChoicePicker("Enter number corresponding to job title: ", options);
    type = ItemType.values()[typePicker.run() - 1];

    return (EntityStorable) (new MenuItem(name, price, type));
  }

  public void mainOptions() {
    int choice = -1;
    TreeMap<Integer, String> options = new TreeMap<Integer, String>();
    options.put(1, "List all Menu Items");
    options.put(2, "Add new Menu Item");
    options.put(3, "Remove a Menu Item");
    options.put(4, "Edit a Menu Item");
    options.put(9, "Exit - Back to main menu");
    ChoicePicker mainPicker = new ChoicePicker("This is the Menu Items menu, what would you like to do? ", options);
    while (choice != 9) {
      choice = mainPicker.run();
      switch (choice) {
      case 1:
        printAll();
        break;
      case 2:
        create();
        break;
      case 3:
        delete();
        break;
      case 4:
        edit();
        break;
      case 9:
        System.out.println("Going back to the main menu ... ");
        break;
      default:
        break;
      }
    }
  }
}
