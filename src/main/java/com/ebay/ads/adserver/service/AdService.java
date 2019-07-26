package com.ebay.ads.adserver.service;

import com.ebay.ads.adserver.model.bo.Ad;
import com.ebay.ads.adserver.model.bo.Click;
import com.ebay.ads.adserver.model.bo.Impression;
import java.util.List;

public interface AdService {

  Ad createAd(Ad ad);

  void deleteAd(String adId);

  void registerImpression(Impression impression, String adId);

  void registerClick(Click click, String adId);

  Ad getAnyAd();

  List<Ad> getAllAds();
}
