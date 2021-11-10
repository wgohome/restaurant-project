package app.utilities;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import app.RestaurantApp;

public class ChoicePicker {
  protected Scanner sc = RestaurantApp.sc;
  String prompt;
  TreeMap<Integer,String> options;

  public ChoicePicker(String promptIn, TreeMap<Integer, String> optionsIn) {
    prompt = promptIn;
    options = optionsIn;
  }

  public int run(boolean toPrintOptions) {
    int choice = -1;

    System.out.println("");
    System.out.println(prompt);
    if (toPrintOptions) printOptions();
    /* Expects none of the options key to really be -1 */
    while (!options.containsKey(choice)) {
      System.out.println("Enter a valid option number: ");
      choice = sc.nextInt(); sc.nextLine();
    }
    return choice;
  }
  public int run() {
    return run(true);
  }

  private void printOptions() {
    for (Map.Entry<Integer, String> e:options.entrySet())
      System.out.println(e.getKey() + ". " + e.getValue());
  }
}
