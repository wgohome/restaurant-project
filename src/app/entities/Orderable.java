package app.entities;

import app.interfaces.EntityStorable;

public abstract class Orderable implements EntityStorable {
  private String name;
  private double price;

  public Orderable(String n, double p) {
    name = n;
    price = p;
  }

  @Override
  public String getName() { return name; }
  public void setName(String n) { name = n; }

  public double getPrice() { return price; }
  public String getPriceString() {
    return String.format("$ %.2f", getPrice());
  }
  public void setPrice(double p) { price = p; }

  public double getPriceDiscounted(double discount) {
    return getPrice() * discount;
  }

  @Override
  public abstract String getDesc();
  @Override
  public abstract String getAttrsString();

  public abstract String getInvoiceEntry();
    /* Just name for MenuItem, but list of items for Package */
}
