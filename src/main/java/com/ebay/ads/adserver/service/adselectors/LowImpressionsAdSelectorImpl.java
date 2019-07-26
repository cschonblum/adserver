package com.ebay.ads.adserver.service.adselectors;

import com.ebay.ads.adserver.model.bo.Ad;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * This selector returns one of the ads that has a low number of impressions
 * This will help the ad gain more impressions
 */
@Component
public class LowImpressionsAdSelectorImpl implements AdSelector {

  private static final int LOW_NUMBER_IMPRESSIONS = 500;
  private final Random random;

  public LowImpressionsAdSelectorImpl() {
    this.random = new Random();
  }

  @Override
  public Optional<Ad> selectAd(List<Ad> ads) {

    List<Ad> lowImpressionsAds = ads.stream()
        .filter(this::isLowImpressionAd)
        .collect(Collectors.toList());
    if (CollectionUtils.isEmpty(lowImpressionsAds)) {
      return Optional.empty();
    }
    Ad selectedAd = lowImpressionsAds.get(random.nextInt(lowImpressionsAds.size()));
    return Optional.of(selectedAd);
  }

  private boolean isLowImpressionAd(Ad ad) {
    return ad.getImpressions().size() < LOW_NUMBER_IMPRESSIONS;
  }
}
