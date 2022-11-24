package com.travl.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
// TODO Review index
@Table(name = ProposalVote.TABLE_NAME)

@AllArgsConstructor
@NoArgsConstructor
public class ProposalVote {
    public static final String TABLE_NAME = "proposal_vote_mapping";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "proposal_id")
    private Long proposalId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "vote")
    private Integer vote;
}
