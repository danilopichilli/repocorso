package com.example.corso.apidocente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class DocenteService {

    @Autowired
    private RestTemplate restTemplate;

    public Docente getDocenteById(long id) {
        // URL del servizio docente
        String docenteServiceUrl = "http://localhost:8080/docente/getDocente/";
        String url = UriComponentsBuilder.fromHttpUrl(docenteServiceUrl)
                .path("/{id}")
                .buildAndExpand(id)
                .toUriString();
        return restTemplate.getForObject(url, Docente.class);
    }
}
