package com.travl.dtos.request;

import lombok.Data;

@Data
public class ProposalVoteRequest {
    private Long id;
    private Long proposalId;
    private String userName;
    private Integer vote;
}
