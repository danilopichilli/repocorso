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

    public DocenteDTO getDocenteById(Long idDocente) {
        String docenteServiceUrl = "http://localhost:8080/docente/getDocente/";// URL del servizio docente
        String url = UriComponentsBuilder.fromHttpUrl(docenteServiceUrl)
                .path("/{id}")
                .buildAndExpand(idDocente)
                .toUriString();
        return restTemplate.getForObject(url, DocenteDTO.class);
    }

    public DocenteDTO createOrFindDocente(CorsoDTO corsoDTO){
        String docenteServiceUrl = "http://localhost:8080/docente/findByNomeAndCognome/{nome}/{cognome}";// URL del servizio docente
        /*String url = UriComponentsBuilder.fromHttpUrl(docenteServiceUrl)
                .path("/{nome}/{cognome}")
                .buildAndExpand(corsoDTO.getNomeDocente(),corsoDTO.getCognomeDocente())
                .toUriString();*/
        DocenteDTO docenteDTO  = restTemplate.getForObject(docenteServiceUrl, DocenteDTO.class, corsoDTO.getNomeDocente(),corsoDTO.getCognomeDocente());
        return docenteDTO;
    }

}
