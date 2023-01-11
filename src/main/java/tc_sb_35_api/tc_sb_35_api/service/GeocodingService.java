package tc_sb_35_api.tc_sb_35_api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;

import lombok.extern.slf4j.Slf4j;
import tc_sb_35_api.tc_sb_35_api.model.Clinic;

@Slf4j
@Service
public class GeocodingService {

    // Annotation to get value of geocoding.apikey in application.properties
    @Value("${geocoding.apikey}")
    private String apiKey;

    public Clinic getCoordenates(Clinic clinic) {
        log.info("INTERNAL PROCESS REQUEST: get coordenates from Google Api");
        // Taken from:
        // https://developers.google.com/maps/documentation/geocoding/client-library
        // https://github.com/googlemaps/google-maps-services-java
        try {
            GeoApiContext context = new GeoApiContext.Builder().apiKey(apiKey).build();
            // Creates a string including addres, city and department
            // Address string will be something like "Calle 4 15-74, Bogotá D.C., Bogotá D.C."
            String address = String.format("%s, %s, %s", clinic.getClinicAddress(), clinic.getClinicCity(), clinic.getClinicDepartment().getDepartment());
            // Console check of address that will be sent to geocoding API
            log.info("***** ADDRESS SENT TO GOOGLE: " + address);
            GeocodingResult[] results = GeocodingApi.geocode(context, address).await();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            // Set into clinic latitude and longitude values received from geocoding API
            // Must parse to Double for higher precision
            clinic.setClinicLatitude(Double.parseDouble(gson.toJson(results[0].geometry.location.lat)));
            clinic.setClinicLongitude(Double.parseDouble(gson.toJson(results[0].geometry.location.lng)));
            // Console check of received latitude and longitude
            log.info("***** GOOGLE RESPONSE FORMATTED ADDRESS: " + gson.toJson(results[0].formattedAddress));
            log.info("***** GOOGLE RESPONSE LOCATION LATITUDE: " + gson.toJson(results[0].geometry.location.lat));
            log.info("***** GOOGLE RESPONSE LOCATION LONGITUDE: " + gson.toJson(results[0].geometry.location.lng));
            // Close connection to geocoding API
            context.shutdown();
            log.info("INTERNAL PROCESS RESPONSE: clinic with coordenates");
            return clinic;
        } catch (Exception e) {
            // Returns clinic without latitude or longitude
            return clinic;
        }
    }

    
}
