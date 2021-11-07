package app.db;

import java.util.ArrayList;
import java.util.List;

import app.interfaces.EntityStorable;

public class ReservationData extends Data {
  public ReservationData() {
    super("data/reservations.dat");
  }

  protected List<EntityStorable> seedList() {
    List<EntityStorable> reservations = new ArrayList<EntityStorable>();
    // reservations.add(s1, c1);
    /* Start with no reservation */
    return reservations;
  }
}
