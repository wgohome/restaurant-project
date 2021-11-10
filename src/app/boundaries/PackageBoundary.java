package app.boundaries;

import java.util.List;
import java.util.TreeMap;

import app.controllers.PackageController;
import app.entities.MenuItem;
import app.entities.MenuItem.ItemType;
import app.interfaces.EntityStorable;
import app.utilities.ChoicePicker;

public class PackageBoundary extends Boundary {
  public PackageBoundary() {
    super(new PackageController());
    entityName = "Promotional Package";
  }

    @Override
    protected EntityStorable entityCreator() {
      String name;
      double price;
      List<MenuItem> items;

      System.out.println("Enter promotional package name: ");
      name = sc.nextLine();

      System.out.println("Enter promotional package price: ");
      price = sc.nextDouble(); sc.nextLine();

      // TODO:Edit this for package not for menu item type
      TreeMap<Integer, String> options = new TreeMap<Integer, String>();
      for (int i = 0; i < ItemType.values().length; i++)
        options.put(i + 1, ItemType.values()[i].name());
      ChoicePicker typePicker = new ChoicePicker("Enter number corresponding to the Menu Item to add: ", options);
      ItemType type = ItemType.values()[typePicker.run() - 1];

      return (EntityStorable) (new MenuItem(name, price, type));
    }

  @Override
  public void mainOptions() {
    int choice = -1;
    TreeMap<Integer, String> options = new TreeMap<Integer, String>();
    options.put(1, "List all Packages");
    options.put(2, "Add new Package");
    options.put(3, "Remove a Package");
    options.put(4, "Edit a Package");
    options.put(9, "Exit - Back to main menu");
    ChoicePicker mainPicker = new ChoicePicker("This is the Packages menu, what would you like to do? ", options);
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
