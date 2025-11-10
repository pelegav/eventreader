package com.example.eventreader.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "requestDetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDetails {
    @Id
    @Getter
    private String id;
    @Getter
    @Setter
    private String acceptDate;
    @Getter
    @Setter
    private String sourceCompany;

    @OneToMany(mappedBy = "requestDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Event> events;
}
