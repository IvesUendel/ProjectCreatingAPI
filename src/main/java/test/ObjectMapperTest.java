package test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ObjectMapperTest {
    @Test
    public void testInsertUser() throws IOException{
        URL externalApiUrl = new URL("https://randomuser.me/api/");
        HttpURLConnection externalApiConnection = (HttpURLConnection) externalApiUrl.openConnection();
        externalApiConnection.setRequestMethod("GET");

        int externalApiResponseCode = externalApiConnection.getResponseCode();
        assertEquals(200, externalApiResponseCode);

        BufferedReader externalApiReader = new BufferedReader(new InputStreamReader(externalApiConnection.getInputStream()));
        StringBuilder externalApiResponse = new StringBuilder();
        String line;
        while ((line = externalApiReader.readLine()) != null) {
            externalApiResponse.append(line);
        }
        externalApiReader.close();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode externalApiJsonNode = objectMapper.readTree(externalApiResponse.toString());

        String name = externalApiJsonNode.path("results").get(0).path("name").path("first").asText();
        String password = "teste1234";

        URL localApiUrl = new URL("http://127.0.0.1:4567/users");
        HttpURLConnection localApiConnection = (HttpURLConnection) localApiUrl.openConnection();
        localApiConnection.setRequestMethod("POST");
        localApiConnection.setRequestProperty("Content-Type", "application/json");
        localApiConnection.setDoOutput(true);

        String requestBody = String.format("{\"name\": \"%s\", \"password\": \"%s\"}", name, password);
        localApiConnection.getOutputStream().write(requestBody.getBytes());

        int localApiResponseCode = localApiConnection.getResponseCode();
        assertEquals(201, localApiResponseCode);

        externalApiConnection.disconnect();
        localApiConnection.disconnect();
    }
}
