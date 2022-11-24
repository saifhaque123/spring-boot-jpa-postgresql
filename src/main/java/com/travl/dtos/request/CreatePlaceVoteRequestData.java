package com.travl.dtos.request;

import lombok.Data;

@Data
public class CreatePlaceVoteRequestData {
    private String userName;
    private Integer vote;
}
