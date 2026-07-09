package com.serviceconnect.service;

import com.serviceconnect.entity.ServiceEntity;
import com.serviceconnect.dto.ProviderProfileResponse;
import com.serviceconnect.repository.ServiceEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceService {

    private final ServiceEntityRepository serviceEntityRepository;

    public List<ServiceEntity> getAllServices() {
        return serviceEntityRepository.findAll();
    }

    public ServiceEntity getServiceById(String id) {
        return serviceEntityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service non trouvé"));
    }

    public List<ServiceEntity> getServicesByCategory(String categorie) {
        return serviceEntityRepository.findByCategorie(categorie);
    }
}
