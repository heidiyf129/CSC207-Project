package use_case;

import entity.AirQuality;
public class AnalyzeAirQuality {
    public String analyze(AirQuality airQuality) {
        int aqi = airQuality.getAqi();
        String category;
        String tips;

        if (aqi <= 50) {
            category = "Good";
            tips = "活着";
        } else if (aqi <= 100) {
            category = "Moderate";
            tips = "快死了";
        }
        //... Complete for all possible AQI ranges
        else {
            category = "Hazardous";
            tips = "死了";
        }

        return category + "\n\nSuggestions: " + tips;
    }
}