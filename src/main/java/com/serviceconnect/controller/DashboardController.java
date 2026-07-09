package com.serviceconnect.controller;

import com.serviceconnect.entity.User;
import com.serviceconnect.dto.ProviderProfileResponse;
import com.serviceconnect.service.ProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final ProviderService providerService;

    @GetMapping("/client")
    public ResponseEntity<Map<String, Object>> getClientDashboard(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(Map.of(
                "user", user,
                "message", "Tableau de bord client"
        ));
    }

    @GetMapping("/prestataire")
    public ResponseEntity<ProviderProfileResponse> getProviderDashboard(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(providerService.getProviderProfileByUserId(user.getId()));
    }
}
