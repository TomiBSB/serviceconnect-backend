package com.serviceconnect.controller;

import com.serviceconnect.entity.User;
import com.serviceconnect.dto.RevenueChartPointResponse;
import com.serviceconnect.dto.RevenueResponse;
import com.serviceconnect.dto.RevenueSummaryResponse;
import com.serviceconnect.service.RevenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/revenues")
@RequiredArgsConstructor
public class RevenueController {

    private final RevenueService revenueService;

    @GetMapping
    public ResponseEntity<List<RevenueResponse>> getRevenues(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(revenueService.getProviderRevenues(user.getId()));
    }

    @GetMapping("/summary")
    public ResponseEntity<RevenueSummaryResponse> getSummary(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(revenueService.getRevenueSummary(user.getId()));
    }

    @GetMapping("/chart")
    public ResponseEntity<List<RevenueChartPointResponse>> getChart(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(revenueService.getRevenueChart(user.getId()));
    }
}
