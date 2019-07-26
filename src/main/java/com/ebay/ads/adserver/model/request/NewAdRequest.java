package com.ebay.ads.adserver.model.request;

import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewAdRequest {

  @NotEmpty
  private String adId;
  private String content;
  private String image;
  private String category;

}


