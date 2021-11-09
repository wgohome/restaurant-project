package app.interfaces;

public interface Printable {
  public String getName();

  public String getDesc();
  /* Used when listing out one line per object for ALL OBJECTS of that class */

  public String getAttrsString();
  /* Used when printing out all attributes of object for ONE INSTANCE of that class (show), for editing purpose */
}
