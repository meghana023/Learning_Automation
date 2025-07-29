package apiTests;

import com.google.gson.Gson;
import kong.unirest.HttpResponse;
import kong.unirest.UnirestException;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ValidateFilePostcall extends APIUtil {

    private static final Logger logger = LogManager.getLogger(ValidateFilePostcall.class);
    private final String baseUrl = "https://dummy.restapiexample.com";
    private final String apiPath = "/api/v1/create";

    public static String readFileAsString(String file) throws IOException {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

    @Test(priority = 1)
    public void validatePostMethod() throws UnirestException, IOException {
        Map<String, String> auth = new HashMap<>();
        auth.put("username", "user");
        auth.put("password", "pass");

        String file = System.getProperty("user.dir") + "/json/test.json";

        String json = readFileAsString(file);

        String payload = new Gson().toJson(json);

        System.out.println(payload);


        HttpResponse<String> response = APIUtil.callApi(baseUrl,Constants.RestMethod.TYPE.POST,
                apiPath, new HashMap<>(), auth, new HashMap<>(),json.toString());

        logger.info("Full HTTP response: {}", response);

        int status = response.getStatus();
        String body = response.getBody();

        logger.info("Status Code: {}", status);
        logger.info("Response Body: {}", body);

        assert status == 200 : "Expected HTTP 200, got " + status;

        if (body.contains("Successfully! Record has been added.")) {
            logger.info("Employee record is created successfully." + body);
        } else {
            logger.warn("Unexpected response content");
        }
    }
}
