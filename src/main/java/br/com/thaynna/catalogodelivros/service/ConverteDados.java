package br.com.thaynna.catalogodelivros.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;

public class ConverteDados implements IConverteDados {
    private ObjectMapper mapper = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Override
    public <T> T obterDados(String json, Class<T> classe) {
        try {
            return mapper.readValue(json, classe);
        } catch (JsonProcessingException e) {
            throw new JsonConversionException("Falha ao converter JSON: " + e.getOriginalMessage(), e);
        }
    }
}

// Nova classe de exceção específica
    class JsonConversionException extends RuntimeException {
        public JsonConversionException(String message, Throwable cause) {
            super(message, cause);
        }
    }
