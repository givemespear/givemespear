package com.example.TheEnd.controller;

import jakarta.validation.constraints.NotBlank;

public class MemberForm {
    @NotBlank
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
