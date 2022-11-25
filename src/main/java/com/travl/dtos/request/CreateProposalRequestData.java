package com.travl.dtos.request;

import com.travl.enums.TravelMode;
import lombok.Data;
@Data
public class CreateProposalRequestData {
    private String source;
    private String destination;
    private String startDate;
    private String endDate;
    private TravelMode travelMode;
}
