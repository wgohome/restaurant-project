package app.controllers;

import java.util.ArrayList;
import java.util.List;

import app.entities.Reservation;
import app.interfaces.EntityStorable;

public class ReservationController extends Controller {
  public ReservationController() {
    super("data/reservations.dat");
    updateNextId();
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
    resv.getTable().removeReservation(resv);
  }

  private void updateNextId() {
    for (EntityStorable entity:getList()) {
      Reservation resv = (Reservation) entity;
      if (Reservation.getNextId() <= resv.getId()) {
        Reservation.setNextId(resv.getId() + 1);
      }
    }
  }
}
