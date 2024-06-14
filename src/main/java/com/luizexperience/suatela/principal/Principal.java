package com.luizexperience.suatela.principal;

import com.luizexperience.suatela.model.DadosEpisodio;
import com.luizexperience.suatela.model.DadosSerie;
import com.luizexperience.suatela.model.DadosTemporada;
import com.luizexperience.suatela.service.ConsumoAPI;
import com.luizexperience.suatela.service.ConverteDados;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner read= new Scanner(System.in);

    private ConverteDados conversor= new ConverteDados();

    private ConsumoAPI consumo= new ConsumoAPI();

    private final String ENDERECO="http://www.omdbapi.com/?t=";
    private final String API_KEY="&apikey=b92c3376";

    public void exibiMenu(){
        System.out.println("Digite o nome da serie para buscar");
        var nomeSerie= read.nextLine();
        var json= consumo.obterDados(ENDERECO+nomeSerie.replace(" ","+")+API_KEY);

        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);

        System.out.println(dados);
        List<DadosTemporada> temporadas = new ArrayList<>();

        for (int i=1; i<=dados.totalTemporadas(); i++){
			json= consumo.obterDados(ENDERECO+nomeSerie.replace(" ","+")+"&season="+i+"&apikey=b92c3376");
			DadosTemporada dadosTemporada= conversor.obterDados(json,DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}
		temporadas.forEach(System.out::println);// forma utilizando o ferEach para imprimir cade elemento na tela

//        for(int i=0; i< dados.totalTemporadas();i++){
//            List<DadosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
//            for (int j=0; j<episodiosTemporada.size(); j++){
//                System.out.println(episodiosTemporada.get(j).titulo());
//            }
//        }

        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo()))); //LAMBDA

  //      List<String> nomes = Arrays.asList("Luiz","Lincoln","Vitoria","Luciano");

        //nomes.stream().sorted().forEach(System.out::println);

        List<DadosEpisodio> dadosEpisodios = temporadas.stream() //flatMap serve para juntar Listas/dados em uma unica lista, ou trabalhar diretamente com esse agrupamento de dados
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());//A diferença entre o collectors.toList e o toList() diretamente é que o toList() cria uma lista imutavel ou seja nao podemos adicionar ou mudar nada desta lista, enquanto o collectors é aberto para alteração

        System.out.println("Top 5");

        dadosEpisodios.stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
                .limit(5)
                .forEach(System.out::println);
    }
}
