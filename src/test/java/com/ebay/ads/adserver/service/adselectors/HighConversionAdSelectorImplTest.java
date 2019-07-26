package com.ebay.ads.adserver.service.adselectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.ebay.ads.adserver.model.bo.Ad;
import com.ebay.ads.adserver.model.bo.Click;
import com.ebay.ads.adserver.model.bo.Impression;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class HighConversionAdSelectorImplTest {

  private HighConversionAdSelectorImpl selector;

  @Before
  public void init() {
    selector = new HighConversionAdSelectorImpl();
  }

  @Test
  public void selectAd_noAdsExist_expectNoAdReturned() {
    Optional<Ad> optionalAd = selector.selectAd(Lists.newArrayList());
    assertFalse(optionalAd.isPresent());
  }

  @Test
  public void selectAd_adExistsWithHighConversion_expectAdReturned() {

    List<Click> clicks = Lists.newArrayList(new Click());
    List<Impression> impressions = Lists.newArrayList(new Impression());
    Ad adWithHighConversion = Ad.builder().clicks(clicks).impressions(impressions).build();
    Ad adWithLowConversion = Ad.builder().build();
    List<Ad> ads = Lists.newArrayList(adWithHighConversion, adWithLowConversion);

    Optional<Ad> optionalAd = selector.selectAd(ads);
    assertTrue(optionalAd.isPresent());
    assertEquals(adWithHighConversion, optionalAd.get());
  }

  @Test
  public void selectAd_noAdExistsWithHighConversion_expectNoAdReturned() {

    List<Ad> ads = Lists.newArrayList(new Ad());

    Optional<Ad> optionalAd = selector.selectAd(ads);
    assertFalse(optionalAd.isPresent());
  }
}