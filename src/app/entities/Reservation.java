package app.entities;

import app.interfaces.EntityStorable;

public class Reservation extends Chopable implements EntityStorable {
  public Reservation(Staff s, Customer c) {
    super(s, c);
  }

  public String getName() {
    return "Reservation No. ";
  }

  public String getDesc() {
    return "For " + customer.getName() + ", ordered by " + staff.getName();
  }
}
