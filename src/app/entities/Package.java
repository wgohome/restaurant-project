package app.entities;

import java.util.ArrayList;
import java.util.List;

public class Package extends Orderable {
  List<MenuItem> items;

  public Package(String n, double p) {
    super(n, p);
    items = new ArrayList<MenuItem>();
  }

  public List<MenuItem> getItems() { return items; }

  protected void addItem(MenuItem item) { items.add(item); }

  protected MenuItem removeItem(int index) {
    MenuItem removedItem = items.remove(index);
    if (removedItem != null) {
      System.out.println(removedItem.getName() + " has been removed from Package " + getName());
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
    return "Name: " + getName() +
      "\nPrice: " + getPriceString();
  }

  @Override
  public String getInvoiceEntry() {
    // TODO: list out all MenuItems in this package
    return null;
  }
}
