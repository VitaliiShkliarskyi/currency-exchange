package com.example.currencyexchange.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

@Component
public class HttpClient {
    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private ObjectMapper objectMapper = new ObjectMapper();
    {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public <T> List<T> get(String url, Class<T> clazz) {
        HttpGet request = new HttpGet(url);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();
            String json = EntityUtils.toString(entity);
            List<T> result = new ArrayList<>();
            JsonNode rootNode = objectMapper.readTree(json);
            for (JsonNode node : rootNode) {
                T obj = objectMapper.treeToValue(node, clazz);
                result.add(obj);
            }
            return result.subList(0, Math.min(result.size(), 2));
            //return objectMapper.readValue(entity.getContent(), clazz);
        } catch (IOException e) {
            throw new RuntimeException("Can't get info from URL: " + url, e);
        }
    }
}