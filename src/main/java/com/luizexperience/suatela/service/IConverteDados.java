package com.luizexperience.suatela.service;

public interface IConverteDados {
   <T> T obterDados(String json, Class<T> classe);
}
