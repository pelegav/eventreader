package com.example.eventreader.xmlelements;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
public class ProductXml {
    @Getter
    @Setter
    private String type;
    @Getter
    @Setter
    private Double price;
    @Setter
    @Getter
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate startDate;
    @Setter
    @Getter
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate endDate;
}
