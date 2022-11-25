package com.travl.dtos.request;

import com.travl.enums.TravelMode;
import lombok.Data;

import java.util.List;

@Data
public class CreateProposalRequestData {
    private String source;
    private String destination;
    private String startDate;
    private String endDate;
    private TravelMode travelMode;
    private List<String> places;
    private List<Activity> activities;
}
