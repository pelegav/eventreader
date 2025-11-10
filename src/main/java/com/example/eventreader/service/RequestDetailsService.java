package com.example.eventreader.service;

import com.example.eventreader.model.RequestDetails;
import com.example.eventreader.repository.RequestDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestDetailsService {

    @Autowired
    private RequestDetailsRepository requestDetailsRepository;

    public void saveRequestDetails(RequestDetails requestDetails) {
        requestDetailsRepository.save(requestDetails);
    }
}
