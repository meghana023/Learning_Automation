package apiTests;

import kong.unirest.HttpResponse;
import kong.unirest.UnirestException;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class ValidatePostcall extends APIUtil {

    private static final Logger logger = LogManager.getLogger(ValidatePostcall.class);
    private final String baseUrl = "https://dummy.restapiexample.com";
    private final String apiPath = "/api/v1/create";

    @Test(priority = 1)
    public void validatePostMethod() throws UnirestException {
        Map<String, String> auth = new HashMap<>();
        auth.put("username", "user");
        auth.put("password", "pass");

        // Construct JSON body
        JSONObject child = new JSONObject()
                .put("child_name", "hi")
                .put("child_age", "123");

        JSONObject jsonObject = new JSONObject()
                .put("name", "test")
                .put("salary", "123")
                .put("age", "23")
                .put("child_details", child);

        JSONArray jsonArray = new JSONArray().put(jsonObject);

        logger.info("Request JSON array: {}", jsonArray.toString());

        HttpResponse<String> response = APIUtil.callApi(baseUrl,Constants.RestMethod.TYPE.POST,
                apiPath, new HashMap<>(), auth, new HashMap<>(),jsonArray.toString());

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
