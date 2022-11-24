package com.travl.controllers;

import com.travl.dtos.request.CreatePlaceRequestData;
import com.travl.dtos.request.CreatePlaceVoteRequestData;
import com.travl.dtos.request.CreateProposalRequestData;
import com.travl.dtos.response.PlaceVoteResponseData;
import com.travl.models.Place;
import com.travl.models.PlaceVote;
import com.travl.models.Proposal;
import com.travl.repositories.PlaceRepository;
import com.travl.repositories.PlaceVoteRepository;
import com.travl.repositories.ProposalRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class TravlController {
    private final ProposalRepository proposalRepository;
    private final PlaceRepository placeRepository;
    private final PlaceVoteRepository placeVoteRepository;

    @PostMapping(
            path = "/proposal",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Proposal> createProposal(
            @RequestBody final CreateProposalRequestData requestData) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Proposal proposal = Proposal.builder()
                .source(requestData.getSource())
                .destination(requestData.getDestination())
                .startDate(LocalDate.parse(requestData.getStartDate(), formatter))
                .endDate(LocalDate.parse(requestData.getEndDate(), formatter))
                .travelMode(requestData.getTravelMode())
                .build();
        proposalRepository.save(proposal);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(proposal);
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
        List<PlaceVoteResponseData> responses = null;

        for (Place place : places) {
            List<PlaceVote> placeVotes = placeVoteRepository.findAllByPlaceId(place.getId());
            PlaceVoteResponseData response = new PlaceVoteResponseData();
            response.setPlaceId(place.getId());
            response.setPlaceName(place.getPlaceName());
            response.setVotersCount(placeVotes.size());
            response.setVote(placeVotes.stream().mapToInt(i->i.getVote()).average().getAsDouble());
            responses.add(response);
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(responses);
    }

    @PostMapping(
            path = "/place/{place_id}/votes",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlaceVote> createPlaceVote( @PathVariable("place_id") Long placeId,
                                              @RequestBody final CreatePlaceVoteRequestData requestData) {
        PlaceVote placeVote = PlaceVote.builder()
                .placeId(placeId)
                .userName(requestData.getUserName())
                .vote(requestData.getVote())
                .build();


        return ResponseEntity.status(HttpStatus.CREATED)
                .body(placeVote);
    }


}
