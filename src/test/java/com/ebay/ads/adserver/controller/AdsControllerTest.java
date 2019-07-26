package com.ebay.ads.adserver.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ebay.ads.adserver.exceptions.NoAdsExistException;
import com.ebay.ads.adserver.model.bo.Ad;
import com.ebay.ads.adserver.model.request.NewAdRequest;
import com.ebay.ads.adserver.model.request.NewClickRequest;
import com.ebay.ads.adserver.model.request.NewImpressionRequest;
import com.ebay.ads.adserver.service.AdService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(AdsController.class)
public class AdsControllerTest {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  @Autowired
  private MockMvc mvc;
  @MockBean
  private AdService adService;

  @Test
  @SneakyThrows
  public void createAd_adRequestIsValid_expectOk() {
    String adId = "123";
    NewAdRequest newAdRequest = NewAdRequest.builder().adId(adId).build();
    Ad createdAd = Ad.builder().adId(adId).build();
    given(adService.createAd(any())).willReturn(createdAd);
    mvc.perform(
        post("/ads/createAd")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .content(asJsonString(newAdRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.adId").value(adId));
  }

  @Test
  @SneakyThrows
  public void createAd_adRequestNotValid_expectError() {
    NewAdRequest ad = NewAdRequest.builder().build();
    mvc.perform(post("/ads/createAd")
        .content(asJsonString(ad))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  @SneakyThrows
  public void deleteAd_deleteRequestValid_expectOk() {
    mvc.perform(delete("/ads/deleteAd/{adId}", "123") )
        .andExpect(status().isOk());
  }

  @Test
  @SneakyThrows
  public void getAnyAd_serviceReturnsAd_expectOk() {
    String adId = "123";
    Ad ad = Ad.builder().adId(adId).build();
    given(adService.getAnyAd()).willReturn(ad);
    mvc.perform(get("/ads/getAnyAd")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.adId").value(adId));
  }

  @Test
  @SneakyThrows
  public void getAnyAd_noAdsExist_expectError() {
    given(adService.getAnyAd()).willThrow(NoAdsExistException.class);
    mvc.perform(get("/ads/getAnyAd")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }


  @Test
  @SneakyThrows
  public void registerImpression_impressionRequestIsValid_expectOk() {
    String adId = "123";
    NewImpressionRequest impressionRequest = NewImpressionRequest.builder().timestamp("123456").build();
    mvc.perform(
        put("/ads/registerImpression/{adId}", adId)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .content(asJsonString(impressionRequest)))
        .andExpect(status().isOk());
  }

  @Test
  @SneakyThrows
  public void registerClick_clickRequestIsValid_expectOk() {
    String adId = "123";
    NewClickRequest clickRequest = NewClickRequest.builder()
        .timestamp("123456")
        .requestParam("userId", "abc")
        .build();
    mvc.perform(
        put("/ads/registerClick/{adId}", adId)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .content(asJsonString(clickRequest)))
        .andExpect(status().isOk());
  }

  @Test
  @SneakyThrows
  public void registerClick_ClickRequestIsInvalidMissingParams_expectError() {
    String adId = "123";
    NewClickRequest clickRequest = NewClickRequest.builder()
        .timestamp("123456")
        .build();
    mvc.perform(
        put("/ads/registerClick/{adId}", adId)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .content(asJsonString(clickRequest)))
        .andExpect(status().isBadRequest());
  }

  @SneakyThrows
  private String asJsonString(Object obj) {
    return OBJECT_MAPPER.writeValueAsString(obj);
  }
}