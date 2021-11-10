package app.controllers;

import java.util.ArrayList;
import java.util.List;

import app.entities.Reservation;
import app.interfaces.EntityStorable;

public class ReservationController extends Controller {
  public ReservationController() {
    super("data/reservations.dat");
  }

  protected List<EntityStorable> seedList() {
    List<EntityStorable> reservations = new ArrayList<EntityStorable>();
    // reservations.add(s1, c1);
    /* Start with no reservation */
    return reservations;
  }

  @Override
  protected void afterRemove(EntityStorable entity) {
    /* Free the table */
    Reservation resv = (Reservation) entity;
    resv.getTable().freeTable();
  }
}
