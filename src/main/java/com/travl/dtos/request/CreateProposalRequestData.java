package com.travl.dtos.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.travl.enums.TravelMode;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;

@Data
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CreateProposalRequestData {
    private String source;
    private String destination;
    private Date startDate;
    private Date endDate;
    private TravelMode travelMode;
}
