package com.example.eventreader.xmlelements;

import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
public class EventXml {
    private String id;
    private String type;
    private String insuredId;
    @XmlElementWrapper(name = "products")
    @XmlElement(name = "product")
    private List<ProductXml> products;

}
