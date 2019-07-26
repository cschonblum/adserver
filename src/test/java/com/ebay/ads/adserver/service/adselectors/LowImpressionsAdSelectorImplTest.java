package com.ebay.ads.adserver.service.adselectors;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
public class LowImpressionsAdSelectorImplTest {

  private LowImpressionsAdSelectorImpl selector;

  @Before
  public void init() {
    selector = new LowImpressionsAdSelectorImpl();
  }

  @Test
  public void selectAd_noAdsExist_expectNoAdReturned() {
    Optional<Ad> optionalAd = selector.selectAd(Lists.newArrayList());
    assertFalse(optionalAd.isPresent());
  }

  @Test
  public void selectAd_adDoesNotExistsWithLowImpressions_expectNoAdReturned() {
    Ad adWithHighImpressions = mock(Ad.class);
    List mockListWithHighImpressions = mock(List.class);
    when(mockListWithHighImpressions.size()).thenReturn(1000);
    when(adWithHighImpressions.getImpressions()).thenReturn(mockListWithHighImpressions);

    List<Ad> ads = Lists.newArrayList(adWithHighImpressions);
    Optional<Ad> optionalAd = selector.selectAd(ads);
    assertFalse(optionalAd.isPresent());
  }

  @Test
  public void selectAd_adExistsWithLowImpressions_expectAdReturned() {
    Ad adWithLowImpressions = Ad.builder().impressions(Lists.newArrayList()).build();

    List<Ad> ads = Lists.newArrayList(adWithLowImpressions);
    Optional<Ad> optionalAd = selector.selectAd(ads);
    assertTrue(optionalAd.isPresent());
  }

}