package com.legaltech.crud.dtos;

import java.util.UUID;

import com.legaltech.crud.models.Estado;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ExpedienteResponseDTO {
    private UUID id;
    private String nombre;
    private String descripcion;
    private Estado estado;
}
