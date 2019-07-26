package com.ebay.ads.adserver.service;

import com.ebay.ads.adserver.exceptions.AdDoesNotExistException;
import com.ebay.ads.adserver.exceptions.AdAlreadyExistsException;
import com.ebay.ads.adserver.exceptions.NoAdsExistException;
import com.ebay.ads.adserver.model.bo.Ad;
import com.ebay.ads.adserver.model.bo.Click;
import com.ebay.ads.adserver.model.bo.Impression;
import com.ebay.ads.adserver.repository.AdRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class AdServiceImpl implements AdService {  

  private final AdRepository adRepository;
  private final AdSelectorProcessor adSelectorProcessor;

  @Autowired
  public AdServiceImpl(AdRepository adRepository, AdSelectorProcessor adSelectorProcessor) {
    this.adRepository = adRepository;
    this.adSelectorProcessor = adSelectorProcessor;
  }

  @Override
  public Ad createAd(Ad ad) {
    if (adRepository.existsById(ad.getAdId())) {
      throw new AdAlreadyExistsException(ad.getAdId());
    }
    return adRepository.save(ad);
  }

  @Override
  public void deleteAd(String adId) {
    if (!adRepository.existsById(adId)) {
      throw new AdDoesNotExistException(adId);
    }
    adRepository.deleteById(adId);
  }

  @Override
  public void registerImpression(Impression impression, String adId) {
    Optional<Ad> byId = adRepository.findById(adId);
    if (!byId.isPresent()) {
      throw new AdDoesNotExistException(adId);
    }
    Ad ad = byId.get();
    ad.addImpression(impression);
    adRepository.save(ad);
  }

  @Override
  public void registerClick(Click click, String adId) {
    Optional<Ad> byId = adRepository.findById(adId);
    if (!byId.isPresent()) {
      throw new AdDoesNotExistException(adId);
    }
    Ad ad = byId.get();
    ad.addClick(click);
    adRepository.save(ad);
  }

  @Override
  public Ad getAnyAd() {
    return adSelectorProcessor.selectAnyAd();
  }

  @Override
  public List<Ad> getAllAds() {
    List<Ad> allAds = adRepository.findAll();
    if (CollectionUtils.isEmpty(allAds)) {
      throw new NoAdsExistException();
    }
    return allAds;
  }
}
