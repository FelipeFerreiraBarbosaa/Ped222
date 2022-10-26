package com.mycompany.ped2;

/**
 *
 * @author Felipe Ferreira
 */
import java.util.ArrayList;

public class Algoritmos {

    public static void main(String arg[]) {

        Grafo inicial = new Grafo();
        Grafo resultado = new Grafo();

        Aresta arestaAux;
        Vertice verticeAux1, verticeAux2;
        int opcao = 5, peso;
        String origem, destino;

        while (opcao != 0) {
            System.out.println("1 - Obter caminho de Dijkstra");
            System.out.println("2 - Obter arvore de Busca em Profundidade");
            System.out.println("3 - Obter arvore de Busca em Largura");
            System.out.println("0 - fim");

            opcao = Keyboard.readInt();

            //dando um reset no grafo de resultado
            resultado.clearLists();

            //limpando verificadores booleanos
            inicial.limparArestaVisitada();
            inicial.limparVerticeVisitado();

            switch (opcao) {
                case 1:
                    //Algoritmo de Dijkstra

                    verticeAux1 = inicial.acharVertice(Keyboard.readString());
                    verticeAux2 = inicial.acharVertice(Keyboard.readString());
                    resultado.setVertices(inicial.encontrarMenorCaminhoDijkstra(verticeAux1, verticeAux2));

                    System.out.println(resultado.getVertices());
                    break;

                case 2:
                    //Algoritmo de Busca em Profundidade

                    origem = Keyboard.readString();
                    destino = Keyboard.readString();
                    resultado.setArestas(inicial.buscaEmProfundidade(origem, destino));

                    resultado.imprimeArvore();
                    break;
                
                case 3:
                    //Algoritmo de Busca em Largura

                    origem = Keyboard.readString();
                    destino = Keyboard.readString();
                    resultado.setArestas(inicial.buscaEmLargura(origem, destino));

                    resultado.imprimeArvore();
                    break;
                
                case 0:
                    break;
                default:
                    System.out.println("invalido");
                    break;
            }

        }
    }
}

