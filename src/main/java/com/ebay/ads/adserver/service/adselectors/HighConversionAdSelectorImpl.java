package com.ebay.ads.adserver.service.adselectors;

import com.ebay.ads.adserver.model.bo.Ad;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * This selector returns one of the ads with the highest conversion rate of clicks to impressions
 */
@Component
public class HighConversionAdSelectorImpl implements AdSelector {

  private static final double CONVERSION_RATE = 0.01;
  private final Random random;

  public HighConversionAdSelectorImpl() {
    this.random = new Random();
  }

  @Override
  public Optional<Ad> selectAd(List<Ad> ads) {

    List<Ad> highConversionAds = ads.stream()
        .filter(this::isHighConversionAd)
        .collect(Collectors.toList());
    if (CollectionUtils.isEmpty(highConversionAds)) {
      return Optional.empty();
    }
    Ad selectedAd = highConversionAds.get(random.nextInt(highConversionAds.size()));
    return Optional.of(selectedAd);
  }

  private boolean isHighConversionAd(Ad ad) {
    return ad.getClicksToImpressionsRatio() > CONVERSION_RATE;
  }
}
