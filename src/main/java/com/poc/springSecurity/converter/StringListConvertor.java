package com.poc.springSecurity.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class StringListConvertor implements AttributeConverter<List<String>, String> {

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        ObjectMapper objectMapper = new ObjectMapper();
        String value = "[]";
        try {
            value = objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return value;
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {

        List<String> dataList = new ArrayList<>();

        try {
            if(StringUtils.hasText(dbData)){
                dataList = new ObjectMapper().readValue(dbData, new TypeReference<List<String>>() {});
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return dataList;
    }
}
