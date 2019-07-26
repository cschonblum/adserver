package com.ebay.ads.adserver.model.request;

import java.util.Map;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewClickRequest {

  @NotEmpty
  private String timestamp;
  //Since the request is a contract with the client, we do not always want to have to change the request contract when additional parameters
  //are needed. This map of request parameters enables this flexibility. (Can do this in other request objects as well)
  //The converter will be responsible for extracting these parameters
  //Currently I am implementing basic validation with the NotEmpty. In the future, possible to expand the validation
  @NotEmpty
  @Singular
  private Map<String, String> requestParams;

}
