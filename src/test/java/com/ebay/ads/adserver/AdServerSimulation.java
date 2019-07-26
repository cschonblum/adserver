package com.ebay.ads.adserver;

import com.ebay.ads.adserver.model.bo.Ad;
import com.ebay.ads.adserver.model.bo.User;
import com.ebay.ads.adserver.model.request.NewAdRequest;
import com.ebay.ads.adserver.model.request.NewClickRequest;
import com.ebay.ads.adserver.model.request.NewImpressionRequest;
import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class AdServerSimulation {

  private Random random;
  private RestTemplate restTemplate;
  private List<User> users;
  private static final String END_POINT = "http://localhost:8080";
  private static final String REGISTER_IMPRESSION_URI = END_POINT + "/ads/registerImpression/{adId}";
  private static final String REGISTER_CLICK_URI = END_POINT + "/ads/registerClick/{adId}";
  private static final String CREATE_AD_URI = END_POINT + "/ads/createAd/";
  private static final String GET_ALL_ADS = END_POINT + "/admin/getAllAds";
  private static final int NUMBER_OF_ADS = 10;
  private static final int NUMBER_OF_IMPRESSIONS = 1500;
  private static final int NUMBER_OF_USERS = 100;
  private static final int NUMBER_OF_IMPRESSIONS_BEFORE_CLICK = 100;

  @Before
  public void init() {
    random = new Random();
    restTemplate = new RestTemplate();
    initUsers();
  }

  @Test
  public void simulateService() {
    List<Ad> ads = createAds();
    addImpressionsAndClicks(ads);
    System.out.println("PRINTING ALLS ADS");
    Arrays.stream(getAllAds()).forEach(System.out::println);
  }

  private List<Ad> createAds() {
    List<Ad> ads = Lists.newArrayList();

    for (int i = 0; i < NUMBER_OF_ADS; i++) {
      String adId = UUID.randomUUID().toString();
      NewAdRequest newAdRequest = NewAdRequest.builder()
          .adId(adId)
          .content("Test Ad " + adId)
          .build();
      ads.add(restTemplate.postForObject(CREATE_AD_URI, newAdRequest, Ad.class));
    }

    return ads;
  }

  /**
   * This method creates X number of impressions. Because the ad is not always clicked on, for every
   * 100 impressions, there will be 1 click (This is just a random way to register the clicks)
   */
  private void addImpressionsAndClicks(List<Ad> ads) {
    for (int i = 0; i < NUMBER_OF_IMPRESSIONS; i++) {
      String randomAdId = getRandomAdId(ads);
      NewImpressionRequest impression = NewImpressionRequest.builder()
          .timestamp(getTimestamp())
          .build();
      addImpressionToAd(randomAdId, impression);
      //for every X impressions there will be one click
      if (i % NUMBER_OF_IMPRESSIONS_BEFORE_CLICK == 0) {
        NewClickRequest click = generateClickWithRandomUser();
        addClickToAd(randomAdId, click);
      }
    }
  }

  private void addClickToAd(String randomAdId, NewClickRequest click) {
    Map<String, String> params = new HashMap<>();
    params.put("adId", randomAdId);
    restTemplate.put(REGISTER_CLICK_URI, click, params);
  }

  private NewClickRequest generateClickWithRandomUser() {
    int userIndex = random.nextInt(users.size());
    User user = users.get(userIndex);
    return NewClickRequest.builder()
        .timestamp(getTimestamp())
        .requestParam("userId", user.getUserId())
        .requestParam("location", user.getLocation())
        .build();
  }

  private void addImpressionToAd(String randomAdId, NewImpressionRequest impression) {
    Map<String, String> params = new HashMap<>();
    params.put("adId", randomAdId);
    restTemplate.put(REGISTER_IMPRESSION_URI, impression, params);
  }

  private String getTimestamp() {
    Date date = new Date();
    return String.valueOf(date.getTime());
  }

  private String getRandomAdId(List<Ad> ads) {
    int adIndex = random.nextInt(ads.size());
    return ads.get(adIndex).getAdId();
  }

  private void initUsers() {
    users = Lists.newArrayList();
    for (int i = 0; i < NUMBER_OF_USERS; i++) {
      User user = User.builder().userId(UUID.randomUUID().toString()).location("NY").build();
      users.add(user);
    }
  }

  private Ad[] getAllAds() {
    return restTemplate.getForObject(GET_ALL_ADS, Ad[].class);
  }
}
