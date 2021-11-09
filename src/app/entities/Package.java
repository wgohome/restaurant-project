package app.entities;

public class Package extends Orderable {
  public Package(String n, double p) {
    super(n, p);
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
