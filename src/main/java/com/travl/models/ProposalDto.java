package com.travl.models;

import com.travl.enums.ProposalStatus;
import com.travl.enums.TravelMode;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Setter
@Getter
@Data
public class ProposalDto {
    private Long id;
    private String source;
    private String destination;
    private Date startDate;
    private Date endDate;
    private TravelMode travelMode;
    private Double vote;
    private Integer votersCount;
    private ProposalStatus status;
}
