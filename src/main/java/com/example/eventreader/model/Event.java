package com.example.eventreader.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "events")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @Getter
    private String id;
    @Getter
    @Setter
    private String type;
    @Getter
    @Setter
    private String insuredId;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Product> products;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "requestDetails_id")
    private RequestDetails requestDetails;
}
