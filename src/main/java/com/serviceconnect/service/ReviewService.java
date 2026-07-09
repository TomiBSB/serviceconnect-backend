package com.serviceconnect.service;

import com.serviceconnect.entity.Review;
import com.serviceconnect.entity.User;
import com.serviceconnect.entity.RoleType;
import com.serviceconnect.dto.ReviewRequest;
import com.serviceconnect.dto.ReviewResponse;
import com.serviceconnect.repository.ReviewRepository;
import com.serviceconnect.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    @Transactional
    public ReviewResponse createReview(String clientId, ReviewRequest request) {
        User client = userRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));
        User prestataire = userRepository.findById(request.getPrestataireId())
                .orElseThrow(() -> new RuntimeException("Prestataire non trouvé"));

        if (prestataire.getRole() != RoleType.PRESTATAIRE) {
            throw new RuntimeException("L'utilisateur cible n'est pas un prestataire");
        }

        Review review = Review.builder()
                .client(client)
                .prestataire(prestataire)
                .note(request.getNote())
                .commentaire(request.getCommentaire())
                .build();

        review = reviewRepository.save(review);
        return ReviewResponse.fromEntity(review);
    }

    public List<ReviewResponse> getProviderReviews(String providerId) {
        User prestataire = userRepository.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Prestataire non trouvé"));

        return reviewRepository.findByPrestataireOrderByDateDesc(prestataire)
                .stream()
                .map(ReviewResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
