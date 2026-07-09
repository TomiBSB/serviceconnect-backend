package com.serviceconnect.repository;

import com.serviceconnect.entity.ProviderProfile;
import com.serviceconnect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProviderProfileRepository extends JpaRepository<ProviderProfile, String> {
    Optional<ProviderProfile> findByUser(User user);
    Optional<ProviderProfile> findByUserId(String userId);
}
