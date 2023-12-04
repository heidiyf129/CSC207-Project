package interface_adapter;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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
    public Integer getAqi(String city, String state, String country) {
        String aqiString = fetchAirQualityData(city, state, country);
        try {
            // If the API call is successful and the response contains a numeric AQI, it will be parsed.
            // If the response contains a message or is not a number, it will throw an exception caught by the catch block.
            return Integer.parseInt(aqiString);
        } catch (NumberFormatException e) {
            // This catch block handles the case where the AQI data is not available or the API response is not a number.
            return null;
        }
    }


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