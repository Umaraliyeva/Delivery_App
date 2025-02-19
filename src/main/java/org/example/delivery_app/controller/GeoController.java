package org.example.delivery_app.controller;

import org.example.delivery_app.service.GeoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/geo")
@CrossOrigin(origins = "*") // CORS muammosini hal qilish uchun qo'shdik
public class GeoController {
    private final GeoService geoService;

    public GeoController(GeoService geoService) {
        this.geoService = geoService;
    }

    @GetMapping("/address")
    public String getAddress(@RequestParam double lat, @RequestParam double lon) {
        return geoService.getAddress(lat, lon);
    }
}
