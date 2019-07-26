package com.ebay.ads.adserver.converters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import com.ebay.ads.adserver.model.bo.Ad;
import com.ebay.ads.adserver.model.request.NewAdRequest;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

@RunWith(SpringRunner.class)
public class NewAdRequestToAdConverterTest {

  private NewAdRequestToAdConverter converter;

  @Before
  public void init(){
    converter = new NewAdRequestToAdConverter();
  }

  @Test
  public void convert() {
    String adId = "123";
    String content = "Test content";
    String category = "Category 1";
    String image = "Image test";
    NewAdRequest newAdRequest = NewAdRequest.builder()
        .adId(adId)
        .content(content)
        .category(category)
        .image(image)
        .build();
    Ad expectedAd = Ad.builder()
        .adId(adId)
        .content(content)
        .category(category)
        .image(image)
        .clicks(Lists.newArrayList())
        .impressions(Lists.newArrayList())
        .build();
    Ad convertedAd = converter.convert(newAdRequest);
    assertNotNull(convertedAd.getClicks());
    assertNotNull(convertedAd.getImpressions());
    assertEquals(expectedAd, convertedAd);
  }
}