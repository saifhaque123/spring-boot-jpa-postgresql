package com.travl.controllers;

import com.travl.dtos.request.CreatePlaceRequestData;
import com.travl.dtos.request.CreatePlaceVoteRequestData;
import com.travl.dtos.request.CreateProposalRequestData;
import com.travl.dtos.response.PlaceVoteResponseData;
import com.travl.dtos.response.ProposalResponseData;
import com.travl.dtos.response.ResponseData;
import com.travl.models.Place;
import com.travl.models.PlaceVote;
import com.travl.models.Proposal;
import com.travl.repositories.PlaceActivityRepository;
import com.travl.repositories.PlaceRepository;
import com.travl.repositories.PlaceVoteRepository;
import com.travl.dtos.request.*;
import com.travl.enums.ProposalStatus;
import com.travl.models.*;
import com.travl.repositories.ProposalRepository;
import com.travl.service.ProposalService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:8080")
public class TravlController {
    private final ProposalRepository proposalRepository;
    private final PlaceRepository placeRepository;
    private final PlaceVoteRepository placeVoteRepository;
    private final PlaceActivityRepository placeActivityRepository;
    private final ProposalService proposalService;

    @PostMapping(
            path = "/proposal",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseUniversal> createProposal(
            @RequestBody final CreateProposalRequestData requestData) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Proposal proposal = Proposal.builder()
                .source(requestData.getSource())
                .destination(requestData.getDestination())
                .startDate(LocalDate.parse(requestData.getStartDate(), formatter))
                .endDate(LocalDate.parse(requestData.getEndDate(), formatter))
                .travelMode(requestData.getTravelMode())
                .build();
        Proposal createdProposal = proposalRepository.save(proposal);
        List<Place> places = new ArrayList<>();
        for(String pl: requestData.getPlaces()){
            Place p = Place.builder()
                    .proposalId(createdProposal.getId())
                    .placeName(pl)
                    .build();
            places.add(p);
        }
        List<Place> updatedPlaces=placeRepository.saveAll(places);
        List<PlaceActivity> activities = new ArrayList<>();
        for(Place p : updatedPlaces){
            for(Activity ay: requestData.getActivities()){
                PlaceActivity placeActivity = PlaceActivity.builder()
                        .placeId(p.getId())
                        .activityName(ay.getActivityName())
                        .activityType(ay.getActivityType()).build();
                activities.add(placeActivity);
            }
        }
        List<PlaceActivity> updatedActivities = placeActivityRepository.saveAll(activities);

        ResponseUniversal response = new ResponseUniversal();
        response.setProposal(createdProposal);
        response.setPlaces(updatedPlaces);
        response.setActivities(updatedActivities);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping(path = "/proposal/{proposalId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> getProposal(@PathVariable final Long proposalId){
        ResponseData response = new ResponseData();
        ProposalResponseData proposal = proposalService.getProposal(proposalId);
        if(null!=proposal){
            response.setSuccess(true);
            response.setData(proposal);
        }else{
            response.setSuccess(false);
            response.setErrorMessage("No Proposal found for proposal id "+proposalId);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }


    @GetMapping(path = "/trip", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> getAllProposal(@RequestParam ProposalStatus status){
        ResponseData response = new ResponseData();
        List<ProposalResponseData> proposal = proposalService.getAllProposal();
        List<ProposalResponseData> filteredProposal = proposal.stream().filter(i->i.getStatus() == status).collect(Collectors.toList());

        if(!filteredProposal.isEmpty()){
            response.setSuccess(true);
            response.setData(filteredProposal);
        }else{
            response.setSuccess(false);
            response.setErrorMessage("No Proposal found! Please create new proposals ! ");
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(path = "/proposals", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> getAllProposal(){
        ResponseData response = new ResponseData();
        List<ProposalResponseData> proposal = proposalService.getAllProposal();
        if(null!=proposal){
            response.setSuccess(true);
            response.setData(proposal);
        }else{
            response.setSuccess(false);
            response.setErrorMessage("No Proposal found! Please create new proposals ! ");
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(path = "/proposal/{proposalId}/vote", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> createProposalVote(@PathVariable final Long proposalId, @RequestBody final CreateProposalVoteRequestData requestData) {
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


    @PatchMapping (path = "/proposal/{proposalId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> updateProposalStatus(@PathVariable final Long proposalId, @RequestParam final ProposalStatus status) {
        ResponseData response = new ResponseData();
        ProposalResponseData proposal = proposalService.updateProposalState(proposalId, status);
        if(proposal!=null){
            response.setSuccess(true);
            response.setData(proposal);
        }else{
            response.setSuccess(false);
            response.setErrorMessage("Error in updating proposal : "+proposalId+ " to state : "+ status);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @PostMapping(
            path = "/proposal/{proposal_id}/place",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Place> createPlace( @PathVariable("proposal_id") Long proposalId,
            @RequestBody final CreatePlaceRequestData requestData) {
        Optional<Proposal> proposal = proposalRepository.findById(proposalId);
        Place place = Place.builder()
                .proposalId(proposalId)
                .placeName(requestData.getPlaceName())
                .build();
        placeRepository.save(place);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(place);
    }

    @GetMapping(
            path = "/proposal/{proposal_id}/places",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PlaceVoteResponseData>> getPlaces(@PathVariable("proposal_id") Long proposalId) {
        List<Place> places = placeRepository.findAllByProposalId(proposalId);
        List<PlaceVoteResponseData> responses = new ArrayList<PlaceVoteResponseData>();

        for (Place place : places) {
            List<PlaceVote> placeVotes = placeVoteRepository.findAllByPlaceId(place.getId());
            PlaceVoteResponseData response = new PlaceVoteResponseData();
            response.setPlaceId(place.getId());
            response.setPlaceName(place.getPlaceName());
            if (!placeVotes.isEmpty()) {
                response.setVotersCount(placeVotes.size());
                response.setVote(placeVotes.stream().mapToInt(i->i.getVote()).average().getAsDouble());
            }
            responses.add(response);
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(responses);
    }

    @PostMapping(
            path = "/proposal/{proposalId}/place/{place_id}/votes",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlaceVote> createPlaceVote( @PathVariable("place_id") Long placeId,
                                              @RequestBody final CreatePlaceVoteRequestData requestData) {
        PlaceVote placeVote = PlaceVote.builder()
                .placeId(placeId)
                .userName(requestData.getUserName())
                .vote(requestData.getVote())
                .build();
        placeVoteRepository.save(placeVote);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(placeVote);
    }

    @PostMapping(path = "/proposal/{proposalId}/place/{placeId}/activity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> createPlaceActivity(@PathVariable final Long proposalId, @PathVariable final Long placeId, @RequestBody final CreateActivityRequestData requestData) {
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

    @GetMapping(path = "/proposal/{proposalId}/place/{placeId}/activities", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PlaceActivity>> getPlaceActivites(@PathVariable final Long proposalId, @PathVariable final Long placeId) {

        return ResponseEntity.status(HttpStatus.OK).body(placeActivityRepository.findAllByPlaceId(placeId));
    }
}
