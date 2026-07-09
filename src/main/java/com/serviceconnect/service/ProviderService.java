package com.serviceconnect.service;

import com.serviceconnect.entity.ProviderProfile;
import com.serviceconnect.entity.User;
import com.serviceconnect.entity.RoleType;
import com.serviceconnect.dto.UpdateProviderProfileRequest;
import com.serviceconnect.dto.ProviderProfileResponse;
import com.serviceconnect.repository.ProviderProfileRepository;
import com.serviceconnect.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProviderService {

    private final ProviderProfileRepository providerProfileRepository;
    private final UserRepository userRepository;

    public ProviderProfileResponse getProviderProfile(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        ProviderProfile profile = providerProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profil prestataire non trouvé"));
        return ProviderProfileResponse.fromEntity(profile, user);
    }

    public ProviderProfileResponse getProviderProfileByUserId(String userId) {
        return getProviderProfile(userId);
    }

    @Transactional
    public ProviderProfileResponse updateProviderProfile(String userId, UpdateProviderProfileRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        ProviderProfile profile = providerProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profil prestataire non trouvé"));

        if (request.getDescription() != null) profile.setDescription(request.getDescription());
        if (request.getCompetences() != null) profile.setCompetences(request.getCompetences());
        if (request.getCertifications() != null) profile.setCertifications(request.getCertifications());
        if (request.getDisponible() != null) profile.setDisponible(request.getDisponible());
        if (request.getPays() != null) profile.setPays(request.getPays());
        if (request.getSpecialite() != null) user.setSpecialite(request.getSpecialite());

        if (request.getSpecialite() != null) {
            userRepository.save(user);
        }

        profile = providerProfileRepository.save(profile);
        return ProviderProfileResponse.fromEntity(profile, user);
    }

    public List<ProviderProfileResponse> getAllProviders() {
        List<User> providers = userRepository.findAll().stream()
                .filter(u -> u.getRole() == RoleType.PRESTATAIRE)
                .collect(Collectors.toList());

        return providers.stream().map(provider -> {
            ProviderProfile profile = providerProfileRepository.findByUserId(provider.getId()).orElse(null);
            if (profile == null) return null;
            return ProviderProfileResponse.fromEntity(profile, provider);
        }).collect(Collectors.toList());
    }

    public ProviderProfileResponse getProviderById(String providerId) {
        User provider = userRepository.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Prestataire non trouvé"));

        if (provider.getRole() != RoleType.PRESTATAIRE) {
            throw new RuntimeException("L'utilisateur n'est pas un prestataire");
        }

        ProviderProfile profile = providerProfileRepository.findByUserId(providerId)
                .orElseThrow(() -> new RuntimeException("Profil prestataire non trouvé"));

        return ProviderProfileResponse.fromEntity(profile, provider);
    }

    public List<ProviderProfileResponse> searchProviders(String query) {
        return getAllProviders().stream()
                .filter(p -> query == null || query.isBlank() ||
                        p.getNom().toLowerCase().contains(query.toLowerCase()) ||
                        p.getPrenom().toLowerCase().contains(query.toLowerCase()) ||
                        p.getSpecialite().toLowerCase().contains(query.toLowerCase()) ||
                        p.getCompetences().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }
}
