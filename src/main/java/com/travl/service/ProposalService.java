package com.travl.service;

import com.travl.dtos.request.CreateActivityRequestData;
import com.travl.enums.ProposalStatus;
import com.travl.models.PlaceActivity;
import com.travl.models.Proposal;
import com.travl.dtos.response.ProposalResponseData;
import com.travl.models.ProposalVote;
import com.travl.dtos.request.CreateProposalVoteRequestData;
import com.travl.repositories.PlaceActivityRepository;
import com.travl.repositories.ProposalRepository;
import com.travl.repositories.ProposalVoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProposalService {

    @Autowired
    ProposalRepository proposalRepo;

    @Autowired
    ProposalVoteRepository proposalVoteRepo;

    @Autowired
    PlaceActivityRepository placeActivityRepository;

    public ProposalResponseData getProposal(long proposalId){
        Optional<Proposal> proposalOptional = proposalRepo.findById(proposalId);
        if(proposalOptional.isPresent()){
            ProposalResponseData proposalData = new ProposalResponseData();
            Proposal proposal= proposalOptional.get();
            proposalData.setId(proposalId);
            proposalData.setDestination(proposal.getDestination());
            proposalData.setSource(proposal.getSource());
            proposalData.setStartDate(proposal.getStartDate());
            proposalData.setEndDate(proposal.getEndDate());
            proposalData.setTravelMode(proposal.getTravelMode());
            proposalData.setStatus(proposal.getProposalStatus());
            Optional<List<ProposalVote>> proposalVotesList = proposalVoteRepo.findByProposalId(proposalId);
            if(!proposalVotesList.get().isEmpty()) {
                List<ProposalVote> proposalVotes = proposalVotesList.get();
                proposalData.setVotersCount(proposalVotes.size());
                proposalData.setVote(proposalVotes.stream().mapToInt(i->i.getVote()).average().getAsDouble());
            }
            return proposalData;
        } else {
            return null;
        }
    }

    public ProposalVote createProposalVote(CreateProposalVoteRequestData requestData) {
        ProposalVote pVote = ProposalVote.builder().proposalId(requestData.getProposalId())
                .userName(requestData.getUserName())
                .vote(requestData.getVote()).build();
        ProposalVote savedVote = proposalVoteRepo.save(pVote);
        return savedVote;
    }

    public ProposalResponseData updateProposalState(Long proposalId, ProposalStatus status) {
        Optional<Proposal> proposalOptional = proposalRepo.findById(proposalId);
        if(proposalOptional.isPresent()){
            ProposalResponseData proposalData = new ProposalResponseData();
            Proposal proposal= proposalOptional.get();
            proposal.setProposalStatus(status);
            Proposal updatedProposal = proposalRepo.save(proposal);
            proposalData.setId(proposalId);
            proposalData.setDestination(updatedProposal.getDestination());
            proposalData.setSource(updatedProposal.getSource());
            proposalData.setStartDate(updatedProposal.getStartDate());
            proposalData.setEndDate(updatedProposal.getEndDate());
            proposalData.setTravelMode(updatedProposal.getTravelMode());
            proposalData.setStatus(updatedProposal.getProposalStatus());
            Optional<List<ProposalVote>> proposalVotesList = proposalVoteRepo.findByProposalId(proposalId);
            if(proposalVotesList.isPresent()) {
                List<ProposalVote> proposalVotes = proposalVotesList.get();
                proposalData.setVotersCount(proposalVotes.size());
                proposalData.setVote(proposalVotes.stream().mapToInt(i->i.getVote()).average().getAsDouble());
            }
            return proposalData;
        } else {
            return null;
        }
    }

    public List<ProposalResponseData> getAllProposal() {
        Optional<List<Proposal>> proposalListOptional = Optional.of(proposalRepo.findAll());
        if(proposalListOptional.isPresent()){
            List<ProposalResponseData> responseList = new ArrayList<>();
            List<Proposal> proposalList= proposalListOptional.get();
            for(Proposal proposal:proposalList){
                ProposalResponseData proposalData = new ProposalResponseData();
                proposalData.setId(proposal.getId());
                proposalData.setDestination(proposal.getDestination());
                proposalData.setSource(proposal.getSource());
                proposalData.setStartDate(proposal.getStartDate());
                proposalData.setEndDate(proposal.getEndDate());
                proposalData.setTravelMode(proposal.getTravelMode());
                proposalData.setStatus(proposal.getProposalStatus());
                Optional<List<ProposalVote>> proposalVotesList = proposalVoteRepo.findByProposalId(proposal.getId());
                if(!proposalVotesList.get().isEmpty()) {
                    List<ProposalVote> proposalVotes = proposalVotesList.get();
                    proposalData.setVotersCount(proposalVotes.size());
                    proposalData.setVote(proposalVotes.stream().mapToInt(i->i.getVote()).average().getAsDouble());
                }
                responseList.add(proposalData);
            }
            return responseList;
        } else {
            return null;
        }
    }

    public PlaceActivity createPlaceActivity(CreateActivityRequestData requestData) {
        PlaceActivity placeActivity = PlaceActivity.builder().id(requestData.getId())
                .placeId(requestData.getPlaceId())
                .activityName(requestData.getActivityName())
                .activityType(requestData.getActivityType()).build();
        PlaceActivity savedResult = placeActivityRepository.save(placeActivity);
        return savedResult;
    }
}
