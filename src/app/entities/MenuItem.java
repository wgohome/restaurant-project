package app.entities;

import app.interfaces.EntityStorable;
import app.interfaces.Orderable;

public class MenuItem implements EntityStorable, Orderable {
  public enum ItemType { ENTREE, SIDES, DRINKS, DESSERTS };

  private String name;
  private double price;
  private ItemType type;

  public MenuItem(String n, double p, ItemType t) {
    name = n;
    price = p;
    type = t;
  }

  @Override
  public String getName() { return name; }
  public void setName(String n) { name = n; }

  public double getPrice() { return price; }
  public String getPriceString() {
    return String.format("$ %.2f", getPrice());
  }
  public void setPrice(double p) { price = p; }

  public ItemType getType() { return type; }
  public String getTypeString() { return getType().name(); }
  public void setType(ItemType t) { type = t; }

  @Override
  public String getDesc() {
    return getName() + " " +
      String.format("$ %.2f", price) + " (" +
      getTypeString() + ")";
  }

  @Override
  public String getAttrsString() {
    return "Name: " + getName() +
      "\nPrice: " + getPriceString() +
      "\nType: " + getTypeString();
  }
}
