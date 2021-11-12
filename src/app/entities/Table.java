package app.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import app.interfaces.EntityStorable;

public class Table implements EntityStorable {
  public static final int MAX_DINING_HRS = 2;

  private static int nextId = 1;

  private int id;
  private int pax;
  private Order occupiedBy;
  private List<Reservation> reservations;

  public Table(int paxIn) {
    id = nextId++;
    pax = paxIn;
    occupiedBy = null;
    reservations = new ArrayList<Reservation>();
  }

  public static int getNextId() { return nextId; }
  public static int incrementNextId() { return ++nextId; }
  public static void setNextId(int id) { nextId = id; }

  public int getId() { return id; }
  public void setId(int idIn) { id = idIn; }

  public int getPax() { return pax; }
  public void setPax(int paxIn) { pax = paxIn; }

  public Order getOccupiedBy() {return occupiedBy; }
  public void setOccupiedBy(Order order) { occupiedBy = order; }
  public void setUnoccupied() { occupiedBy = null; }

  public List<Reservation> getReservations() { return reservations; }
  public void addReservation(Reservation resv) {
    if (reservations.add(resv))
      System.out.println("Added " + resv.getName() + " to " + this.getName());
  }

  public void AddReservation(Reservation resv) {
    if (reservations.contains(resv))
      System.out.println(this.getName() + " already has " + resv.getName());
    else if (reservations.add(resv))
      System.out.println("Added " + resv.getName() + " to " + this.getName());
    else
      System.out.println("Failed to add " + resv.getName() + " to " + this.getName());
  }

  public void removeReservation(Reservation resv) {
    if (reservations.remove(resv))
      System.out.println("Removed " + resv.getName() + " from " + this.getName());
    else
      System.out.println("Failed to remove " + resv.getName() + " from " + this.getName());
  }

  /* For Reservation, need to check for the time of Reservation */
  public boolean isFree(LocalDateTime start, LocalDateTime end) {
    /* Some one is sitting on it at this time interval */
    if (getOccupiedBy() != null
        && start.isBefore(getOccupiedBy().getEnd()))
      return false;

    for (Reservation resv:getReservations())
      /* Check if the given time range overlap with any Reservation on this Table */
      if (start.isBefore(resv.end) && end.isAfter(resv.start))
        return false;
      else if (end.isAfter(resv.start) && start.isBefore(resv.end))
        return false;

    return true;
  }
  /* For Order wo Reservation, use the current time */
  public boolean isFree() {
    return isFree(LocalDateTime.now(), LocalDateTime.now().plusHours(MAX_DINING_HRS));
  }

  public void freeExpiredReservations() {
    List<Reservation> toRemove = new ArrayList<Reservation>();
    for (Reservation resv : getReservations()) {
      if (resv.isExpired()) {
        /* Unlink Table from Reservation */
        resv.unsetTable(this);
        toRemove.add(resv);
      }
    }
    /* Remove Reservation from List attached to this Table */
    for (Reservation resv : toRemove) {
      removeReservation(resv);
    }
    /* We are unable to delete the Reservation from ReservationController list here.
      When a Reservation has table = null, it is considered expired, or checkign the start end will also show the same through isExpired(), so it will be handled by the ReservationController accordingly */
  }

  @Override
  public String getName() { return "Table #" + getId(); }

  @Override
  public String getDesc() {
    return pax + " pax max";
  }

  @Override
  public String getAttrsString() {
    String bookStatus = isFree() ?
      "(unoccupied)":
      getOccupiedBy().getName();
    return "Table ID: " + getId() +
      "\nCapacity (pax):" + getPax() +
      "\nOccupied by: " + bookStatus;
  }

  // public void bookTable(Bookable b) {
  //   if (isOccupied()) {
  //     System.out.println("Fail to book: " + getName() + " already booked for " + getBookerName() + "!");
  //   } else {
  //     setOccupied();
  //     setBooker(b);
  //     System.out.println(getName() + " is now booked for " + getBookerName());
  //   }
  // }
}
