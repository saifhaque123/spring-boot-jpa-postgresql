package com.travl.dtos.request;

import com.travl.models.Place;
import com.travl.models.PlaceActivity;
import com.travl.models.Proposal;
import lombok.Data;

import java.util.List;

@Data
public class ResponseUniversal {
    private Proposal proposal;
    private List<Place> places;
    private List<PlaceActivity> activities;
}
