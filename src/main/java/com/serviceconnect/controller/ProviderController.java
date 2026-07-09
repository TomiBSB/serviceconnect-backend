package com.serviceconnect.controller;

import com.serviceconnect.entity.User;
import com.serviceconnect.dto.UpdateProviderProfileRequest;
import com.serviceconnect.dto.ProviderProfileResponse;
import com.serviceconnect.service.ProviderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/providers")
@RequiredArgsConstructor
public class ProviderController {

    private final ProviderService providerService;

    @GetMapping("/profile")
    public ResponseEntity<ProviderProfileResponse> getMyProfile(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(providerService.getProviderProfileByUserId(user.getId()));
    }

    @PutMapping("/profile")
    public ResponseEntity<ProviderProfileResponse> updateProfile(@AuthenticationPrincipal User user,
                                                                  @Valid @RequestBody UpdateProviderProfileRequest request) {
        return ResponseEntity.ok(providerService.updateProviderProfile(user.getId(), request));
    }

    @GetMapping
    public ResponseEntity<List<ProviderProfileResponse>> getAllProviders() {
        return ResponseEntity.ok(providerService.getAllProviders());
    }

    @GetMapping("/{providerId}")
    public ResponseEntity<ProviderProfileResponse> getProviderById(@PathVariable String providerId) {
        return ResponseEntity.ok(providerService.getProviderById(providerId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProviderProfileResponse>> searchProviders(@RequestParam(required = false) String q) {
        return ResponseEntity.ok(providerService.searchProviders(q));
    }
}
