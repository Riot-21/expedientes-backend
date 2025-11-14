package com.legaltech.crud.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.legaltech.crud.models.Expediente;


public interface ExpedienteRepository extends JpaRepository<Expediente, UUID>{

}
