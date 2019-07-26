package com.ebay.ads.adserver.service.adselectors;

import com.ebay.ads.adserver.model.bo.Ad;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

/**
 * This selector returns the ad with the most clicks
 */
@Component
public class MostClickedAdSelectorImpl implements AdSelector {  

  @Override
  public Optional<Ad> selectAd(List<Ad> ads) {

    Ad mostClickedAd = ads.stream().max(Comparator.comparingInt(ad -> ad.getClicks().size())).get();
    return Optional.of(mostClickedAd);
  }
}
