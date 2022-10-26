
package com.mycompany.ped2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class Grafo {

    private ArrayList<Aresta> arestas = new ArrayList<Aresta>();
    private ArrayList<Vertice> vertices = new ArrayList<Vertice>();
    private boolean hasCycle = false;

    public void clearLists() {
        this.arestas.clear();
        this.vertices.clear();
        this.setHasCycle(false);
    }

    public boolean isHasCycle() {
        return hasCycle;
    }

    public void setHasCycle(boolean hasCycle) {
        this.hasCycle = hasCycle;
    }

    public void addAresta(int peso, String origem, String destino) {
        int i, j, k;

        //adiciona vertices e retorna posicao
        i = this.addVertice(origem);
        j = this.addVertice(destino);

        //adiciona aresta na lista
        Aresta a = new Aresta(peso,
                this.vertices.get(i),
                this.vertices.get(j));

        temCiclo(a);
        this.arestas.add(a);
        k = this.arestas.size();

        //adiciona aresta na lista de arestas incidentes em cada vertice
        this.vertices.get(i).addIncidentes(this.arestas.get(k - 1));
        this.vertices.get(j).addIncidentes(this.arestas.get(k - 1));
    }

    public void setArestas(ArrayList<Aresta> arestas) {
        this.clearLists();

        for (int i = 0; i < arestas.size(); i++) {
            this.addAresta(arestas.get(i).getPeso(),
                    arestas.get(i).getOrigem().getNome(),
                    arestas.get(i).getDestino().getNome());
        }
    }

    public void setVertices(ArrayList<Vertice> vertices) {
        this.clearLists();

        for (int i = 0; i < vertices.size(); i++) {

            //se ja existir na lista nao passara daqui
            if (this.posicaoVertice(vertices.get(i).getNome()) == this.vertices.size()) {
                //adicionando as arestas correspondentes a tais vertices
                for (int j = 0; j < vertices.get(i).getIncidentes().size(); j++) {

                    //se o adicionado for a origem desse seu incidente, e o seu destino estiver na lista de vertices
                    if ((vertices.get(i).getNome().equals(vertices.get(i).getIncidentes().get(j).getOrigem().getNome()))
                            && (this.posicaoVertice(vertices.get(i).getIncidentes().get(j).getDestino().getNome()) != this.vertices.size())) {

                        this.addAresta(vertices.get(i).getIncidentes().get(j).getPeso(),
                                vertices.get(i).getIncidentes().get(j).getOrigem().getNome(),
                                vertices.get(i).getIncidentes().get(j).getDestino().getNome());

                        //se o adicionado for o destino desse seu incidente, e o sua origem estiver na lista de vertices	
                    } else if ((vertices.get(i).getNome().equals(vertices.get(i).getIncidentes().get(j).getDestino().getNome()))
                            && (this.posicaoVertice(vertices.get(i).getIncidentes().get(j).getOrigem().getNome()) != this.vertices.size())) {

                        this.addAresta(vertices.get(i).getIncidentes().get(j).getPeso(),
                                vertices.get(i).getIncidentes().get(j).getOrigem().getNome(),
                                vertices.get(i).getIncidentes().get(j).getDestino().getNome());

                    }
                }
                this.addVertice(vertices.get(i).getNome());
            }
        }
    }

    public int addVertice(String nome) {
        int i = this.posicaoVertice(nome);

        if (i == this.vertices.size()) {
            this.vertices.add(new Vertice(nome));
            return (this.vertices.size() - 1);
        }

        return i;
    }

    public void limparVerticesPai() {
        for (int i = 0; i < this.getVertices().size(); i++) {
            this.getVertices().get(i).setPai(null);
        }
    }

    public void limparVerticeVisitado() {
        for (int i = 0; i < this.getVertices().size(); i++) {
            this.getVertices().get(i).setVisitado(false);
        }
    }

    public void limparArestaVisitada() {
        for (int i = 0; i < this.getArestas().size(); i++) {
            this.getArestas().get(i).setVisitado(false);
        }
    }

    public void imprimeArvore() {
        for (int i = 0; i < arestas.size(); i++) {
            System.out.print(this.arestas.get(i).getOrigem().getNome() + this.arestas.get(i).getDestino().getNome() + " - " + this.arestas.get(i).getPeso() + " | ");
        }
        System.out.println();
    }

    public ArrayList<Vertice> getVertices() {
        return vertices;
    }

    public int posicaoVertice(String nome) {
        int i;

        for (i = 0; i < this.vertices.size(); i++) {
            if (this.vertices.get(i).getNome().equals(nome)) {
                return i;
            }
        }

        return this.vertices.size();

    }

    public Vertice acharVertice(String nome) {
        return this.vertices.get(this.posicaoVertice(nome));
    }

    public Aresta acharAresta(Vertice vet1, Vertice vet2) {
        for (int i = 0; i < this.arestas.size(); i++) {
            if (((this.arestas.get(i).getOrigem().getNome().equals(vet1.getNome()))
                    && (this.arestas.get(i).getDestino().getNome().equals(vet2.getNome())))
                    || ((this.arestas.get(i).getOrigem().getNome().equals(vet2.getNome()))
                    && (this.arestas.get(i).getDestino().getNome().equals(vet1.getNome())))) {
                return this.arestas.get(i);
            }
        }
        return null;
    }

    public ArrayList<Aresta> getArestas() {
        return arestas;
    }

    public boolean temCiclo(Aresta aresta) {

        Vertice anterior = aresta.getDestino();

        for (int j = 0; j < this.getArestas().size(); j++) {

            for (int i = 0; i < this.getArestas().size(); i++) {

                if ((aresta == this.getArestas().get(i)) && (this.getArestas().get(i).isVisitado() == false)) {
                    this.getArestas().get(i).setVisitado(true);
                } else if (aresta != this.getArestas().get(i)) {

                    if (anterior.getNome().equals(this.getArestas().get(i).getOrigem().getNome())) {

                        if (aresta.getOrigem().getNome().equals(this.getArestas().get(i).getDestino().getNome())) {
                            this.limparArestaVisitada();
                            this.hasCycle = true;
                            return true;
                        } else {
                            anterior = this.getArestas().get(i).getDestino();
                            this.getArestas().get(i).setVisitado(true);
                        }

                    } else if (anterior.getNome().equals(this.getArestas().get(i).getDestino().getNome())) {

                        if (aresta.getOrigem().getNome().equals(this.getArestas().get(i).getOrigem().getNome())) {
                            this.limparArestaVisitada();
                            this.hasCycle = true;
                            return true;
                        } else {
                            anterior = this.getArestas().get(i).getOrigem();
                            this.getArestas().get(i).setVisitado(true);
                        }
                    }
                }
            }
        }
        this.limparArestaVisitada();
        this.hasCycle = false;
        return false;
    }

//----------------------DIJKSTRA-------------------------------------------
    public ArrayList<Vertice> encontrarMenorCaminhoDijkstra(Vertice v1, Vertice v2) {

        ArrayList<Vertice> menorCaminho = new ArrayList<Vertice>();

        // Variavel que recebe os vertices pertencentes ao menor caminho
        Vertice verticeCaminho;

        // Variavel que guarda o vertice que esta sendo visitado
        Vertice atual;

        // Variavel que marca o vizinho do vertice atualmente visitado
        Vertice vizinho;

        // Aresta que liga o atual ao seu vizinho;
        Aresta ligacao;

        // Lista dos vertices que ainda nao foram visitados
        ArrayList<Vertice> naoVisitados = new ArrayList<Vertice>();

        menorCaminho.add(v1);

        // Colocando a distancias iniciais 
        for (int i = 0; i < this.getVertices().size(); i++) {

            if (this.getVertices().get(i).getNome().equals(v1.getNome())) {
                this.getVertices().get(i).setDistancia(0);
            } else {
                this.getVertices().get(i).setDistancia(9999);
            }
            // Insere o vertice na lista de vertices nao visitados
            naoVisitados.add(this.getVertices().get(i));
        }

        Collections.sort(naoVisitados);

        // O algoritmo continua ate que todos os vertices sejam visitados
        while (!naoVisitados.isEmpty()) {

            atual = naoVisitados.get(0);

            for (int i = 0; i < atual.getVizinhos().size(); i++) {

                vizinho = atual.getVizinhos().get(i);

                if (!vizinho.isVisitado()) {

                    // Comparando a distância do vizinho com a possível
                    // distância
                    ligacao = this.acharAresta(atual, vizinho);
                    if (vizinho.getDistancia() > (atual.getDistancia() + ligacao.getPeso())) {
                        vizinho.setDistancia(atual.getDistancia()
                                + ligacao.getPeso());
                        vizinho.setPai(atual);

                        if (vizinho == v2) {
                            menorCaminho.clear();
                            verticeCaminho = vizinho;
                            menorCaminho.add(vizinho);
                            while (verticeCaminho.getPai() != null) {
                                menorCaminho.add(verticeCaminho.getPai());
                                verticeCaminho = verticeCaminho.getPai();

                            }

                            Collections.sort(menorCaminho);

                        }
                    }
                }

            }
            // Marca o vertice atual como visitado e o retira da lista de nao
            // visitados
            atual.setVisitado(true);
            naoVisitados.remove(atual);

            Collections.sort(naoVisitados);

        }
        this.limparVerticesPai();
        return menorCaminho;
    }

//------------------BUSCA-EM-PROFUNDIDADE----------------------------------
    public ArrayList<Aresta> buscaEmProfundidade(String raiz, String buscado) {

        ArrayList<Aresta> arvoreProfundidade = new ArrayList<Aresta>();

        if (this.buscaRecursiva(raiz, buscado)) {
            System.out.println("Vertice encontrado");
        } else {
            System.out.println("Vertice nao encontrado");
        }

        for (int i = 0; i < this.arestas.size(); i++) {
            if (this.arestas.get(i).isVisitado()) {
                arvoreProfundidade.add(this.arestas.get(i));
            }
        }

        return arvoreProfundidade;
    }

    public boolean buscaRecursiva(String raiz, String buscado) {

        int posRaiz = this.posicaoVertice(raiz);

        this.vertices.get(posRaiz).setVisitado(true);

        if (!raiz.equals(buscado)) {
            for (int i = 0; i < this.vertices.get(posRaiz).getVizinhos().size(); i++) {

                if (!this.vertices.get(posRaiz).getVizinhos().get(i).isVisitado()) {
                    //acha aresta entre eles e seta como visitada
                    this.acharAresta(this.vertices.get(posRaiz), this.vertices.get(posRaiz).getVizinhos().get(i)).setVisitado(true);
                    //continua busca recursivamente
                    if (this.buscaRecursiva(this.vertices.get(posRaiz).getVizinhos().get(i).getNome(), buscado)) {
                        return true;
                    }
                }
            }
        } else {
            return true;
        }
        return false;
    }

//------------------BUSCA-EM-LARGURA---------------------------------------
    public ArrayList<Aresta> buscaEmLargura(String raiz, String buscado) {

        ArrayList<Aresta> arvoreLargura = new ArrayList<Aresta>();
        for (Vertice v : this.vertices) {
            v.setCor("branco");
        }

        Vertice v = this.acharVertice(raiz);
        v.setCor("cinza");

        LinkedList<Vertice> queue = new LinkedList<Vertice>();
        queue.add(v);
        boolean achou = false;
        while (queue.size() > 0) {
            Vertice current = queue.remove();
            current.setCor("preto");
            if (current.getNome().equals(buscado)) {
                achou = true;
                break;
            }

            for (Vertice visinho : current.getVizinhos()) {
                if (visinho.getCor().equals("branco")) {
                    visinho.setCor("cinza");
                    queue.add(visinho);
                    arvoreLargura.add(this.acharAresta(current, visinho));
                }
            }
        }

        if (achou) {
            System.out.println("Vertice encontrado");
        } else {
            System.out.println("Vertice nao encontrado");
        }

        return arvoreLargura;
    }

//-------------------------------------------------------------------------
}
