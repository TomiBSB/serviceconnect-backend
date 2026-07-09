package com.serviceconnect.service;

import com.serviceconnect.entity.User;
import com.serviceconnect.dto.UpdateUserRequest;
import com.serviceconnect.dto.UserResponse;
import com.serviceconnect.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse getUserById(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        return UserResponse.fromEntity(user);
    }

    @Transactional
    public UserResponse updateUser(String userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (request.getNom() != null) user.setNom(request.getNom());
        if (request.getPrenom() != null) user.setPrenom(request.getPrenom());
        if (request.getSpecialite() != null) user.setSpecialite(request.getSpecialite());
        if (request.getDateNaissance() != null) user.setDateNaissance(request.getDateNaissance());
        if (request.getNumeroTel() != null) user.setNumeroTel(request.getNumeroTel());
        if (request.getAdresse() != null) user.setAdresse(request.getAdresse());
        if (request.getVille() != null) user.setVille(request.getVille());

        user = userRepository.save(user);
        return UserResponse.fromEntity(user);
    }
}
