package io.github.hectorvent.floci.services.appsync.graphql.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class DynamoDbUtil {

    private final ObjectMapper objectMapper;

    public DynamoDbUtil(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Map<String, Object> toDynamoDB(Object value) {
        if (value == null) {
            return Collections.singletonMap("NULL", true);
        }
        if (value instanceof String s) {
            return Collections.singletonMap("S", s);
        }
        if (value instanceof Boolean b) {
            return Collections.singletonMap("BOOL", b);
        }
        if (value instanceof Number n) {
            return Collections.singletonMap("N", n.toString());
        }
        if (value instanceof List<?> list) {
            List<Object> converted = new ArrayList<>(list.size());
            for (Object item : list) {
                converted.add(toDynamoDB(item));
            }
            return Collections.singletonMap("L", converted);
        }
        if (value instanceof Map<?, ?> map) {
            Map<String, Object> converted = new LinkedHashMap<>(map.size());
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                converted.put(entry.getKey().toString(), toDynamoDB(entry.getValue()));
            }
            return Collections.singletonMap("M", converted);
        }
        return Collections.singletonMap("S", value.toString());
    }

    public String toDynamoDBJson(Object value) {
        try {
            return objectMapper.writeValueAsString(toDynamoDB(value));
        } catch (JsonProcessingException e) {
            return "{}";
        }
    }

    public Map<String, Object> toString(String value) {
        return Collections.singletonMap("S", value);
    }

    public Map<String, String> toStringJson(String value) {
        return Collections.singletonMap("S", value);
    }

    public Map<String, Object> toNumber(Number value) {
        return Collections.singletonMap("N", value.toString());
    }

    public Map<String, String> toNumberJson(Number value) {
        return Collections.singletonMap("N", value.toString());
    }

    public Map<String, Object> toBinary(String value) {
        String encoded = Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8));
        return Collections.singletonMap("B", encoded);
    }

    public Map<String, String> toBinaryJson(String value) {
        String encoded = Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8));
        return Collections.singletonMap("B", encoded);
    }

    public Map<String, Object> toBoolean(Boolean value) {
        return Collections.singletonMap("BOOL", value);
    }

    public Map<String, String> toBooleanJson(Boolean value) {
        return Collections.singletonMap("BOOL", value.toString());
    }

    public Map<String, Object> toNull() {
        return Collections.singletonMap("NULL", true);
    }

    public Map<String, String> toNullJson() {
        return Collections.singletonMap("NULL", "true");
    }

    public Map<String, Object> toList(List<?> value) {
        List<Object> converted = new ArrayList<>(value.size());
        for (Object item : value) {
            converted.add(toDynamoDB(item));
        }
        return Collections.singletonMap("L", converted);
    }

    public Map<String, Object> toListJson(List<?> value) {
        return toList(value);
    }

    public Map<String, Object> toMap(Map<String, Object> value) {
        Map<String, Object> converted = new LinkedHashMap<>(value.size());
        for (Map.Entry<String, Object> entry : value.entrySet()) {
            converted.put(entry.getKey(), toDynamoDB(entry.getValue()));
        }
        return Collections.singletonMap("M", converted);
    }

    public Map<String, Object> toMapJson(Map<String, Object> value) {
        return toMap(value);
    }

    public Map<String, Object> toMapValues(Map<String, Object> value) {
        return toMap(value);
    }

    public Map<String, Object> toMapValuesJson(Map<String, Object> value) {
        return toMap(value);
    }

    public Map<String, Object> toStringSet(List<String> value) {
        return Collections.singletonMap("SS", new ArrayList<>(value));
    }

    public Map<String, Object> toStringSetJson(List<String> value) {
        return toStringSet(value);
    }

    public Map<String, Object> toNumberSet(List<? extends Number> value) {
        List<String> strings = new ArrayList<>(value.size());
        for (Number n : value) {
            strings.add(n.toString());
        }
        return Collections.singletonMap("NS", strings);
    }

    public Map<String, Object> toNumberSetJson(List<? extends Number> value) {
        return toNumberSet(value);
    }

    public Map<String, Object> toBinarySet(List<String> value) {
        List<String> encoded = new ArrayList<>(value.size());
        for (String s : value) {
            encoded.add(Base64.getEncoder().encodeToString(s.getBytes(StandardCharsets.UTF_8)));
        }
        return Collections.singletonMap("BS", encoded);
    }

    public Map<String, Object> toBinarySetJson(List<String> value) {
        return toBinarySet(value);
    }
}
