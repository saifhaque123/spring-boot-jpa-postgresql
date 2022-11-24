package com.travl.models;

import java.time.LocalDate;
import javax.persistence.*;
import com.travl.enums.TravelMode;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
// TODO Review index
@Table(name = Place.TABLE_NAME)

@AllArgsConstructor
@NoArgsConstructor
public class Place {
    public static final String TABLE_NAME = "place";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "proposal_id")
    private Long proposalId;

    @Column(name = "place_name")
    private String placeName;


}
