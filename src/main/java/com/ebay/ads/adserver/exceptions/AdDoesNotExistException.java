package com.ebay.ads.adserver.exceptions;

public class AdDoesNotExistException extends RuntimeException {

  public AdDoesNotExistException(String adId) {
    super("Ad with id: " + adId + " does not exist");
  }
}
