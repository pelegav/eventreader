package com.example.eventreader.xmlelements;

import com.example.eventreader.model.RequestDetails;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@XmlRootElement(name = "root")
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
public class RootXml {
    @Getter
    @Setter
    @XmlElement(name = "requestDetails")
    private RequestDetails requestDetails;

    @Getter
    @Setter
    @XmlElementWrapper(name = "events")
    @XmlElement(name = "event")
    private List<EventXml> events;
}
