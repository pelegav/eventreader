package com.example.eventreader.xmlelements;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
public class RequestDetailsXml {
    private String id;
    private String acceptDate;
    private String sourceCompany;
}
