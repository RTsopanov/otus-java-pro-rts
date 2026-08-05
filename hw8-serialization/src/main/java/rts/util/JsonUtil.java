package rts.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JsonUtil {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public <T> List<T> fromJson(File file, Class<T> clazz) {
        try {
            return objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void toJson(File file, Object object) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}