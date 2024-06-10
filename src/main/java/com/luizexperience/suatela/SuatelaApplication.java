package com.luizexperience.suatela;

import com.luizexperience.suatela.model.DadosSerie;
import com.luizexperience.suatela.service.ConsumoAPI;
import com.luizexperience.suatela.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SuatelaApplication implements CommandLineRunner {

	public static void main(String[] args){SpringApplication.run(SuatelaApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		var consumindoApi= new ConsumoAPI();
		var json= consumindoApi.obterDados("http://www.omdbapi.com/?t=the+supernatural&apikey=b92c3376");
		System.out.println(json);
		ConverteDados conversor = new ConverteDados();
		DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
		System.out.println(dados);

	}
}


