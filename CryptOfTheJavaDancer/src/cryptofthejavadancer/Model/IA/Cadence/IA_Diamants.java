/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.IA.Cadence;

import cryptofthejavadancer.Model.Carte.Cases.Case;
import cryptofthejavadancer.Model.Carte.Graphes.Algorithmes.Dijkstra;
import cryptofthejavadancer.Model.Carte.Graphes.Vertex;
import cryptofthejavadancer.Model.Entites.Entite;
import cryptofthejavadancer.Model.IA.Directions;
import cryptofthejavadancer.Model.IA.IA;
import cryptofthejavadancer.Model.IA.Type_Action;
import static cryptofthejavadancer.Model.IA.Directions.*;
import static cryptofthejavadancer.Model.IA.Type_Action.*;
import cryptofthejavadancer.Model.Objet.Objet;
import cryptofthejavadancer.Model.Objet.Type_Objet;
import static cryptofthejavadancer.Model.Objet.Type_Objet.*;
import java.util.ArrayList;
import java.util.List;

/**
 * IA qui récupère tous les diamants accessibles puis se dirige vers la sortie en empruntant le plus court chemin
 * @author jp032952
 */
public class IA_Diamants extends IA {
    
    private Dijkstra algo;
    private Dijkstra algo_ameliore;
    private final ArrayList<Objet> listeDiamants;
    private ArrayList<Vertex> verticesDiamonds, verticesDiamonds_ameliore;
    private List<Vertex> chemin;
    private boolean haveShovel;
    private boolean end;
    
    public IA_Diamants(Entite _entite) {
        super(_entite);
        this.algo = null;
        this.algo_ameliore = null;
        this.listeDiamants = new ArrayList();
        this.verticesDiamonds = new ArrayList();
        this.verticesDiamonds_ameliore = new ArrayList();
        this.chemin = new ArrayList();
        this.haveShovel = false;
        this.end = false;
   }
    
    private Vertex plusProcheDiamant() {
        Vertex vDiamant = null;
        int distanceMin = algo.getInfinity()+1;
        
        //On retrouve le sommet de la pelle
        ArrayList<Objet> listeObjet = getMap().getListeObjet();
        Vertex vShovel_simple = null;
        Vertex vShovel_ameliore = null;
        for (Objet o : listeObjet) {
            if (o.getType() == Type_Objet.Pelle) {
                vShovel_simple = getMap().getGraphe_simple().getVertex(o.getCase());
                vShovel_ameliore = getMap().getGraphe_ameliore().getVertex(o.getCase());
            }
        }
        
        if(haveShovel) {
            /* GRAPHE AMELIORE */
            //Remplis la liste de vertex ayant un diamant
            verticesDiamonds_ameliore = new ArrayList<>();
            for (Objet o : listeDiamants) {
                Vertex v = getMap().getGraphe_ameliore().getVertex(o.getCase());
                verticesDiamonds_ameliore.add(v);
            }
            System.out.println("");
            //Trouve le plus proche diamant
            for (Vertex i : verticesDiamonds_ameliore) {
                if (algo_ameliore.getDistance().get(i)<distanceMin) {
                    distanceMin = algo_ameliore.getDistance().get(i);
                    vDiamant = i;
                }
            }
        } else {
            /* GRAPHE SIMPLE */
            //Remplis la liste de vertex ayant un diamant
            verticesDiamonds = new ArrayList();
            for (Objet o : listeDiamants) {
                Vertex v = getMap().getGraphe_simple().getVertex(o.getCase());
                verticesDiamonds.add(v);
            }

            //Trouve le plus proche diamant
            for (Vertex i : verticesDiamonds) {
                if (algo.getDistance().get(i)<distanceMin) {
                    distanceMin = algo.getDistance().get(i);
                    vDiamant = i;
                }
            }
            
            if (vShovel_simple != null && vShovel_ameliore != null) {
                /* GRAPHE AMELIORE */
                //Remplis la liste de vertex ayant un diamant
                verticesDiamonds_ameliore = new ArrayList();
                for (Objet o : listeDiamants) {
                    Vertex v = getMap().getGraphe_ameliore().getVertex(o.getCase());
                    verticesDiamonds_ameliore.add(v);
                }

                int distanceTmp = algo.getDistance().get(vShovel_simple);
                Vertex vEnd = getMap().getGraphe_ameliore().getVertex(getMap().getHashMapCases().get(getMap().getSortie()));
                algo_ameliore.calcul(vShovel_ameliore, vEnd);

                //Il faut prendre la pelle
                for (Vertex i : verticesDiamonds_ameliore) {
                    if (distanceTmp+algo_ameliore.getDistance().get(i)<distanceMin) {
                        distanceMin = algo_ameliore.getDistance().get(i);
                        vDiamant = vShovel_simple;
                    }
                }
            }
        }
        
        if (distanceMin == algo.getInfinity()) {
            return null;
        } else {
            return vDiamant;
        }
    }

    @Override
    public Type_Action action() {
        if (!end) {
            System.out.println("Pos actuelle : "+getEntite().getCase().getLigne()+","+getEntite().getCase().getColonne());
            //S'il y a un objet sur la case on prend une décision
            if (getEntite().getCase().getObjet() != null) {
                switch (getEntite().getCase().getObjet().getType()) {
                    case Sortie:
                        if (plusProcheDiamant() == null) {
                            end = true;
                            System.out.println("Vous avez trouvé la sortie !");
                            return sortir;
                        }
                        break;
                    case Diamant:
                        listeDiamants.remove(getEntite().getCase().getObjet());
                        System.out.println("Direction : AUCUNE");
                        System.out.println("Action : ramasser diamant");
                        System.out.println("---");
                        return ramasser;
                    case Pelle:
                        System.out.println("Direction : AUCUNE");
                        System.out.println("Action : ramasser pelle");
                        System.out.println("---");
                        haveShovel = true;
                        return ramasser;
                }
            }
                        
            //On intialise nos algorithmes et la liste de diamants
            if (algo == null && algo_ameliore == null) {
                /* GRAPHE AMELIORE */
                //Calcul les distances avec Dijkstra
                algo_ameliore = new Dijkstra(getMap().getGraphe_ameliore());
                Vertex vEntite = getMap().getGraphe_ameliore().getVertex(getEntite().getCase());
                Vertex vEnd = getMap().getGraphe_ameliore().getVertex(getMap().getHashMapCases().get(getMap().getSortie()));
                algo_ameliore.calcul(vEntite, vEnd);
                
                /* LISTE DE DIAMANTS */
                //Remplis la liste d'objets de type diamant
                ArrayList<Objet> listeObjet = getMap().getListeObjet();
                for (Objet o : listeObjet) {
                    if (o.getType() == Type_Objet.Diamant) {
                        listeDiamants.add(o);
                    }
                }
                
                /* GRAPHE SIMPLE */
                //Calcul les distances avec Dijkstra
                algo = new Dijkstra(getMap().getGraphe_simple());
                vEntite = getMap().getGraphe_simple().getVertex(getEntite().getCase());
                vEnd = getMap().getGraphe_simple().getVertex(getMap().getHashMapCases().get(getMap().getSortie()));
                algo.calcul(vEntite, vEnd);
            }
            
            //Si le chemin est vide on génère un nouveau chemin
            if (chemin.isEmpty()) {
                    /**pas optimisé à changer d'endroit ! (résous le bug spécial lorsque deux diamants sont très proches)**/
                    Vertex vEntite = getMap().getGraphe_simple().getVertex(getEntite().getCase());
                    algo.calcul(vEntite, plusProcheDiamant());
                    vEntite = getMap().getGraphe_ameliore().getVertex(getEntite().getCase());
                    algo_ameliore.calcul(vEntite, plusProcheDiamant());
                    /**pas optimisé jusqu'à ici !**/
                    
                    Vertex plusProcheDiamant = plusProcheDiamant();
                    if (!haveShovel) {
                         vEntite = getMap().getGraphe_simple().getVertex(getEntite().getCase());
                        //S'il y a un plus proche diamant
                        if (plusProcheDiamant != null) {
                            algo.calcul(vEntite, plusProcheDiamant);
                            chemin = algo.getChemin();
                        } else {
                            goSortie();
                        }
                    } else {
                         vEntite = getMap().getGraphe_ameliore().getVertex(getEntite().getCase());
                        //S'il y a un plus proche diamant
                        if (plusProcheDiamant != null) {
                            algo_ameliore.calcul(vEntite, plusProcheDiamant);
                            chemin = algo_ameliore.getChemin();  
                        } else {
                            goSortie();
                        }                      
                    }
                    //---
                    afficherChemin();
            }
            
            //On effectue une action
            return vertexToAction(chemin.get(0).getCase());
        } else {
            //Si on a trouvé la sortie on ne bouge plus
            return attendre;
        }
    }
    
    void goSortie() {
        if (!haveShovel) {
            Vertex vEntite = getMap().getGraphe_simple().getVertex(getEntite().getCase());
            Vertex vEnd = getMap().getGraphe_simple().getVertex(getMap().getHashMapCases().get(getMap().getSortie()));
                //On va vers la sortie
                algo.calcul(vEntite, vEnd);
                chemin = algo.getChemin();
        } else {
            Vertex vEntite = getMap().getGraphe_ameliore().getVertex(getEntite().getCase());
            Vertex vEnd = getMap().getGraphe_ameliore().getVertex(getMap().getHashMapCases().get(getMap().getSortie()));
                //On va vers la sortie
                algo_ameliore.calcul(vEntite, vEnd);
                chemin = algo_ameliore.getChemin();                        
        }
    }
    
    void afficherChemin() {
        if (!chemin.isEmpty()) {
            //On affiche le chemin
            System.out.println("Nouveau chemin généré :");
            for (Vertex i : chemin) {
            System.out.println("> "+i.getCase().getLigne()+","+i.getCase().getColonne());
            }
            System.out.println("---");
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
        
        System.out.println("Direction : "+direction);
        System.out.println("Action : "+actionRealise);
        System.out.println("---");
        
        if (actionRealise == deplacement_droite || actionRealise == deplacement_gauche || actionRealise == deplacement_bas || actionRealise == deplacement_haut) {
            if (!chemin.isEmpty()) {
                chemin.remove(0);
            }
        }
        
        return actionRealise;
    }
    
}
