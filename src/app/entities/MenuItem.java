package app.entities;

public class MenuItem extends Orderable {
  public enum ItemType { ENTREE, SIDES, DRINKS, DESSERTS };

  private ItemType type;

  public MenuItem(String n, double p, ItemType t) {
    super(n, p);
    type = t;
  }

  public ItemType getType() { return type; }
  public String getTypeString() { return getType().name(); }
  public void setType(ItemType t) { type = t; }

  @Override
  public String getDesc() {
    return getName() + " " +
      getPriceString() + " (" +
      getTypeString() + ")";
  }

  @Override
  public String getAttrsString() {
    return "Name: " + getName() +
      "\nPrice: " + getPriceString() +
      "\nType: " + getTypeString();
  }

  @Override
  public String getInvoiceEntry() {
    return getName();
  }
}
