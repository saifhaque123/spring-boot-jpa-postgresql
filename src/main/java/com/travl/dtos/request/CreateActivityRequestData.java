package com.travl.dtos.request;

import com.travl.enums.ActivityType;
import lombok.Data;

@Data
public class CreateActivityRequestData {
   private Long id;
   private Long placeId;
   private String activityName;
   private ActivityType activityType;
}
