package app.entities;

import app.interfaces.EntityStorable;

public class Table implements EntityStorable {
  private int id;
  private int pax;
  private boolean occupied;
  // private Order curOrder;
  // private Reservation curReservation;
  // private Chopable curChoper;

  public Table(int idIn, int paxIn) {
    id = idIn;
    pax = paxIn;
    occupied = false;
  }

  public int getId() { return id; }
  public int getPax() { return pax; }
  public boolean isOccupied() { return occupied; }
  public void setId(int idIn) { id = idIn; }
  public void setPax(int paxIn) { pax = paxIn; }
  public void setOccupied() { occupied = true; }
  public void setFree() { occupied = false; }

  /* Realising methods of EntityStorable interface */
  public String getName() { return "Table no " + id; }
  public String getDesc() {
    return occupied ? pax + " pax max (occupied)": pax + " pax max (free)";
  }
}
