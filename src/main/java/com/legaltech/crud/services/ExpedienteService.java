package com.legaltech.crud.services;

import java.util.List;
import java.util.UUID;

// import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.legaltech.crud.dtos.CreateExpedienteDTO;
import com.legaltech.crud.dtos.ExpedienteResponseDTO;
import com.legaltech.crud.dtos.UpdateExpedienteDTO;
import com.legaltech.crud.exceptions.NotFoundException;
import com.legaltech.crud.models.Estado;
import com.legaltech.crud.models.Expediente;
import com.legaltech.crud.repositories.ExpedienteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpedienteService {
    private final ExpedienteRepository expedienteRepository;

    @Transactional(readOnly = true)
    public List<ExpedienteResponseDTO> getAll() {
        return expedienteRepository.findAll().stream()
                .map(ex -> mapToDTO(ex))
                .toList();
    }

    @Transactional(readOnly = true)
    public ExpedienteResponseDTO getById(UUID uuid) {
        Expediente ex = expedienteRepository.findById(uuid)
                .orElseThrow(() -> new NotFoundException("Expediente no encontrado, id: " + uuid));

        return mapToDTO(ex);
    }

    @Transactional
    public ExpedienteResponseDTO create(CreateExpedienteDTO dto) {
        Expediente ex = new Expediente();
        ex.setNombre(dto.getNombre());
        ex.setDescripcion(dto.getDescripcion());
        ex.setEstado(Estado.PENDIENTE);

        return mapToDTO(expedienteRepository.save(ex));
    }

    @Transactional
    public ExpedienteResponseDTO update(UpdateExpedienteDTO dto,UUID uuid) {
        Expediente ex = expedienteRepository.findById(uuid)
                .orElseThrow(() -> new NotFoundException("Expediente no encontrado, id: " + uuid));
        if (dto.getNombre() != null) {
            ex.setNombre(dto.getNombre());
        }
        if (dto.getDescripcion() != null) {
            ex.setDescripcion(dto.getDescripcion());
        }
        if (dto.getEstado() != null) {
            ex.setEstado(dto.getEstado());
        }

        Expediente updatedEx = expedienteRepository.save(ex);

        return mapToDTO(updatedEx);
    }

    @Transactional
    public void delete(UUID uuid) {
        Expediente ex = expedienteRepository.findById(uuid)
                .orElseThrow(() -> new NotFoundException("Expediente no encontrado, id: " + uuid));

        expedienteRepository.delete(ex);
    }

    private ExpedienteResponseDTO mapToDTO(Expediente ex) {
        return ExpedienteResponseDTO.builder()
                .id(ex.getId())
                .nombre(ex.getNombre())
                .descripcion(ex.getDescripcion())
                .estado(ex.getEstado())
                .build();
    }
}
