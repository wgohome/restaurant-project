package app.utilities;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import app.entities.Order;


public class RevenueReport {
  static Map<String, TemporalAdjuster> ADJUSTERS = getAdjusters();

  private List<Order> orders;

  public RevenueReport(List<Order> ord) {
    orders = ord;
  }

  private static Map<String, TemporalAdjuster> getAdjusters() {
    Map<String, TemporalAdjuster> ADJUSTERS = new HashMap<>();
    ADJUSTERS.put("day",TemporalAdjusters.ofDateAdjuster(d->d)); // identity
    ADJUSTERS.put("week",TemporalAdjusters.previousOrSame(DayOfWeek.of(1)));ADJUSTERS.put("month",TemporalAdjusters.firstDayOfMonth());
    ADJUSTERS.put("year",TemporalAdjusters.firstDayOfYear());
    return ADJUSTERS;
  }

  public void printReport(String frequency) {
    if (frequency != "day" && frequency != "month") {
      System.out.println("Invalid time frequency!");
      return;
    }
    System.out.println("Revenue report by " + frequency);
    Map<LocalDate, List<Order>> result = orders.stream()
        .collect(Collectors.groupingBy(item -> item.getStart().toLocalDate().with(ADJUSTERS.get(frequency))));

    double revenue;
    /* Iterate over every group of orders per time period */
    for (Map.Entry<LocalDate, List<Order>> group:result.entrySet()) {
      revenue = 0;
      /* Iterate every order */
      for (Order order:group.getValue()) {
        if (order.getInvoice() != null) { /* If order is paid */
          revenue += order.getInvoice().calcGrandTotal();
        }
      }
      System.out.println(group.getKey() + ": " + String.format("$ %.2f", revenue));
    }
  }
}
