package com.ebay.ads.adserver.model;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import com.ebay.ads.adserver.model.bo.Ad;
import com.ebay.ads.adserver.model.bo.Click;
import com.ebay.ads.adserver.model.bo.Impression;
import org.junit.Test;

public class AdTest {

  @Test
  public void addImpression() {
    Ad ad = new Ad();
    ad.addImpression(new Impression());
    assertEquals(1, ad.getImpressions().size());
  }

  @Test
  public void addClick() {
    Ad ad = new Ad();
    ad.addClick(new Click());
    assertEquals(1, ad.getClicks().size());
  }

  @Test
  public void getClicksToImpressionsRatio_adHasClicksAndImpressions_expectProperRatioCalculated() {
    Ad ad = new Ad();
    ad.addClick(new Click());
    ad.addImpression(new Impression());
    ad.addImpression(new Impression());
    float clicksToImpressionsRatio = ad.getClicksToImpressionsRatio();
    assertEquals(0.5, clicksToImpressionsRatio, 0);
  }

  @Test
  public void getClicksToImpressionsRatio_adDoesNotHaveClicksAndImpressions_expectRatioOfZero() {
    Ad ad = new Ad();
    float clicksToImpressionsRatio = ad.getClicksToImpressionsRatio();
    assertEquals(0, clicksToImpressionsRatio, 0);
  }

}