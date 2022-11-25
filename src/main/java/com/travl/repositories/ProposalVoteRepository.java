package com.travl.repositories;

import com.travl.models.Proposal;
import com.travl.models.ProposalVote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProposalVoteRepository extends JpaRepository<ProposalVote, Long> {
    Optional<List<ProposalVote>> findByProposalId(Long id);
}
