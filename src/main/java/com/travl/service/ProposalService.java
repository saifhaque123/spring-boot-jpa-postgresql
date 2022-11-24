package com.travl.service;

import com.travl.models.Proposal;
import com.travl.models.ProposalDto;
import com.travl.models.ProposalVote;
import com.travl.repositories.ProposalRepository;
import com.travl.repositories.ProposalVoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProposalService {

    @Autowired
    ProposalRepository proposalRepo;

    @Autowired
    ProposalVoteRepository proposalVoteRepo;

    public ProposalDto getProposal(long proposalId){
        Optional<Proposal> proposalOptional = proposalRepo.findById(proposalId);
        if(proposalOptional.isPresent()){
            ProposalDto proposalData = new ProposalDto();
            Proposal proposal= proposalOptional.get();
            proposalData.setId(proposalId);
            proposalData.setDestination(proposal.getDestination());
            proposalData.setSource(proposal.getSource());
            proposalData.setStartDate(proposal.getStartDate());
            proposalData.setEndDate(proposal.getEndDate());
            proposalData.setTravelMode(proposal.getTravelMode());
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

}
