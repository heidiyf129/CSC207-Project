package interface_adapter;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

import org.json.JSONObject;
import okhttp3.*;

public class APIAdapter implements data_access.AirQualityDao {
    private static String API_KEY;

    static {
        Properties prop = new Properties();
        try (InputStream input = APIAdapter.class.getClassLoader().getResourceAsStream("config.properties")) {
            prop.load(input);
            API_KEY = prop.getProperty("API_KEY");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static final String API_URL_TEMPLATE = "http://api.airvisual.com/v2/city?city=%s&state=%s&country=%s&key=" + API_KEY;
    private static final MediaType MEDIA_TYPE = MediaType.parse("text/plain");

    @Override
    public String fetchAirQualityData(String city, String state, String country) {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        String apiUrl = String.format(API_URL_TEMPLATE, city, state, country);
        Request request = new Request.Builder().url(apiUrl).build();
        try {
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();

            // Step 1: Print the received JSON
            System.out.println("Received JSON: " + responseBody);

            if (!response.isSuccessful()) {
                return "API responded with status: " + response.code();
            }

            // Step 2: Parse the JSON carefully
            JSONObject jsonResponse = new JSONObject(responseBody);
            JSONObject data = jsonResponse.optJSONObject("data");
            if (data != null) {
                JSONObject current = data.optJSONObject("current");
                if (current != null) {
                    JSONObject pollution = current.optJSONObject("pollution");
                    if (pollution != null && pollution.has("aqius")) {
                        int aqi = pollution.getInt("aqius");
                        return Integer.toString(aqi);  // Convert AQI to string
                    }
                }
            }
            return "AQI data not available.";  // Return some default or error message

        } catch (IOException e) {
            e.printStackTrace();
            return "Error fetching data.";
        }
    }
}