package app.entities;

import app.interfaces.EntityStorable;

public class Table implements EntityStorable {
  private int id;
  private int pax;
  // private Order curOrder;
  // private Reservation curReservation;
  // private Chopable curChoper;

  public Table(int idIn, int paxIn) {
    id = idIn;
    pax = paxIn;
  }

  public int getId() { return id; }
  public int getPax() { return pax; }
  public void setId(int idIn) { id = idIn; }
  public void setPax(int paxIn) { pax = paxIn; }

  /* Realising methods of EntityStorable interface */
  public String getName() { return "Table no " + id; }
  public String getDesc() { return pax + " pax max"; }
}
