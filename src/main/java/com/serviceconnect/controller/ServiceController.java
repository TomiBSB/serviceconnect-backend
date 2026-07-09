package com.serviceconnect.controller;

import com.serviceconnect.entity.ServiceEntity;
import com.serviceconnect.service.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class ServiceController {

    private final ServiceService serviceService;

    @GetMapping
    public ResponseEntity<List<ServiceEntity>> getAllServices() {
        return ResponseEntity.ok(serviceService.getAllServices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceEntity> getServiceById(@PathVariable String id) {
        return ResponseEntity.ok(serviceService.getServiceById(id));
    }

    @GetMapping("/categorie/{categorie}")
    public ResponseEntity<List<ServiceEntity>> getServicesByCategory(@PathVariable String categorie) {
        return ResponseEntity.ok(serviceService.getServicesByCategory(categorie));
    }
}
