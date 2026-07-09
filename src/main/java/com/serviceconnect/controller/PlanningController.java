package com.serviceconnect.controller;

import com.serviceconnect.entity.User;
import com.serviceconnect.dto.PlanningSlotRequest;
import com.serviceconnect.dto.PlanningSlotResponse;
import com.serviceconnect.service.PlanningService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/planning")
@RequiredArgsConstructor
public class PlanningController {

    private final PlanningService planningService;

    @GetMapping
    public ResponseEntity<List<PlanningSlotResponse>> getWeekPlanning(
            @AuthenticationPrincipal User user,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate weekStart) {
        return ResponseEntity.ok(planningService.getProviderWeekPlanning(user.getId(), weekStart));
    }

    @GetMapping("/day")
    public ResponseEntity<List<PlanningSlotResponse>> getDayPlanning(
            @AuthenticationPrincipal User user,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(planningService.getProviderDayPlanning(user.getId(), date));
    }

    @PostMapping("/slots")
    public ResponseEntity<PlanningSlotResponse> createSlot(@AuthenticationPrincipal User user,
                                                           @Valid @RequestBody PlanningSlotRequest request) {
        return ResponseEntity.ok(planningService.createSlot(user.getId(), request));
    }

    @DeleteMapping("/slots/{slotId}")
    public ResponseEntity<Void> deleteSlot(@PathVariable String slotId) {
        planningService.deleteSlot(slotId);
        return ResponseEntity.ok().build();
    }
}
