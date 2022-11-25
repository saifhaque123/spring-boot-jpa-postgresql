package com.travl.dtos.response;

import com.travl.enums.ProposalStatus;
import com.travl.enums.TravelMode;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;

@Setter
@Getter
@Data
public class ProposalResponseData {
    private Long id;
    private String source;
    private String destination;
    private LocalDate startDate;
    private LocalDate endDate;
    private TravelMode travelMode;
    private Double vote;
    private Integer votersCount;
    private ProposalStatus status;
}
