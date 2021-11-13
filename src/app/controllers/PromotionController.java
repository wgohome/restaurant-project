package app.controllers;

import java.util.ArrayList;
import java.util.List;

import app.entities.Promotion;
import app.interfaces.EntityStorable;

public class PromotionController extends Controller {
  public PromotionController() {
    super("data/promotion.dat");
  }

  @Override
  protected List<EntityStorable> seedList() {
    List<EntityStorable> promotions = new ArrayList<EntityStorable>();
    Promotion promo1 = new Promotion("Promo 1", 20);
    promotions.add(promo1);
    return promotions;
  }
}
