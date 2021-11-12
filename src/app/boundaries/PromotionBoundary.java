package app.boundaries;

import java.util.TreeMap;

import app.controllers.PromotionController;
import app.entities.MenuItem;
import app.entities.Promotion;
import app.interfaces.EntityStorable;
import app.utilities.ChoicePicker;

public class PromotionBoundary extends Boundary {
  MenuItemBoundary itemBoundary;

  public PromotionBoundary(MenuItemBoundary itemB) {
    super(new PromotionController());
    entityName = "Promotional Promotion";
    itemBoundary = itemB;
  }

  @Override
  protected EntityStorable entityCreator() {
    String name;
    double price;

    System.out.println("Enter promotional package name: ");
    name = sc.nextLine();
    System.out.println("Enter promotional package price: ");
    price = sc.nextDouble(); sc.nextLine();
    Promotion promo = new Promotion(name, price);

    /* Add MenuItem to created Promotion */
    TreeMap<Integer, String> options = new TreeMap<Integer, String>();
    options.put(1, "Add Menu Item to Promotion");
    options.put(2, "Done");
    ChoicePicker addPicker = new ChoicePicker("What do you want to do with this Promotion: " + promo.getName(), options);
    int choice;
    choice = addPicker.run();
    while (choice != 2) {
      addItemToPromotion(promo);
      choice = addPicker.run();
    }
    return promo;
  }

  @Override
  public void mainOptions() {
    int choice = -1;
    TreeMap<Integer, String> options = new TreeMap<Integer, String>();
    options.put(1, "List all Promotions");
    options.put(2, "Add new Promotion");
    options.put(3, "Remove a Promotion");
    options.put(4, "Edit a Promotion (Replace old Promotion with a new one");
    options.put(5, "Add MenuItem to a Promotion");
    options.put(6, "Remove MenuItem from a Promotion");
    options.put(9, "Exit - Back to main menu");
    ChoicePicker mainPicker = new ChoicePicker("This is the Promotions menu, what would you like to do? ", options);
    while (choice != 9) {
      choice = mainPicker.run();
      switch (choice) {
      case 1:
        indexAll();
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
      case 5:
        addItemToPromotion();
        break;
      case 6:
        removeItemFromPromotion();
        break;
      case 9:
        System.out.println("Going back to the main menu ... ");
        break;
      default:
        break;
      }
    }
  }

  private void addItemToPromotion() {
    /* Choose which promotion to add to */
    ChoicePicker promotionPicker = new ChoicePicker(
      "Which Promotion to add to?",
      getChoiceMap()
    );
    int promoChoice = promotionPicker.run();
    Promotion promo = (Promotion) getEntity(promoChoice - 1);
    this.addItemToPromotion(promo);
  }

  private void addItemToPromotion(Promotion promo) {
    int itemChoice;
    /* Choose the MenuItem to add */
    TreeMap<Integer, String> options = itemBoundary.getChoiceMap();
    options.put(0, "Done and go back to previous menu");
    ChoicePicker itemPicker = new ChoicePicker(
      "Which Menu Item to add to Promotion " + promo.getName(),
      options
    );
    itemChoice = itemPicker.run();
    while (itemChoice != 0) {
      MenuItem item = (MenuItem) itemBoundary.getEntity(itemChoice - 1);
      /* Add the MenuItem to Promotion */
      promo.addItem(item);
        /* Note: No checks done if item exists because ChoicePicker already takes care of the checks */
      itemChoice = itemPicker.run();
    }
  }

  private void removeItemFromPromotion() {
    int itemChoice;
    int promoChoice;
    /* Choose which promotion to remove from */
    ChoicePicker promotionPicker = new ChoicePicker(
      "Which Promotion to remove from?",
      getChoiceMap()
    );
    promoChoice = promotionPicker.run();
    Promotion promo = (Promotion) getEntity(promoChoice - 1);
    /* Choose the MenuItem to remove */
    ChoicePicker itemPicker = new ChoicePicker(
      "Which Menu Item to remove from Promotion: " + promo.getName(),
      this.makeChoiceMap(promo)
    );
    itemChoice = itemPicker.run();
    while (itemChoice != 0) {
      /* Remove the MenuItem from Promotion */
      promo.removeItem(itemChoice - 1);
        /* Note: No checks done if item exists because ChoicePicker already takes care of the checks */
      itemChoice = itemPicker.run();
    }
  }

  private TreeMap<Integer, String> makeChoiceMap(Promotion promo) {
    /* Make TreeMap of options for MenuItems in a Promotion */
    TreeMap<Integer, String> options = new TreeMap<Integer, String>();
    for (int i = 0 ; i < promo.getNumItems(); i++) {
      options.put(i + 1, promo.getItemName(i));
    }
    options.put(0, "Done and go back to previous menu");
    return options;
  }
}
