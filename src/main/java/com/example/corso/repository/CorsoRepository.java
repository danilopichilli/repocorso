package com.example.corso.repository;

import com.example.corso.entity.Corso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CorsoRepository extends JpaRepository<Corso, Long> {
    List<Corso> findByDurata(String durata);

    List<Corso> findByNome(String nome);
}
