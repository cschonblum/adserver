package com.ebay.ads.adserver.repository;

import com.ebay.ads.adserver.model.bo.Ad;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdRepository extends MongoRepository<Ad, String> {

}

