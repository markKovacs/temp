package com.codecool.appsystem.admin.model;

import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class ScreeningStep {

    @Id
    @Column(name = "id", length = 40)
    private String id = UUID.randomUUID().toString();

    private String name;

    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

    private Boolean enabled = Boolean.TRUE;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "screeningStepId", cascade = CascadeType.ALL)
    private List<ScreeningStepCriteria> criteria;

}
