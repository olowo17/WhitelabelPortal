package lazyprogrammer.jwtdemo.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;

import java.util.Optional;

public class JSONHelper {

    private static ObjectMapper mapper;

    private static ObjectMapper getMapper() {

        if (mapper != null) return mapper;

        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


        objectMapper.registerModule(new JavaTimeModule());

        mapper = objectMapper;

        return mapper;

    }



    public static Optional<String> safeMarshal(Object obj) throws JsonProcessingException {

        if (obj == null) return Optional.empty();

        return Optional.of(getMapper().writeValueAsString(obj));

    }


    public static <DataType> DataType unMarshal(String json, Class<DataType> classType) throws JsonProcessingException {

        DataType obj = getMapper().readValue(json, classType);

        return obj;

    }

    public static <DestinationType> Optional<DestinationType> copyFields(Object srcObj, Class<DestinationType> destTypeClass) throws JsonProcessingException {

        Optional<String> marshal = safeMarshal(srcObj);

        return marshal.map(s -> {
            try {
                return unMarshal(s, destTypeClass);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });

    }

}
