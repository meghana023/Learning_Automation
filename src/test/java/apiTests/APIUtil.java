package apiTests;


import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class APIUtil {

    public static HttpResponse<String> response;
    public static String responseBody;
    public static long responseTime;
    public static String cookieValue;

    private static final Logger logger = LogManager.getLogger(APIUtil.class);


     public static HttpResponse<String> callApi(final String url, final Constants.RestMethod.TYPE methodType,
                                                final String apiPath, final Map<String, String> headers,
                                                final Map<String, String> auth, final Map<String, Object> queryParam,
                                                final String payload) {

        HttpResponse<String> response = null;

        switch (methodType) {
            case GET:
                response = Unirest.get(url + apiPath)
                        .headers(headers)
                        .basicAuth(auth.get("username"), auth.get("password"))
                        .queryString(queryParam)
                        .connectTimeout(1000000)
                        .socketTimeout(1000000)
                        .asString();
                break;
            case POST:
                logger.info("Request Payload : " + payload);

                response = Unirest.post(url + apiPath)
                        .basicAuth(auth.get("username"), auth.get("password"))
                        .headers(headers)
                        .body(payload)
                        .queryString(queryParam)
                        .connectTimeout(1000000)
                        .socketTimeout(1000000)
                        .asString();
                break;
            case PUT:
                logger.info("Request Payload : " + payload);

                response = Unirest.put(url + apiPath)
                        .basicAuth(auth.get("username"), auth.get("password"))
                        .headers(headers)
                        .body(payload)
                        .queryString(queryParam)
                        .asString();
                break;
            case DELETE:
                response = Unirest.delete(url + apiPath)
                        .basicAuth(auth.get("username"), auth.get("password"))
                        .headers(headers)
                        .queryString(queryParam)
                        .asString();
                break;
            default:
                break;
        }
        assert response != null;

        String responsecode = Integer.toString(response.getStatus());
        logger.info(responsecode + " :: " + methodType + " " + url + apiPath);

     return response;
    }

}
