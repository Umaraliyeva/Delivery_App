package org.example.delivery_app.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

@Service
public class GeoService {
    private static final String API_KEY = "78f73487-90dc-40af-897b-4afd03d3dc92"; // O'zingiz olgan API kalitini shu yerga qo'ying
    private static final String YANDEX_GEOCODER_URL = "https://geocode-maps.yandex.ru/1.x/?format=json&apikey=%s&geocode=%s,%s";

    public String getAddress(double latitude, double longitude) {
        String url = String.format(YANDEX_GEOCODER_URL, API_KEY, longitude, latitude);
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject featureMember = jsonObject
                    .getJSONObject("response")
                    .getJSONObject("GeoObjectCollection")
                    .getJSONArray("featureMember")
                    .getJSONObject(0)
                    .getJSONObject("GeoObject");

            return featureMember.getJSONObject("metaDataProperty")
                    .getJSONObject("GeocoderMetaData")
                    .getString("text");
        } catch (Exception e) {
            return "Manzil topilmadi";
        }
    }
}
