package app.entities;

import java.util.ArrayList;
import java.util.List;

public class Promotion extends Orderable {
  List<MenuItem> items;

  public Promotion(String n, double p) {
    super(n, p);
    items = new ArrayList<MenuItem>();
  }

  public List<MenuItem> getItems() { return items; }
  public int getNumItems() { return getItems().size(); }
  public MenuItem getItem(int index) {
    return getItems().get(index);
  }
  public String getItemName(int index) {
    return getItem(index).getName();
  }

  /* TODO: Promotional Package should have different quantities of Menu Items */
  public void addItem(MenuItem item) {
    /* Check that item is not already inside promotion */
    if (getItems().contains(item)){
      System.out.println("Menu Item " + item.getName() + " is already in the package!");
      return;
    }
    if (items.add(item))
      System.out.println("Added " + item.getName() + " to Promotion " + this.getName());
  }

  public MenuItem removeItem(int index) {
    MenuItem removedItem = items.remove(index);
    if (removedItem != null) {
      System.out.println(removedItem.getName() + " has been removed from Promotion " + getName());
    } else {
      System.out.println("Failed to remove null MenuItem from Pakage " + getName());
    }
    return removedItem;
  }

  @Override
  public String getDesc() {
    return getName() + " " +
      getPriceString() + " \n\t(" +
      getInvoiceEntry() + ")";
  }

  @Override
  public String getAttrsString() {
    String attrs = "Name: " + getName() +
      "\nPrice: " + getPriceString() +
      "\nItems: ";
    for (MenuItem item:getItems()) {
      attrs += ("\n\t" + item.getName());
    }
    return attrs;
  }

  @Override
  public String getInvoiceEntry() {
    String line = getName() + " (";
    for (int i = 0; i < getNumItems(); i++) {
      if (i > 0) line += ", ";
      line += getItemName(i);
    }
    return line;
  }
}
