package com.example.eventreader.xmlelements;

import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
public class EventXml {
    @Getter
    @Setter
    private String id;
    @Getter
    @Setter
    private String type;
    @Getter
    @Setter
    private String insuredId;
    @Getter
    @Setter
    @XmlElementWrapper(name = "products")
    @XmlElement(name = "product")
    private List<ProductXml> products;

}
