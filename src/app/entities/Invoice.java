package app.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import app.utilities.StringAlign;

public class Invoice implements Serializable {
  private static final DateTimeFormatter DT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
  private static final StringAlign RIGHT_FORMATTER = new StringAlign(70, StringAlign.Justify.RIGHT);
  private static double DISCOUNT = 0.1;

  private Order order;
  private LocalDateTime paidAt;

  public Invoice(Order orderIn) {
    order = orderIn;
    paidAt = LocalDateTime.now();
  }

  public LocalDateTime getPaidAt() { return paidAt; }
  public String getPaidAtString() { return paidAt.format(DT_FORMATTER) ; }

  public void printOrder() {
    String priceString;
    String discString;
    double netTotal, discPrice, grandTotal;

    System.out.println("\n===  Invoice  ===");
    System.out.println(order.getAttrsString());
    System.out.println("Paid at: " + getPaidAtString());
    System.out.println("=================");
    System.out.println("Orders:");
    System.out.println();

    for (Map.Entry<Orderable, Integer> entry : order.getOrderables().entrySet())
    {
      priceString = getEntryPriceString(entry);
      System.out.println(entry.getKey().getName());
      System.out.println(RIGHT_FORMATTER.format(priceString));
    }

    netTotal = calcNetTotal();
    // TODO: issue
    discString = String.format("%.0f %%", DISCOUNT * 100);
    discPrice = netTotal * DISCOUNT;
    grandTotal = netTotal - discPrice;

    System.out.println("");
    System.out.println("NET TOTAL: " + formatPriceString(netTotal));
    System.out.println("DISCOUNT (" + discString + "): -(" + formatPriceString(discPrice) + ")");
    System.out.println("GRAND TOTAL: " + formatPriceString(grandTotal));
  }

  private String getEntryPriceString(Map.Entry<Orderable, Integer> entry) {
    /* "unit price x quantity", before discounts */
    return entry.getKey().getPriceString() + " x " + entry.getValue().toString();
  }

  private double calcNetTotal() {
    double total = 0;
    for (Map.Entry<Orderable, Integer> entry : order.getOrderables().entrySet()) {
      /* Quantity x Raw Price */
      total += (entry.getValue() * entry.getKey().getPrice());
    }
    return total;
  }

  private String formatPriceString(double price) {
    return String.format("$ %.2f", price);
  }
}

/*
Orders:

Entry item blah blah
                $2.40 x 2
Entry item blah blah
                $2.40 x 3
Entry item blah blah
                $2.40 x 1

NET TOTAL: $132.21
DISOUNT (5%): -($10.20)
GST (7%): $20.00

GRAND TOTAL: $140.21
*/
