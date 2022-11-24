package com.travl.dtos.response;

import lombok.Data;

@Data
public class PlaceVoteResponseData {
    Long placeId;
    String placeName;
    Integer votersCount;
    Double vote;
}
