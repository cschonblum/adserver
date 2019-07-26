package com.ebay.ads.adserver.converters;

import com.ebay.ads.adserver.model.bo.Ad;
import com.ebay.ads.adserver.model.request.NewAdRequest;
import com.google.common.collect.Lists;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NewAdRequestToAdConverter implements Converter<NewAdRequest, Ad> {

  @Override
  public Ad convert(NewAdRequest adRequest) {
    return Ad.builder()
        .adId(adRequest.getAdId())
        .content(adRequest.getContent())
        .clicks(Lists.newArrayList())
        .impressions(Lists.newArrayList())
        .image(adRequest.getImage())
        .category(adRequest.getCategory())
        .build();
  }
}
