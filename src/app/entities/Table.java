package app.entities;

import app.interfaces.EntityStorable;

public class Table implements EntityStorable {
  private int id;
  private int pax;
  private boolean occupied;
  private Bookable booker;

  public Table(int idIn, int paxIn) {
    id = idIn;
    pax = paxIn;
    occupied = false;
    booker = null;
  }

  public int getId() { return id; }
  public void setId(int idIn) { id = idIn; }

  public int getPax() { return pax; }
  public void setPax(int paxIn) { pax = paxIn; }

  public boolean isOccupied() { return occupied; }
  private void setOccupied() { occupied = true; }
  private void setUnoccupied() { occupied = false; }

  public Bookable getBooker() { return booker; }
  public String getBookerName() { return getBooker().getName(); }
  private void setBooker(Bookable b) { booker = b; }

  @Override
  public String getName() { return "Table no " + id; }

  @Override
  public String getDesc() {
    return getName() + ": " +
      pax + " pax max " + (occupied ? "(occupied)" : "(free)");
  }

  @Override
  public String getAttrsString() {
    String bookStatus = isOccupied() ?
      getBooker().getName() :
      "(unoccupied)";
    return "Table ID: " + getId() +
      "\nCapacity (pax):" + getPax() +
      "\nBooker: " + bookStatus;
  }

  public void bookTable(Bookable b) {
    if (isOccupied()) {
      System.out.println("Fail to book: " + getName() + " already booked for " + getBookerName() + "!");
    } else {
      setOccupied();
      setBooker(b);
      System.out.println(getName() + " is now booked for " + getBookerName());
    }
  }

  public void freeTable() {
    if (isOccupied()) {
      setUnoccupied();
      setBooker(null);
      System.out.println(getName() + " has been freed");
    }
  }

  public void freeIfResvExpired() {
    if (!(booker instanceof Reservation)) return;

    Reservation resv = (Reservation) booker;
    if (resv.isExpired()) {
      System.out.println(resv.getName() +" has expired, freeing up table ...");
      freeTable();
    }
  }
}
