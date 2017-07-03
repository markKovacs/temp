package com.codecool.appsystem.admin.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Data
@Entity
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@EqualsAndHashCode(of = "id")
@Table(name = "sent_emails")
@ToString
public class SentEmail {

    @Id
    private String id;

    private String applicationId;

    private String initial;

    private String info;
    private String reminder;

    private String deadlineReminder;
    private String scheduleOrFailed;
    private String scheduleReminder;
    private String screeningThanks;
}
