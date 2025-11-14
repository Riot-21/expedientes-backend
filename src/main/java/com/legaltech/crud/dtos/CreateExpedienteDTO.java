package com.legaltech.crud.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateExpedienteDTO {

    @NotBlank
    private String nombre;

    @NotBlank
    private String descripcion;

}
