/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.Carte.Graphes;

import cryptofthejavadancer.Model.Carte.Cases.Case;
import java.util.Collection;
import java.util.HashMap;

/**
 *
 * @author jp032952
 */
public class Graph {
    private HashMap<Case, Vertex> vertices;
    private HashMap<VertexCouple, Integer> labels;
    
    public Graph() {
        vertices = new HashMap();
        labels = new HashMap();
    }
    
    public void addVertex(Case name) {
        vertices.put(name, new Vertex(this, name));
    }
    
    public void addEdge(Case name1, Case name2) {
        vertices.get(name1).addNeighbour(vertices.get(name2));
    }
    
    public Vertex getVertex(Case name) {
        return vertices.get(name);
    }
    
    public Integer getLabel(Case name1, Case name2) {
        return labels.get(new VertexCouple(vertices.get(name1), vertices.get(name2)));
    }
    
    public Integer getLabel(Vertex v1, Vertex v2) {
        return labels.get(new VertexCouple(v1, v2));
    }

    public HashMap<VertexCouple, Integer> getLabels() {
        return labels;
    }
    
    public void setLabel(Case name1, Case name2, int label) {
        labels.put(new VertexCouple(vertices.get(name1), vertices.get(name2)), label);
    }
    
    public void setLabel(Vertex v1, Vertex v2, int label) {
        labels.put(new VertexCouple(v1, v2), label);
    }
    
    public Collection<Vertex> getVertexCollection() {
        return vertices.values();
    }
    
    public int nbVertices() {
        return vertices.size();
    }
    
    public void editVertex(Case oldCase, Case newCase) {
        Vertex oldVertex = getVertex(oldCase);
        vertices.put(newCase,oldVertex);
        vertices.remove(oldCase);
        oldVertex.setCase(newCase);
        
        for (Vertex v : getVertexCollection()) {
            if (getLabel(v,oldVertex) != null) {
                setLabel(v, oldVertex, 1);
            }
        }
    }
}
