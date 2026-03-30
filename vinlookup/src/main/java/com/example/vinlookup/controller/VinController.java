package com.example.vinlookup.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.vinlookup.dto.VehicleResponse;
import com.example.vinlookup.service.VinService;

@RestController
@RequestMapping("/api/vin")
@CrossOrigin(origins = {"http://localhost:8080",
    "https://21joha08.github.io"}) // Tillåt CORS för React-appen
public class VinController {

    private final VinService vinService;

    public VinController(VinService vinService) {
        this.vinService = vinService;
    }

    @GetMapping("/{vin}")
    public VehicleResponse getVinData(@PathVariable String vin) {
        return vinService.getVehicleData(vin);
    }
}
