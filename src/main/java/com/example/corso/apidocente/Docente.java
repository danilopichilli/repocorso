package com.example.corso.apidocente;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Docente {
    private long id;
    @JsonProperty("nome")
    private String nome;
    @JsonProperty("cognome")
    private String cognome;
}
