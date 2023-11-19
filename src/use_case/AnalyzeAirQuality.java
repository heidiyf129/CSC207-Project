package use_case;

import entity.AirQuality;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;
public class AnalyzeAirQuality {
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static final int MAX_WIDTH = 95;

    public String getAirQuality(int aqi) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(100, TimeUnit.SECONDS) // Increase connection timeout
                .readTimeout(100, TimeUnit.SECONDS)    // Increase read timeout
                .writeTimeout(100, TimeUnit.SECONDS)   // Increase write timeout
                .build();;

        String prompt = "Provide tips when being outside with air quality index" + aqi + ", give your response in 250 characters, give tips in full sentences, not in bullet form or numbered, the tone of the message should sound professional";;
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

        try {
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();

            // Parse the JSON response
            JSONObject jsonObject = new JSONObject(responseBody);
            String content = jsonObject.getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content");

            return content; // Return only the content field
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Unable to retrieve tips";
    }

    private String formatTipsForDisplay(String tips) {
        StringBuilder formattedTips = new StringBuilder();
        String[] words = tips.split(" ");
        int currentLineLength = 0;

        for (String word : words) {
            // Check if adding the next word exceeds the MAX_WIDTH
            if (currentLineLength + word.length() > MAX_WIDTH) {
                formattedTips.append("\n"); // Start a new line
                currentLineLength = 0;
            }
            formattedTips.append(word).append(" ");
            currentLineLength += word.length() + 1; // +1 for the space
        }

        return formattedTips.toString().trim(); // Trim trailing spaces
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
        }
        //... Complete for all possible AQI ranges
        else {
            category = "Hazardous";
            tips = getAirQuality(aqi);
        }
        tips = formatTipsForDisplay(tips);
        return category + "\n\nSuggestions: \n" + tips;
    }

}

