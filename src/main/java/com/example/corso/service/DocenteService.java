package com.example.corso.service;

import com.example.corso.dto.CorsoDTO;
import com.example.corso.dto.DocenteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class DocenteService {

    @Autowired
    private RestTemplate restTemplate;

    public DocenteDTO getDocenteById(Long id) {
        String docenteServiceUrl = "http://localhost:8080/docente/getDocente/";// URL del servizio docente
        String url = UriComponentsBuilder.fromHttpUrl(docenteServiceUrl)
                .path("/{id}")
                .buildAndExpand(id)
                .toUriString();
        return restTemplate.getForObject(url, DocenteDTO.class);
    }

    public DocenteDTO getDocente(CorsoDTO corsoDTO){
        String docenteServiceUrl = "http://localhost:8080/docente/findDocente/";
        String url = UriComponentsBuilder.fromHttpUrl(docenteServiceUrl)
                .path("/{nome}/{cognome}")
                .buildAndExpand(corsoDTO.getNomeDocente(),corsoDTO.getCognomeDocente())
                .toUriString();
        return restTemplate.getForObject(url, DocenteDTO.class);
    }

}
