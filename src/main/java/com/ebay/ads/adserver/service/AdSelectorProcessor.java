package com.ebay.ads.adserver.service;

import com.ebay.ads.adserver.exceptions.NoAdsExistException;
import com.ebay.ads.adserver.model.bo.Ad;
import com.ebay.ads.adserver.repository.AdRepository;
import com.ebay.ads.adserver.service.adselectors.AdSelector;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * Explanation about how ad selector algorithm works -
 * There are many different AdSelector implementations, for example a selector which returns the ad that has the most clicks.
 * The algorithm implements a round robin approach to choosing an ad selector. This way a different ad selector is responsible
 * to return an ad with each call to the API.
 * If no ad is returned by the ad selector, the next ad selector will have its turn.
 * Could have also added APIs for each selector and enabled the client to choose which type of ad interested in, but implemented processor
 * to manage it instead.
 */
@Component
@Slf4j
public class AdSelectorProcessor {

  private final AdRepository adRepository;
  private final List<AdSelector> adSelectors;
  private static Iterator<AdSelector> SELECTOR_ITERATOR;

  @Autowired
  public AdSelectorProcessor(AdRepository adRepository, List<AdSelector> adSelectors) {
    this.adRepository = adRepository;
    this.adSelectors = adSelectors;
    SELECTOR_ITERATOR = adSelectors.iterator();
  }

  public Ad selectAnyAd() {

    List<Ad> allAds = adRepository.findAll();
    if (CollectionUtils.isEmpty(allAds)) {
      throw new NoAdsExistException();
    }
    return getAdFromAdSelector(allAds);
  }

  /**
   * For the purpose of the exercise, I made sure that there is at least 1 selector which will return an ad.
   * (The LowImpressionsAdSelector will always return an ad that does not have any impressions)
   * In production code, I understand that this assumption is not good practice.
   * A possible solution would be to implement a break in the loop after X amount of tries.
   * @param ads
   * @return Ad
   */
  private Ad getAdFromAdSelector(List<Ad> ads) {

    while (SELECTOR_ITERATOR.hasNext()) {
      AdSelector adSelector = SELECTOR_ITERATOR.next();
      Optional<Ad> ad = adSelector.selectAd(ads);
      if (ad.isPresent()) {
        return ad.get();
      }
      return getAdFromAdSelector(ads);
    }
    SELECTOR_ITERATOR = adSelectors.iterator();
    return getAdFromAdSelector(ads);
  }

}
