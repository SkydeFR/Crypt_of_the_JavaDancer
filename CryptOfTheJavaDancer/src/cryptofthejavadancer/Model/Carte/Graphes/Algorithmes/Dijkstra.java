/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.Carte.Graphes.Algorithmes;

import cryptofthejavadancer.Model.Carte.Graphes.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Dijkstra
 * @author jp032952
 */
public class Dijkstra {
    
    protected Graph graph;
    protected Vertex debut;
    protected Vertex fin;
    protected HashMap<Vertex, Integer> distance;
    protected HashMap<Vertex, Boolean> visite;
    protected HashMap<Vertex, Vertex> predecesseur;
    protected List<Vertex> chemin;
    protected int infinity;
    
    public Dijkstra(Graph _graph) {
        this.graph = _graph;
        this.debut = null;
        this.fin = null;
        this.distance = new HashMap<>();
        this.visite = new HashMap<>();
        this.predecesseur = new HashMap<>();
        this.chemin = new ArrayList<>();
        this.infinity = -1;
    }
    
    protected void init() {
        this.distance = new HashMap<>();
        this.visite = new HashMap<>();
        this.predecesseur = new HashMap<>();
        this.chemin = new ArrayList<>();
        this.infinity = getInfinity();
        
        for (Vertex v : graph.getVertexCollection()) {
            distance.put(v, infinity);
            visite.put(v, false);
            predecesseur.put(v, null);
        }
        
        distance.put(debut, 0);
    }
    
    protected Vertex plusProcheSommet() {
        Vertex myVertex = null;
        int distanceMin = getInfinity()+1;
        
        for (Vertex i : graph.getVertexCollection()) {
            if (!visite.get(i) && distance.get(i) < distanceMin) {
                myVertex = i;
                distanceMin = distance.get(i);
            }
        }
        
        return myVertex;
    }
    
    public void calcul(Vertex _debut, Vertex _fin) {
        this.debut = _debut;
        this.fin = _fin;
        
        init();
        
        while(plusProcheSommet() != null) {
            Vertex a = plusProcheSommet();
            visite.put(a, true);
            for (Vertex b : graph.getVertexCollection()) {
                relax(a, b);
            }
        }
        
        Vertex tmp = fin;
        while (tmp != null) {
            chemin.add(tmp);
            tmp = predecesseur.get(tmp);
        }
        Collections.reverse(chemin);
        if (!chemin.isEmpty()) {
            chemin.remove(0);
        }
    }
    
    protected void relax(Vertex a, Vertex b) {
        Integer poids = graph.getLabel(a, b);
        
        if (poids == null) {
            poids = getInfinity();
        }
        
        if (distance.get(b) > distance.get(a) + poids) {
            distance.put(b, distance.get(a) + poids);
            predecesseur.put(b, a);
        }
    }
    
    public List<Vertex> getChemin() {
        return chemin;
    }
    
    public int getInfinity() {
        if (infinity == -1) {
            infinity = 0;
            for (Integer i : graph.getLabels().values()) {
                infinity += i;
            }
            infinity++;
        }
        return infinity;
    }
    
    public HashMap<Vertex, Integer> getDistance() {
        return distance;
    }
    
}
