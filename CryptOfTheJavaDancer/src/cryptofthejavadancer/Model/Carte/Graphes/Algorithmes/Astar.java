/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.Carte.Graphes.Algorithmes;

import cryptofthejavadancer.Model.Carte.Graphes.*;
import java.util.Collections;

/**
 * Astar
 * @author jp032952
 */
public class Astar extends Dijkstra {
    
    private final int maxDistance;
    
    public Astar(Graph _graph) {
        super(_graph);
        this.maxDistance = _graph.nbVertices();
    }
    
    private double heuristic(Vertex v) {
        return distance.get(v) + Math.sqrt(Math.pow(fin.getCase().getColonne()-v.getCase().getColonne(), 2)+Math.pow(fin.getCase().getLigne()-v.getCase().getLigne(), 2));
    }
    
    @Override
    protected Vertex plusProcheSommet() {
        Vertex myVertex = null;
        double distanceH = getInfinity()+1 + maxDistance;
        
        for (Vertex i : graph.getVertexCollection()) {
            if (!visite.get(i) && heuristic(i) < distanceH) {
                myVertex = i;
                distanceH = heuristic(i);
            }
        }
        
        return myVertex;
    }
    
    @Override
    public void calcul(Vertex _debut, Vertex _fin) {
        this.debut = _debut;
        this.fin = _fin;
        
        init();
        
        while(!visite.get(fin)) {
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
        
        /*
        System.out.println("[Astar] Shortest path :");
        for (Vertex i : getChemin()) {
            System.out.println(i.getCase().getLigne()+","+i.getCase().getColonne());
        }
        System.out.println("---");
        */
    }
    
}
