package com.codecool.appsystem.admin.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Email {

    private String fromAddress;
    private String toAddress;
    private String subject;
    private String html;
    private String application;

}
