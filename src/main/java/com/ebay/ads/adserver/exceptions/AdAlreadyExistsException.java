package com.ebay.ads.adserver.exceptions;

public class AdAlreadyExistsException extends RuntimeException {

  public AdAlreadyExistsException(String adId) {
    super("Ad with id: " + adId + " already exists");
  }
}
