package com.travl.controllers;

import com.travl.dtos.request.*;
import com.travl.enums.ProposalStatus;
import com.travl.models.*;
import com.travl.repositories.ProposalRepository;
import com.travl.service.ProposalService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        }else{
            response.setSuccess(false);
            response.setErrorMessage("No Proposal found for proposal id "+proposalId);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }


    @GetMapping(path = "/proposals", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> getAllProposal(){
        ResponseData response = new ResponseData();
        List<ProposalDto> proposal = proposalService.getAllProposal();
        if(null!=proposal){
            response.setSuccess(true);
            response.setData(proposal);
        }else{
            response.setSuccess(false);
            response.setErrorMessage("No Proposal found! Please create new proposals ! ");
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(path = "/proposals/{proposalId}/vote", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> createProposalVote(@PathVariable final Long proposalId, @RequestBody final ProposalVoteRequest requestData) {
        ResponseData response = new ResponseData();
        requestData.setProposalId(proposalId);
        ProposalVote proposalVote = proposalService.createProposalVote(requestData);
        if(proposalVote!=null){
            response.setSuccess(true);
            response.setData(proposalVote);
        }else{
            response.setSuccess(false);
            response.setErrorMessage("Error in casting proposal vote : "+requestData.toString());
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PatchMapping (path = "/proposals/{proposalId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> updateProposalStatus(@PathVariable final Long proposalId, @RequestParam final ProposalStatus status) {
        ResponseData response = new ResponseData();
        ProposalDto proposal = proposalService.updateProposalState(proposalId, status);
        if(proposal!=null){
            response.setSuccess(true);
            response.setData(proposal);
        }else{
            response.setSuccess(false);
            response.setErrorMessage("Error in updating proposal : "+proposalId+ " to state : "+ status);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PostMapping(path = "/proposals/{proposalId}/place/{placeId}/activity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> createPlaceActivity(@PathVariable final Long proposalId, @PathVariable final Long placeId, @RequestBody final ActivityRequest requestData) {
        ResponseData response = new ResponseData();
        requestData.setPlaceId(placeId);
        PlaceActivity placeActivity = proposalService.createPlaceActivity(requestData);
        if(placeActivity!=null){
            response.setSuccess(true);
            response.setData(placeActivity);
        }else{
            response.setSuccess(false);
            response.setErrorMessage("Error in creating activity for place id : "+requestData.getPlaceId() +" and proposal id : "+ proposalId);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
