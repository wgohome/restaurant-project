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

  public String getName() { return name; }
  public double getPrice() { return price; }
  public String getDesc() { return String.format("$ %.2f", price); }
  public String getType() { return type.name(); }
}
