package com.example.eventreader.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SourceCompanyGroup {
    private String sourceCompanyName;
    private List<Product> products;
}
