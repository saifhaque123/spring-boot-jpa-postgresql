package com.travl.dtos.request;

import com.travl.enums.ActivityType;
import lombok.Data;

@Data
public class Activity {
    private String activityName;
    private ActivityType activityType;
}
