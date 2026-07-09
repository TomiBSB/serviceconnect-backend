package com.serviceconnect.repository;

import com.serviceconnect.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ServiceEntityRepository extends JpaRepository<ServiceEntity, String> {
    List<ServiceEntity> findByCategorie(String categorie);
}
