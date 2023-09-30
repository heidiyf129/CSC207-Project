import java.io.*;
import okhttp3.*;
public class Main {
    public static void main(String []args) throws IOException{
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://api.airvisual.com/v2/nearest_city?key=788d7141-476d-41bd-8acd-17a3f9ff5d55")
                .build();
        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());
    }
}