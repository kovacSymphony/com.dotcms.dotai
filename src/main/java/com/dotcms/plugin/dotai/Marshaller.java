package com.dotcms.plugin.dotai;

import com.dotcms.plugin.dotai.app.AppKeys;
import com.dotcms.plugin.dotai.app.AppConfig;
import com.dotmarketing.util.ConfigUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Marshaller {

    private static final ObjectMapper MAPPER = createObjectMapper();

    public static <T> void marshal(OutputStream output, T value) throws IOException {
        MAPPER.writeValue(output, value);
    }

    public static <T> String marshal(T value) throws IOException {
        return MAPPER.writeValueAsString(value);
    }

    public static <T> T unmarshal(InputStream input, Class<T> type) throws IOException {
        return MAPPER.readValue(input, type);
    }

    public static <T> T unmarshal(String input, Class<T> type) throws IOException {
        if (input == null) {
            return null;
        }
        return MAPPER.readValue(input, type);
    }

    public static JsonNode readTree(String input) throws JsonProcessingException {
        if (input == null) {
            return null;
        }
        return MAPPER.readTree(input);
    }

    public static <T> T treeToValue(JsonNode node, Class<T> type) throws JsonProcessingException {
        if (node == null) {
            return null;
        }
        return MAPPER.treeToValue(node, type);
    }

//    public static AppConfig getConfig() throws IOException {
//
//        final File installedAppYaml = new File(ConfigUtils.getAbsoluteAssetsRootPath() + File.separator + "server"
//            + File.separator + "apps" + File.separator + AppKeys.APP_YAML_NAME);
//
//        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
//        InputStream inputStream = mapper.getClass()
//            .getResourceAsStream(installedAppYaml.getAbsolutePath());
//        return mapper.readValue(inputStream, AppConfig.class);
//    }

    private static ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper()
            .findAndRegisterModules()
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }
}
