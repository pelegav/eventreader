package com.example.eventreader.xmlelements;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
public class RequestDetailsXml {
    @Getter
    @Setter
    private String id;
    @Getter
    @Setter
    private String acceptDate;
    @Getter
    @Setter
    private String sourceCompany;
}
