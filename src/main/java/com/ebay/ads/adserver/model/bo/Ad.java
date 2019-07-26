package com.ebay.ads.adserver.model.bo;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.CollectionUtils;

@Data
@Document(collection = "ads")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ad {

  @Id
  private String adId;
  private String content;
  private String image;
  private String category;
  // Can user JsonIgnore so wont overflow the response with many clicks and impressions
  // For this exercise, I want to return the clicks and impressions to show that the simulation works properly
  // @JsonIgnore
  private List<Click> clicks;
  // @JsonIgnore
  private List<Impression> impressions;

  //Can add many more fields such as creation date, and status (whether active or inactive)

  public void addImpression(Impression impression) {
    if (Objects.isNull(impressions)) {
      impressions = Lists.newArrayList();
    }
    impressions.add(impression);
  }

  public void addClick(Click click) {
    if (Objects.isNull(clicks)) {
      clicks = Lists.newArrayList();
    }
    clicks.add(click);
  }

  public float getClicksToImpressionsRatio() {
    if (CollectionUtils.isEmpty(clicks) || CollectionUtils.isEmpty(impressions)) {
      return 0;
    }
    return (float) clicks.size() / impressions.size();
  }
}
