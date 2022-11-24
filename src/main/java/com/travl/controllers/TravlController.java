package com.travl.controllers;

import com.travl.dtos.request.CreateProposalRequestData;
import com.travl.enums.TravelMode;
import com.travl.models.Proposal;
import com.travl.repositories.ProposalRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class TravlController {
    private final ProposalRepository proposalRepository;

    @PostMapping(
            path = "/proposals",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Proposal> createProposal(
            @RequestBody final CreateProposalRequestData requestData) {
        Proposal proposal = Proposal.builder()
                .source(requestData.getSource())
                .destination(requestData.getDestination())
                .startDate(requestData.getStartDate())
                .endDate(requestData.getEndDate())
                .travelMode(requestData.getTravelMode())
                .build();
        proposalRepository.save(proposal);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(proposal);
    }


}
