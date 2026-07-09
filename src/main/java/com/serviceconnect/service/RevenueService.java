package com.serviceconnect.service;

import com.serviceconnect.entity.Revenue;
import com.serviceconnect.entity.User;
import com.serviceconnect.dto.RevenueChartPointResponse;
import com.serviceconnect.dto.RevenueResponse;
import com.serviceconnect.dto.RevenueSummaryResponse;
import com.serviceconnect.repository.RevenueRepository;
import com.serviceconnect.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RevenueService {

    private final RevenueRepository revenueRepository;
    private final UserRepository userRepository;

    public List<RevenueResponse> getProviderRevenues(String providerId) {
        User provider = userRepository.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Prestataire non trouvé"));

        return revenueRepository.findByProviderOrderByDateDesc(provider)
                .stream()
                .map(RevenueResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public RevenueSummaryResponse getRevenueSummary(String providerId) {
        User provider = userRepository.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Prestataire non trouvé"));

        List<Revenue> allRevenues = revenueRepository.findByProviderOrderByDateDesc(provider);
        LocalDate now = LocalDate.now();
        LocalDate startOfMonth = now.withDayOfMonth(1);
        LocalDate startOfYear = now.withDayOfYear(1);

        List<Revenue> monthRevenues = allRevenues.stream()
                .filter(r -> !r.getDate().isBefore(startOfMonth))
                .collect(Collectors.toList());

        List<Revenue> yearRevenues = allRevenues.stream()
                .filter(r -> !r.getDate().isBefore(startOfYear))
                .collect(Collectors.toList());

        double totalMonth = monthRevenues.stream().mapToDouble(Revenue::getNet).sum();
        double totalYear = yearRevenues.stream().mapToDouble(Revenue::getNet).sum();
        double totalGlobal = allRevenues.stream().mapToDouble(Revenue::getNet).sum();

        long missionsMois = monthRevenues.size();
        long missionsAnnee = yearRevenues.size();

        double moyenne = missionsMois > 0 ? totalMonth / missionsMois : 0;

        return RevenueSummaryResponse.builder()
                .totalMois(totalMonth)
                .totalAnnee(totalYear)
                .totalGlobal(totalGlobal)
                .missionsMois((int) missionsMois)
                .missionsAnnee((int) missionsAnnee)
                .progression(0.0)
                .moyenneParMission(moyenne)
                .build();
    }

    public List<RevenueChartPointResponse> getRevenueChart(String providerId) {
        User provider = userRepository.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Prestataire non trouvé"));

        List<Revenue> yearRevenues = revenueRepository.findByProviderAndDateBetweenOrderByDateAsc(
                provider,
                LocalDate.now().withDayOfYear(1),
                LocalDate.now().withDayOfYear(LocalDate.now().lengthOfYear())
        );

        return yearRevenues.stream()
                .collect(Collectors.groupingBy(
                        r -> r.getDate().getMonth().name().substring(0, 3),
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> RevenueChartPointResponse.builder()
                                        .mois(list.get(0).getDate().getMonth().name().substring(0, 3))
                                        .montant(list.stream().mapToDouble(Revenue::getNet).sum())
                                        .missions(list.size())
                                        .build()
                        )
                )).values().stream()
                .collect(Collectors.toList());
    }
}
