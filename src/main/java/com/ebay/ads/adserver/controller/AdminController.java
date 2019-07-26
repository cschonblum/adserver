package com.ebay.ads.adserver.controller;

import com.ebay.ads.adserver.model.bo.Ad;
import com.ebay.ads.adserver.service.AdService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

  private final AdService adService;

  @Autowired
  public AdminController(AdService adService) {
    this.adService = adService;
  }

  @RequestMapping("/getAllAds")
  public List<Ad> getAllAds() {
    return adService.getAllAds();
  }
}
