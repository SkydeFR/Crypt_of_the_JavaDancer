/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.Carte.Graphes;

import cryptofthejavadancer.Model.Carte.Cases.Case;
import java.util.ArrayList;

/**
 *
 * @author jp032952
 */
public class Vertex {
    private ArrayList<Vertex> neighbours;
    private Graph graph;
    private Case myCase;
    
    public Vertex(Graph _graph, Case _myCase) {
        neighbours = new ArrayList();
        graph = _graph;
        myCase = _myCase;
    }
    
    public void addNeighbour(Vertex vertex) {
        neighbours.add(vertex);
    }
    
    public ArrayList<Vertex> getNeighbours() {
        return neighbours;
    }
    
    public Case getCase() {
        return myCase;
    }
    
    public void setCase(Case newCase) {
        myCase = newCase;
    }
    
}
