package com.example.corso.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocenteDto {

    private Long id;

    @JsonProperty("nome")
    private String nome;
    @JsonProperty("cognome")
    private String cognome;

}
