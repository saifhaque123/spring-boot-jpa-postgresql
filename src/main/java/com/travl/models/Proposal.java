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
@Table(name = Proposal.TABLE_NAME)

@AllArgsConstructor
@NoArgsConstructor
public class Proposal {
    public static final String TABLE_NAME = "proposal";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "source")
    private String source;

    @Column(name = "destination")
    private String destination;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "travel_mode")
    @Enumerated(EnumType.STRING)
    private TravelMode travelMode;
}
