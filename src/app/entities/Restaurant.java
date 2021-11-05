package app.entities;

import java.util.List;
import java.util.Scanner;

import app.RestaurantApp;

public class Restaurant {
  private List<Staff> staffs;
  private Staff curStaff;
  private Scanner sc = RestaurantApp.sc;

  public Restaurant() {
    staffs = Staff.loadAllStaffs();
  }

  public Staff getCurStaff() { return curStaff; }
  public void setCurStaff(Staff s) { curStaff = s; }

  public void staffSelection() {
    int choice;
    printAllStaffs();
    System.out.println("Which staff are you? Enter the corresponding number: ");
    choice = sc.nextInt(); sc.nextLine(); /* flush buffer */
    while (choice < 1 || choice > staffs.size()) {
      System.out.println("Please enter a valid number from the choices above: ");
      choice = sc.nextInt(); sc.nextLine(); /* flush buffer */
    }
    setCurStaff(staffs.get(choice - 1));
    System.out.println("Hello " + curStaff.getName() + "!");
  }

  private void printAllStaffs() {
    if (staffs == null) return;
    System.out.println("All staffs:");
    for (int i = 0; i < staffs.size(); i++) {
      System.out.println((i+1) +  ". " + staffs.get(i).getName());
    }
  }

  public void saveEntities() {
    Staff.saveAllStaffs(staffs);
  }
}
