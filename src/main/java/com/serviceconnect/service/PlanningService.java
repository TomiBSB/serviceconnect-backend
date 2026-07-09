package com.serviceconnect.service;

import com.serviceconnect.entity.PlanningSlot;
import com.serviceconnect.entity.User;
import com.serviceconnect.entity.PlanningSlotType;
import com.serviceconnect.dto.PlanningSlotRequest;
import com.serviceconnect.dto.PlanningSlotResponse;
import com.serviceconnect.repository.PlanningSlotRepository;
import com.serviceconnect.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlanningService {

    private final PlanningSlotRepository planningSlotRepository;
    private final UserRepository userRepository;

    public List<PlanningSlotResponse> getProviderWeekPlanning(String providerId, LocalDate weekStart) {
        User provider = userRepository.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Prestataire non trouvé"));

        LocalDate start = weekStart != null ? weekStart : LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate end = start.plusDays(6);

        return planningSlotRepository.findByProviderAndDateBetweenOrderByDateAscHeureDebutAsc(provider, start, end)
                .stream()
                .map(PlanningSlotResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public PlanningSlotResponse createSlot(String providerId, PlanningSlotRequest request) {
        User provider = userRepository.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Prestataire non trouvé"));

        PlanningSlotType type;
        try {
            type = PlanningSlotType.valueOf(request.getType() != null ? request.getType().toUpperCase() : "DISPONIBLE");
        } catch (IllegalArgumentException e) {
            type = PlanningSlotType.DISPONIBLE;
        }

        PlanningSlot slot = PlanningSlot.builder()
                .provider(provider)
                .date(request.getDate())
                .heureDebut(request.getHeureDebut())
                .heureFin(request.getHeureFin())
                .type(type)
                .build();

        slot = planningSlotRepository.save(slot);
        return PlanningSlotResponse.fromEntity(slot);
    }

    @Transactional
    public void deleteSlot(String slotId) {
        planningSlotRepository.deleteById(slotId);
    }

    public List<PlanningSlotResponse> getProviderDayPlanning(String providerId, LocalDate date) {
        User provider = userRepository.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Prestataire non trouvé"));

        return planningSlotRepository.findByProviderAndDateOrderByHeureDebutAsc(provider, date)
                .stream()
                .map(PlanningSlotResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
