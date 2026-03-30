package com.example.vinlookup.service;

import com.example.vinlookup.dto.VehicleResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class VinService {

    private final RestTemplate restTemplate = new RestTemplate();

    public VehicleResponse getVehicleData(String vin) {
        String url = "https://vpic.nhtsa.dot.gov/api/vehicles/decodevinvaluesextended/" + vin + "?format=json";

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        if (response == null || !response.containsKey("Results")) {
            throw new RuntimeException("No response from VIN API");
        }

        List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("Results");

        if (results == null || results.isEmpty()) {
            throw new RuntimeException("No vehicle data found");
        }

        Map<String, Object> data = results.get(0);

        VehicleResponse vehicle = new VehicleResponse();
        vehicle.setVehicleBrandName(getValue(data, "Make"));
        vehicle.setVehicleModel(getValue(data, "Model"));
        vehicle.setVehicleDrive(getValue(data, "DriveType"));
        vehicle.setChassisNo(vin);
        vehicle.setYearOfBuilt(getValue(data, "ModelYear"));
        vehicle.setCountryOfOrigin(getValue(data, "PlantCountry"));
        vehicle.setVehicleType(getValue(data, "VehicleType"));

        // VIN innehåller normalt inte färg
        vehicle.setVehicleColor(null);

        return vehicle;
    }

    private String getValue(Map<String, Object> data, String key) {
        Object value = data.get(key);
        if (value == null) {
            return null;
        }

        String text = value.toString().trim();
        return text.isEmpty() ? null : text;
    }
}