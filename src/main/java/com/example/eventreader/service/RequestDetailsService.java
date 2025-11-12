package com.example.eventreader.service;

import com.example.eventreader.model.RequestDetails;
import com.example.eventreader.repository.RequestDetailsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RequestDetailsService {

    private final RequestDetailsRepository requestDetailsRepository;

    public void saveRequestDetails(RequestDetails requestDetails) {
        requestDetailsRepository.save(requestDetails);
    }
}
