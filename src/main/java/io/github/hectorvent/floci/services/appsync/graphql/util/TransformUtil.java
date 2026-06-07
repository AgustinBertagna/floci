package io.github.hectorvent.floci.services.appsync.graphql.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransformUtil {

    private final ObjectMapper objectMapper;

    public TransformUtil(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> toDynamoDBFilterExpression(Object input) {
        if (input == null) {
            return Collections.emptyMap();
        }
        Map<String, Object> source;
        if (input instanceof Map<?, ?> map) {
            source = (Map<String, Object>) map;
        } else if (input instanceof String s) {
            try {
                source = objectMapper.readValue(s, Map.class);
            } catch (Exception e) {
                return Collections.emptyMap();
            }
        } else {
            return Collections.emptyMap();
        }

        Map<String, Object> result = new HashMap<>();
        if (source.containsKey("expression")) {
            result.put("FilterExpression", source.get("expression"));
        }
        if (source.containsKey("expressionNames")) {
            result.put("ExpressionAttributeNames", source.get("expressionNames"));
        }
        if (source.containsKey("expressionValues")) {
            result.put("ExpressionAttributeValues", source.get("expressionValues"));
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> toElasticsearchQueryDSL(Object input) {
        if (input == null) {
            return Collections.emptyMap();
        }
        if (input instanceof Map<?, ?> map) {
            return (Map<String, Object>) map;
        }
        if (input instanceof String s) {
            try {
                return objectMapper.readValue(s, Map.class);
            } catch (Exception e) {
                return Collections.emptyMap();
            }
        }
        return Collections.emptyMap();
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> toSubscriptionFilter(Object input) {
        if (input == null) {
            return Collections.emptyMap();
        }
        if (input instanceof Map<?, ?> map) {
            return (Map<String, Object>) map;
        }
        if (input instanceof String s) {
            try {
                return objectMapper.readValue(s, Map.class);
            } catch (Exception e) {
                return Collections.emptyMap();
            }
        }
        return Collections.emptyMap();
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> toSubscriptionFilter(Object input, List<String> filterKeys) {
        Map<String, Object> result = toSubscriptionFilter(input);
        Map<String, Object> mutableResult = new HashMap<>(result);
        if (filterKeys != null && !filterKeys.isEmpty()) {
            mutableResult.put("filterKeys", filterKeys);
        }
        return mutableResult;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> toSubscriptionFilter(Object input, List<String> filterKeys,
                                                     Map<String, String> headerOverrides) {
        Map<String, Object> result = toSubscriptionFilter(input, filterKeys);
        Map<String, Object> mutableResult = new HashMap<>(result);
        if (headerOverrides != null && !headerOverrides.isEmpty()) {
            mutableResult.put("headerOverrides", headerOverrides);
        }
        return mutableResult;
    }
}
