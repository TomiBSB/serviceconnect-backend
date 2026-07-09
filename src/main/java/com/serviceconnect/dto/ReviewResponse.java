package com.serviceconnect.dto;

import com.serviceconnect.entity.Review;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponse {

    private String id;
    private String initials;
    private String name;
    private LocalDateTime date;
    private Integer note;
    private String text;

    public static ReviewResponse fromEntity(Review review) {
        String clientPrenom = review.getClient().getPrenom();
        String clientNom = review.getClient().getNom();
        String initials = (clientPrenom.isBlank() ? "" : String.valueOf(clientPrenom.charAt(0)).toUpperCase())
                + (clientNom.isBlank() ? "" : String.valueOf(clientNom.charAt(0)).toUpperCase());

        return ReviewResponse.builder()
                .id(review.getId())
                .initials(initials)
                .name(clientPrenom + " " + clientNom)
                .date(review.getDate())
                .note(review.getNote())
                .text(review.getCommentaire() != null ? review.getCommentaire() : "")
                .build();
    }
}
