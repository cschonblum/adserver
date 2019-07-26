package com.ebay.ads.adserver.service.adselectors;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.ebay.ads.adserver.model.bo.Ad;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class MostClickedAdSelectorImplTest {

  private MostClickedAdSelectorImpl selector;

  @Before
  public void init(){
    selector = new MostClickedAdSelectorImpl();
  }

  @Test
  public void selectAd_twoAdsExist_expectMostClickedAdReturned() {

    Ad ad1 = mock(Ad.class);
    List mockClicksForAd1 = mock(List.class);
    when(mockClicksForAd1.size()).thenReturn(100);
    when(ad1.getClicks()).thenReturn(mockClicksForAd1);

    Ad ad2 = mock(Ad.class);
    List mockClicksForAd2 = mock(List.class);
    when(mockClicksForAd2.size()).thenReturn(5);
    when(ad1.getClicks()).thenReturn(mockClicksForAd2);

    ArrayList<Ad> ads = Lists.newArrayList(ad1, ad2);

    Optional<Ad> mostClickedAdSelected = selector.selectAd(ads);
    assertEquals(ad1, mostClickedAdSelected.get());
  }
}