package com.codecool.appsystem.admin.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@EqualsAndHashCode(of = "id")
@Table(name = "email_templates")
@AllArgsConstructor
@Builder
@ToString
public class EmailTemplate {

    @Id
    @JsonIgnore
    @Column(length = 40)
    private String id = UUID.randomUUID().toString();

    private String locationId;
    @Column(columnDefinition = "TEXT")
    private String html;
    private Boolean master;
}
