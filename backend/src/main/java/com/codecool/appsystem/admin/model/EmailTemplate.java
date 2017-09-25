package com.codecool.appsystem.admin.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
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
    @Column(length = 40)
    private String id = UUID.randomUUID().toString();

    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String template;

    @Column(columnDefinition = "TEXT")
    private String model;

    private Boolean master;

    private int order;
}
