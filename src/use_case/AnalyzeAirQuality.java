package use_case;

import entity.AirQuality;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import java.util.concurrent.TimeUnit;
import java.io.IOException;
import org.json.JSONObject;

public class AnalyzeAirQuality {
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static final int MAX_WIDTH = 95;

    public String getAirQuality(int aqi) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS)
                .build();

        String prompt = "Provide tips/suggestions when being outside with air quality index " + aqi + ", give your response in 250 characters, give tips in full sentences only, NOT in bullet form, NOT in numbered form, the tone of the message should sound professional and helping";
        String apiKey = "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";

        String jsonPayload = "{\"model\": \"gpt-3.5-turbo\", \"messages\": [{\"role\": \"system\", \"content\": \"You are a helpful assistant.\"}, {\"role\": \"user\", \"content\": \"" + prompt + "\"}]}";

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, jsonPayload);

        Request request = new Request.Builder()
                .url(API_URL)
                .post(body)
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            String responseBody = response.body().string();

            JSONObject jsonObject = new JSONObject(responseBody);
            String content = jsonObject.getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content");

            return content;
        } catch (Exception e) {
            e.printStackTrace();
            return "Unable to retrieve tips: " + e.getMessage();
        }
    }

    private String formatTipsForDisplay(String tips) {
        StringBuilder formattedTips = new StringBuilder();
        String[] words = tips.split(" ");
        int currentLineLength = 0;

        for (String word : words) {
            if (currentLineLength + word.length() > MAX_WIDTH) {
                formattedTips.append("\n");
                currentLineLength = 0;
            }
            formattedTips.append(word).append(" ");
            currentLineLength += word.length() + 1;
        }

        return formattedTips.toString().trim();
    }

    public String analyze(AirQuality airQuality) {
        int aqi = airQuality.getAqi();
        String category;
        String tips;

        if (aqi <= 50) {
            category = "Good";
            tips = getAirQuality(aqi);
        } else if (aqi <= 100) {
            category = "Moderate";
            tips = getAirQuality(aqi);
        } else if (aqi <= 150) {
            category = "Unhealthy for sensitive groups";
            tips = getAirQuality(aqi);
        } else if (aqi <= 200) {
            category = "Unhealthy";
            tips = getAirQuality(aqi);
        } else if (aqi <= 300) {
            category = "Very unhealthy";
            tips = getAirQuality(aqi);
        } else {
            category = "Hazardous";
            tips = getAirQuality(aqi);
        }
        tips = formatTipsForDisplay(tips);
        return category + "\n\nSuggestions: \n" + tips;
    }
    // Example method to print current AQI - This method is not used in the main flow
    public void printCurrentAQI(AirQuality airQuality) {
        System.out.println("Current AQI: " + airQuality.getAqi());
    }
    // Method to demonstrate method overloading
    public void printCurrentAQI() {
        System.out.println("AQI data not available");
    }

    // Additional method to check air quality level
    public String checkAirQualityLevel(int aqi) {
        if (aqi <= 50) return "Good";
        else if (aqi <= 100) return "Moderate";
        else if (aqi <= 150) return "Unhealthy for Sensitive Groups";
        else if (aqi <= 200) return "Unhealthy";
        else if (aqi <= 300) return "Very Unhealthy";
        else return "Hazardous";
    }

    // Method to simulate a delay (for demonstration purposes)
    public void simulateDelay() {
        try {
            Thread.sleep(1000); // Simulate a 1-second delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    public int generateRandomAQI() {
        return (int) (Math.random() * 500);
    }

    // Log AQI information for debugging
    public void logAQIInfo(AirQuality airQuality) {
        System.out.println("Logging AQI Information: " + airQuality.toString());
    }

    // Compare two AQI values
    public int compareAQI(AirQuality first, AirQuality second) {
        return Integer.compare(first.getAqi(), second.getAqi());
    }

    // Convert AQI to a descriptive string
    public String aqiToString(int aqi) {
        if (aqi <= 50) return "Good (" + aqi + ")";
        else if (aqi <= 100) return "Moderate (" + aqi + ")";
        else if (aqi <= 150) return "Unhealthy for Sensitive Groups (" + aqi + ")";
        else if (aqi <= 200) return "Unhealthy (" + aqi + ")";
        else if (aqi <= 300) return "Very Unhealthy (" + aqi + ")";
        else return "Hazardous (" + aqi + ")";
    }

    // A method to estimate outdoor activity safety based on AQI
    public String estimateActivitySafety(int aqi) {
        if (aqi <= 100) return "Safe for all outdoor activities";
        else if (aqi <= 150) return "Consider reducing intense outdoor activities";
        else return "Limit outdoor activities";
    }

    // A method to get health advice based on AQI
    public String getHealthAdvice(int aqi) {
        if (aqi <= 50) return "Enjoy your day, air quality is good!";
        else if (aqi <= 100) return "Air quality is acceptable; however, there might be a risk for some people, particularly those who are unusually sensitive to air pollution.";
        else if (aqi <= 150) return "Members of sensitive groups may experience health effects. The general public is not likely to be affected.";
        else if (aqi <= 200) return "Everyone may begin to experience health effects; members of sensitive groups may experience more serious health effects.";
        else if (aqi <= 300) return "Health alert: everyone may experience more serious health effects.";
        else return "Health warnings of emergency conditions. The entire population is more likely to be affected.";
    }

    // A method to suggest indoor air purifiers based on AQI
    public String suggestAirPurifiers(int aqi) {
        if (aqi > 150) return "Consider using an air purifier in indoor spaces.";
        else return "Air purifier not necessary under current air quality.";
    }

    // A method to suggest ventilation strategies based on AQI
    public String suggestVentilationStrategies(int aqi) {
        if (aqi <= 100) return "Normal ventilation is fine.";
        else return "Reduce ventilation to limit outdoor air intake.";
    }

    // Method to validate AQI values
    public boolean isValidAQI(int aqi) {
        return aqi >= 0 && aqi <= 500;
    }

    // Method to assess environmental impact based on AQI
    public String assessEnvironmentalImpact(int aqi) {
        if (aqi > 200) return "Significant environmental impact likely.";
        else return "Minimal environmental impact.";
    }

    // Method to log AQI data for historical analysis
    public void logHistoricalAQIData(AirQuality airQuality) {
        // Method to log historical AQI data for analysis
        System.out.println("Logging Historical AQI Data: " + airQuality.toString());
    }

    // Method to predict future AQI trends based on historical data
    public String predictFutureAQITrend(AirQuality[] historicalData) {
        // Simplified prediction logic
        return "Future AQI trend prediction based on historical data.";
    }

    // Method to analyze the effect of AQI on local fauna
    public String analyzeEffectOnFauna(int aqi) {
        if (aqi > 150) return "Potential adverse effects on local wildlife.";
        else return "No significant impact on local fauna.";
    }

    // Method to recommend protective measures for outdoor workers
    public String recommendProtectiveMeasuresForWorkers(int aqi) {
        if (aqi > 150) return "Recommend wearing masks and limiting outdoor work hours.";
        else return "No special protective measures needed.";
    }

    // Method to evaluate the need for public health alerts
    public String evaluatePublicHealthAlertNecessity(int aqi) {
        if (aqi > 200) return "Issuing a public health alert is advisable.";
        else return "Public health alert not necessary at this time.";
    }

    // Method to assess the impact of AQI on visibility
    public String assessImpactOnVisibility(int aqi) {
        if (aqi > 150) return "Reduced visibility likely, exercise caution while driving.";
        else return "Normal visibility conditions.";
    }

    // Method to provide guidelines for HVAC system settings in different AQI conditions
    public String provideHVACGuidelines(int aqi) {
        if (aqi > 150) return "Increase air filtration and reduce intake of outside air.";
        else return "Normal HVAC settings are adequate.";
    }

    // Method to suggest indoor activities based on AQI
    public String suggestIndoorActivities(int aqi) {
        if (aqi > 100) return "Consider indoor activities such as yoga, reading, or indoor sports.";
        else return "Outdoor activities are safe, but indoor options are always available.";
    }

    // Method to assess the suitability of outdoor sports events
    public String assessOutdoorSportsSuitability(int aqi) {
        if (aqi > 150) return "Postpone or relocate outdoor sports events.";
        else return "Outdoor sports events can proceed as scheduled.";
    }

    // Method to suggest types of air quality monitoring equipment
    public String suggestMonitoringEquipment() {
        return "Recommended equipment: AQI sensors, particulate matter detectors, and gas analyzers.";
    }

    // Method to analyze the trend of AQI over a week
    public String analyzeWeeklyTrend(AirQuality[] weeklyData) {
        // Simplified trend analysis
        return "Analysis of AQI trend over the past week.";
    }

    // Method to provide general information about air quality
    public String provideGeneralAirQualityInfo() {
        return "General information about air quality and its importance.";
    }

    // Method to assess air quality impact on building structures
    public String assessImpactOnBuildings(int aqi) {
        if (aqi > 200) return "Potential corrosive effects on buildings and structures.";
        else return "Minimal impact on buildings.";
    }

    // Method to suggest air quality improvements in urban planning
    public String suggestUrbanPlanningImprovements() {
        return "Recommendations for green spaces, pollution control, and urban forestry to improve air quality.";
    }

    // Method to evaluate the effect of AQI on solar panel efficiency
    public String evaluateSolarPanelEfficiency(int aqi) {
        if (aqi > 150) return "Potential reduction in solar panel efficiency due to particulate matter.";
        else return "No significant impact on solar panel efficiency.";
    }

    // Method to provide guidelines for using air quality masks
    public String provideMaskUsageGuidelines(int aqi) {
        if (aqi > 150) return "Use N95 masks or higher for effective filtration.";
        else return "Masks not necessary under current air quality conditions.";
    }

    // Method to assess the need for indoor air quality monitoring
    public String assessIndoorAirMonitoringNecessity(int aqi) {
        if (aqi > 100) return "Advisable to monitor indoor air quality.";
        else return "Indoor air quality monitoring not critical.";
    }

    // Method to offer advice on planting vegetation to improve air quality
    public String adviseOnVegetationForAirQuality() {
        return "Planting trees and shrubs can help filter air pollutants and improve air quality.";
    }

    // Method to analyze the seasonal variation of AQI
    public String analyzeSeasonalVariation(AirQuality[] seasonalData) {
        // Simplified seasonal variation analysis
        return "Analysis of AQI seasonal variation.";
    }

    // Method to suggest best practices for reducing personal air pollution
    public String suggestPersonalPollutionReductionPractices() {
        return "Recommendations for reducing personal contribution to air pollution, such as using public transportation and reducing use of aerosols.";
    }

    // Method to provide education on the health effects of different AQI levels
    public String educateOnHealthEffects(int aqi) {
        if (aqi > 200) return "Education on severe health effects due to poor air quality.";
        else return "General health education related to air quality.";
    }

    // Method to suggest smart home integrations for air quality monitoring
    public String suggestSmartHomeIntegrations() {
        return "Recommendations for smart home devices and systems to monitor and control air quality.";
    }

    // Method to evaluate the impact of vehicular emissions on AQI
    public String evaluateVehicularEmissionsImpact() {
        return "Assessment of the contribution of vehicular emissions to air quality degradation.";
    }

    // Method to provide guidelines for air quality during public events
    public String providePublicEventAirQualityGuidelines(int aqi) {
        if (aqi > 150) return "Guidelines for maintaining air quality during large public events.";
        else return "Standard air quality measures are sufficient for public events.";
    }

    // Method to analyze the impact of industrial emissions on AQI
    public String analyzeIndustrialEmissionsImpact() {
        return "Analysis of the effect of industrial emissions on local air quality.";
    }

}

