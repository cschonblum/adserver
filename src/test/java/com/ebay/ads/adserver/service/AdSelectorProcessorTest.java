package com.ebay.ads.adserver.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import com.ebay.ads.adserver.exceptions.NoAdsExistException;
import com.ebay.ads.adserver.model.bo.Ad;
import com.ebay.ads.adserver.repository.AdRepository;
import com.ebay.ads.adserver.service.adselectors.AdSelector;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AdSelectorProcessorTest {

  @Mock
  private AdSelector adSelector1;
  @Mock
  private AdSelector adSelector2;
  @Mock
  private AdRepository adRepository;
  private AdSelectorProcessor adSelectorProcessor;

  @Before
  public void init() {
    List<AdSelector> adSelectors = Lists.newArrayList(adSelector1, adSelector2);
    adSelectorProcessor = new AdSelectorProcessor(adRepository, adSelectors);
  }

  @Test
  public void selectAnyAd_verifyDifferentSelectorInteractionEachTimeProcessorCalled() {

    List<Ad> ads = Lists.newArrayList(new Ad());
    when(adRepository.findAll()).thenReturn(ads);
    when(adSelector1.selectAd(ads)).thenReturn(Optional.of(new Ad()));
    when(adSelector2.selectAd(ads)).thenReturn(Optional.of(new Ad()));
    for (int i = 0; i < 2; i++) {
      adSelectorProcessor.selectAnyAd();
    }
    Mockito.verify(adSelector2, times(1)).selectAd(ads);
    Mockito.verify(adSelector1, times(1)).selectAd(ads);
  }

  @Test
  public void selectAnyAd_verifyAllSelectorsInteractionsWhenCalledAgain() {

    List<Ad> ads = Lists.newArrayList(new Ad());
    when(adRepository.findAll()).thenReturn(ads);
    when(adSelector1.selectAd(ads)).thenReturn(Optional.of(new Ad()));
    when(adSelector2.selectAd(ads)).thenReturn(Optional.of(new Ad()));
    for (int i = 0; i < 4; i++) {
      adSelectorProcessor.selectAnyAd();
    }
    Mockito.verify(adSelector2, times(2)).selectAd(ads);
    Mockito.verify(adSelector1, times(2)).selectAd(ads);
  }

  @Test
  public void selectAnyAd_selectorReturnsAd_expectAd() {
    Ad expectedAd = Ad.builder().adId("123").build();
    List<Ad> ads = Lists.newArrayList(expectedAd);
    when(adRepository.findAll()).thenReturn(ads);
    when(adSelector1.selectAd(ads)).thenReturn(Optional.empty());
    when(adSelector2.selectAd(ads)).thenReturn(Optional.of(expectedAd));
    Ad selectedAd = adSelectorProcessor.selectAnyAd();
    assertEquals(expectedAd, selectedAd);
  }

  @Test(expected = NoAdsExistException.class)
  public void selectAnyAd_noAdsExist_expectException() {
    when(adRepository.findAll()).thenReturn(Lists.newArrayList());
    adSelectorProcessor.selectAnyAd();
  }
}