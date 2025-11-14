package com.legaltech.crud.dtos;

import com.legaltech.crud.models.Estado;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateExpedienteDTO {

    @Size(min = 1, message = "no puede estar en blanco")
    private String nombre;

    @Size(min = 1, message = "no puede estar en blanco")
    private String descripcion;

    
    private Estado estado;

}
