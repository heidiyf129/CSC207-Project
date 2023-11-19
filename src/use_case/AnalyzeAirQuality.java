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

}

