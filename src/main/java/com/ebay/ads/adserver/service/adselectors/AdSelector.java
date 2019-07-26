package com.ebay.ads.adserver.service.adselectors;

import com.ebay.ads.adserver.model.bo.Ad;
import java.util.List;
import java.util.Optional;

public interface AdSelector {

  Optional<Ad> selectAd(List<Ad> ads);
}