package com.ebay.ads.adserver.controller;

import com.ebay.ads.adserver.model.bo.Ad;
import com.ebay.ads.adserver.model.bo.Click;
import com.ebay.ads.adserver.model.bo.Impression;
import com.ebay.ads.adserver.model.request.NewAdRequest;
import com.ebay.ads.adserver.model.request.NewClickRequest;
import com.ebay.ads.adserver.model.request.NewImpressionRequest;
import com.ebay.ads.adserver.service.AdService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ads")
public class AdsController {

  private final AdService adService;
  private final ConversionService conversionService;

  @Autowired
  public AdsController(AdService adService, ConversionService conversionService) {
    this.adService = adService;
    this.conversionService = conversionService;
  }

  @RequestMapping("/createAd")
  public Ad createAd(@RequestBody @Valid NewAdRequest adRequest) {
    Ad ad = conversionService.convert(adRequest, Ad.class);
    return adService.createAd(ad);
  }

  @DeleteMapping("/deleteAd/{adId}")
  public void deleteAd(@PathVariable String adId) {
    adService.deleteAd(adId);
  }

  @RequestMapping("/getAnyAd")
  public Ad getAnyAd() {
    return adService.getAnyAd();
  }

  @RequestMapping("/registerImpression/{adId}")
  public void registerImpression(@RequestBody @Valid NewImpressionRequest impressionRequest, @PathVariable String adId) {
    Impression impression = conversionService.convert(impressionRequest, Impression.class);
    adService.registerImpression(impression, adId);
  }

  @RequestMapping("/registerClick/{adId}")
  public void registerClick(@RequestBody @Valid NewClickRequest clickRequest, @PathVariable String adId) {
    Click click = conversionService.convert(clickRequest, Click.class);
    adService.registerClick(click, adId);
  }
}
