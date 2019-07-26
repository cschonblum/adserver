package com.ebay.ads.adserver.converters;

import com.ebay.ads.adserver.model.bo.Click;
import com.ebay.ads.adserver.model.bo.User;
import com.ebay.ads.adserver.model.request.NewClickRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NewClickRequestToClickConverter implements Converter<NewClickRequest, Click> {

  @Override
  public Click convert(NewClickRequest newClickRequest) {

    String userId = newClickRequest.getRequestParams().get("userId");
    String location = newClickRequest.getRequestParams().get("location");
    User user = User.builder()
        .userId(userId)
        .location(location)
        .build();

    return Click.builder()
        .timestamp(Long.valueOf(newClickRequest.getTimestamp()))
        .user(user)
        .build();
  }
}
