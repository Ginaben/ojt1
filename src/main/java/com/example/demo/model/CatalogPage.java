package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CatalogPage {

    private Long cpCatalogPageSk;
    private String cpCatalogPageId;
    private int cpStartDateSk;
    private int cpEndDateSk;
    private String cpDepartment;
    private int cpCatalogNumber;
    private int cpCatalogPageNumber;
    private String cpDescription;
    private String cpType;
}
