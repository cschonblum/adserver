package com.ebay.ads.adserver.converters;

import com.ebay.ads.adserver.model.bo.Impression;
import com.ebay.ads.adserver.model.request.NewImpressionRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NewImpressionRequestToImpressionConverter implements Converter<NewImpressionRequest, Impression> {

  @Override
  public Impression convert(NewImpressionRequest newImpressionRequest) {
    return Impression.builder()
        .timestamp(Long.valueOf(newImpressionRequest.getTimestamp()))
        .build();
  }
}
