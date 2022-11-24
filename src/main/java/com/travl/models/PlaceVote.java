package com.travl.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
// TODO Review index
@Table(name = PlaceVote.TABLE_NAME)

@AllArgsConstructor
@NoArgsConstructor
public class PlaceVote {
    public static final String TABLE_NAME = "place_vote_mapping";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "place_id")
    private Long placeId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "vote")
    private Integer vote;
}
