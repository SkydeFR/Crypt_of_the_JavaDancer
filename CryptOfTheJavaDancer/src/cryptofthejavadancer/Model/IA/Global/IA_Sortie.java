/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.IA.Global;

import cryptofthejavadancer.Model.Carte.Cases.Case;
import static cryptofthejavadancer.Model.Carte.Cases.Type_Case.*;
import cryptofthejavadancer.Model.Carte.Graphes.Algorithmes.*;
import cryptofthejavadancer.Model.Carte.Graphes.Vertex;
import cryptofthejavadancer.Model.Entites.Entite;
import cryptofthejavadancer.Model.IA.Directions;
import cryptofthejavadancer.Model.IA.IA;
import cryptofthejavadancer.Model.IA.Type_Action;
import static cryptofthejavadancer.Model.IA.Directions.*;
import static cryptofthejavadancer.Model.IA.Type_Action.*;

/**
 * IA se dirigeant vers la sortie en empruntant le plus court chemin
 * @author jp032952
 */
public class IA_Sortie extends IA {
    
    private Astar shortestPath;
    private boolean end;

    public IA_Sortie(Entite _entite) {
        super(_entite);
        this.shortestPath = null;
        this.end = false;
   }

    @Override
    public Type_Action action() {
        if (!end) {
            if (shortestPath == null) {
                shortestPath = new Astar(getMap().getGraphe_simple());
            }

            if (shortestPath.getChemin().isEmpty()) {
                Vertex vEntite = getMap().getGraphe_simple().getVertex(getEntite().getCase());
                Vertex vEnd = getMap().getGraphe_simple().getVertex(getMap().getHashMapCases().get(getMap().getSortie()));
                shortestPath.calcul(vEntite, vEnd);
            }
            
            return vertexToAction(shortestPath.getChemin().get(0).getCase());
        } else {
            return attendre;
        }
    }
    
    public Type_Action vertexToAction(Case nextCase) {
        Type_Action actionRealise = null;
        
        //Determiner la direction
        int ligneA, colonneA;
        int ligneB, colonneB;
        
        ligneA = getEntite().getCase().getLigne();
        colonneA = getEntite().getCase().getColonne();
        ligneB = nextCase.getLigne();
        colonneB = nextCase.getColonne();
        
        Directions direction = AUCUNE;
        if (colonneB == colonneA+1) {
            direction = DROITE;
        } else if (colonneB == colonneA-1) {
            direction = GAUCHE;
        } else if (ligneB == ligneA+1) {
            direction = BAS;
        } else if (ligneB == ligneA-1) {
            direction = HAUT;
        }
        
        //Determiner le type
        switch(direction) {
            case DROITE:
                switch(nextCase.getType()) {
                    case Mur:
                        actionRealise = interagir_droite;
                        break;
                    case MurDur: //Cadence utilise la pelle
                        actionRealise = interagir_droite;                        
                        break;
                    case Sol:
                        if (nextCase.getEntite() != null) {
                            actionRealise = interagir_droite;
                        } else  {
                            actionRealise = deplacement_droite;
                        }
                        break;
                }
                break;
            case GAUCHE:
                switch(nextCase.getType()) {
                    case Mur:
                        actionRealise = interagir_gauche;
                        break;
                    case MurDur: //Cadence utilise la pelle
                        actionRealise = interagir_gauche;                        
                        break;
                    case Sol:
                        if (nextCase.getEntite() != null) {
                            actionRealise = interagir_gauche;
                        } else  {
                            actionRealise = deplacement_gauche;
                        }
                        break;
                }
                break;
            case BAS:
                switch(nextCase.getType()) {
                    case Mur:
                        actionRealise = interagir_bas;
                        break;
                    case MurDur: //Cadence utilise la pelle
                        actionRealise = interagir_bas;                        
                        break;
                    case Sol:
                        if (nextCase.getEntite() != null) {
                            actionRealise = interagir_bas;
                        } else  {
                            actionRealise = deplacement_bas;
                        }
                        break;
                }
                break;
            case HAUT:
                switch(nextCase.getType()) {
                    case Mur:
                        actionRealise = interagir_haut;
                        break;
                    case MurDur: //Cadence utilise la pelle
                        actionRealise = interagir_haut;                        
                        break;
                    case Sol:
                        if (nextCase.getEntite() != null) {
                            actionRealise = interagir_haut;
                        } else  {
                            actionRealise = deplacement_haut;
                        }
                        break;
                }
                break;
        }
        
        if (actionRealise == deplacement_droite || actionRealise == deplacement_gauche || actionRealise == deplacement_bas || actionRealise == deplacement_haut) {
            shortestPath.getChemin().remove(0);
            if (shortestPath.getChemin().isEmpty()) {
                end = true;
            }
        }
        
        return actionRealise;
    }
    
}
