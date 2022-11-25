package com.travl.models;

import com.travl.enums.ActivityType;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@Table(name = PlaceActivity.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
public class PlaceActivity {

    public static final String TABLE_NAME = "place_activity";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "place_id")
    private Long placeId;
    @Column(name = "activity_name")
    private String activityName;
    @Column(name = "activity_type")
    private ActivityType activityType;
}
