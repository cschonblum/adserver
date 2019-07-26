package com.ebay.ads.adserver.controller;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

import com.ebay.ads.adserver.exceptions.AdAlreadyExistsException;
import com.ebay.ads.adserver.exceptions.AdDoesNotExistException;
import com.ebay.ads.adserver.exceptions.NoAdsExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * The ControllerAdvice is a centralized place to handle all the application level exceptions
 */
@ControllerAdvice
@Slf4j
public class AdServiceErrorAdvice {

  @ExceptionHandler({AdAlreadyExistsException.class})
  public ResponseEntity<String> handleAdAlreadyExistsException(AdAlreadyExistsException e) {
    return error(UNPROCESSABLE_ENTITY, e);
  }

  @ExceptionHandler({AdDoesNotExistException.class, NoAdsExistException.class})
  public ResponseEntity<String> handleAdDoesNotExistException(RuntimeException e) {
    return error(NOT_FOUND, e);
  }

  @ExceptionHandler({RuntimeException.class})
  public ResponseEntity<String> handleRunTimeException(RuntimeException e) {
    return error(INTERNAL_SERVER_ERROR, e);
  }

  private ResponseEntity<String> error(HttpStatus status, Exception e) {
    log.error("Exception : ", e);
    return ResponseEntity.status(status).body(e.getMessage());
  }

}
