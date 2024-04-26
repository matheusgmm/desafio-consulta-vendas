package com.devsuperior.dsmeta.dto;

public class SellerMinDTO {
    private Long id;
    private String name;

    public SellerMinDTO() {
    }

    public SellerMinDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
