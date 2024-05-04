package com.example.corso.service;

import com.example.corso.dto.CorsoDto;
import com.example.corso.dto.DocenteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@Service
public class DocenteService {

    @Autowired
    private RestTemplate restTemplate;

    public DocenteDto getDocenteById(Long idDocente) {
        String docenteServiceUrl = "http://localhost:8080/docente/getDocente/";// URL del servizio docente
        String url = UriComponentsBuilder.fromHttpUrl(docenteServiceUrl)
                .path("/{id}")
                .buildAndExpand(idDocente)
                .toUriString();
        return restTemplate.getForObject(url, DocenteDto.class);
    }

    public DocenteDto createOrFindDocente(CorsoDto corsoDTO){
        String docenteServiceUrl = "http://localhost:8080/docente/findByNomeAndCognome/{nome}/{cognome}";// URL del servizio docente
        /*String url = UriComponentsBuilder.fromHttpUrl(docenteServiceUrl)
                .path("/{nome}/{cognome}")
                .buildAndExpand(corsoDTO.getNomeDocente(),corsoDTO.getCognomeDocente())
                .toUriString();*/
        DocenteDto docenteDTO  = restTemplate.getForObject(docenteServiceUrl, DocenteDto.class, corsoDTO.getNomeDocente(),corsoDTO.getCognomeDocente());
        return docenteDTO;
    }

}
