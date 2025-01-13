package org.twentyfive.shop_manager_api_layer.utilities.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.twentyfive.shop_manager_api_layer.utilities.classes.LabelAndValue;

import java.io.IOException;
import java.util.List;

@Converter
public class LabelAndValueConverter implements AttributeConverter<List<LabelAndValue>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<LabelAndValue> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting List<LabelAndValue> to JSON", e);
        }
    }

    @Override
    public List<LabelAndValue> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, objectMapper.getTypeFactory().constructCollectionType(List.class, LabelAndValue.class));
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to List<LabelAndValue>", e);
        }
    }
}
