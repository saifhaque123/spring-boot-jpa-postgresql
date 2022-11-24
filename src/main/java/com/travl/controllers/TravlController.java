package com.travl.controllers;

import com.travl.dtos.request.CreateProposalRequestData;
import com.travl.enums.TravelMode;
import com.travl.models.Proposal;
import com.travl.models.ProposalDto;
import com.travl.models.ResponseData;
import com.travl.repositories.ProposalRepository;
import com.travl.service.ProposalService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class TravlController {
    private final ProposalRepository proposalRepository;

    @Autowired
    ProposalService proposalService;

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

    @GetMapping(path = "/proposals/{proposalId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> getProposal(@PathVariable final Long proposalId){
        ResponseData response = new ResponseData();
        ProposalDto proposal = proposalService.getProposal(proposalId);
        if(null!=proposal){
            response.setSuccess(true);
            response.setData(proposal);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else{
            response.setSuccess(false);
            response.setErrorMessage("No Proposal found for proposal id "+proposalId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

    }


}
