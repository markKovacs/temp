package com.codecool.appsystem.admin.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Email {

    private String fromAddress;
    private String toAddress;
    private String subject;
    private String html;
    private String application;
    private Boolean success;
    private Date sent;
    private Date deliveredAt;
    private Boolean opened;
    private Date openedAt;

}
