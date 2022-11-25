package com.travl.dtos.request;

import lombok.Data;

@Data
public class CreateProposalVoteRequestData {
    private Long id;
    private Long proposalId;
    private String userName;
    private Integer vote;
}
