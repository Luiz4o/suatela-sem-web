package com.luizexperience.suatela.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luizexperience.suatela.model.DadosSerie;

public class ConverteDados implements IConverteDados{
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T obterDados(String json, Class<T> classe) {
        try {
            return mapper.readValue(json,classe);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
