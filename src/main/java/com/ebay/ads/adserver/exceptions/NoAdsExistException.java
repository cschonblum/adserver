package com.ebay.ads.adserver.exceptions;

public class NoAdsExistException extends RuntimeException {

  public NoAdsExistException() {
    super("No ads exist in database");
  }
}
