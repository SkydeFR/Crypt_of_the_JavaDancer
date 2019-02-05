package cryptofthejavadancer.Model.Carte;

import cryptofthejavadancer.Model.Carte.Cases.*;
import cryptofthejavadancer.Model.Carte.Cases.Type_Case;
import static cryptofthejavadancer.Model.Carte.Cases.Type_Case.*;
import cryptofthejavadancer.Model.Carte.Graphes.Algorithmes.*;
import cryptofthejavadancer.Model.Carte.Graphes.Graph;
import cryptofthejavadancer.Model.Carte.Graphes.Vertex;
import cryptofthejavadancer.Model.Carte.Parseur.Fabrique_Cases;
import cryptofthejavadancer.Model.Carte.Parseur.Parseur;
import cryptofthejavadancer.Model.Entites.Entite;
import cryptofthejavadancer.Model.Entites.Entite_Cadence;
import cryptofthejavadancer.Model.Entites.Type_Entite;
import cryptofthejavadancer.Model.Objet.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Carte du jeu
 * @author Matthieu
 */
public class Map {

    private final HashMap<Coordonnees,Case> hashMapCases;                       //Stockage par coordonnées des cases
    private final ArrayList<Entite> listeEntite;                                //Liste des entités    
    private final ArrayList<Case> listeCase;                                    //Liste des cases
    private final ArrayList<Objet> listeObjet;                                  //Liste des objets
    private Coordonnees depart;                                                 //Position du point de départ
    private Coordonnees fin;                                                    //Position de la sortie
    
    private Entite_Cadence joueur;                                              //Cadence
    
    private Graph graphe_simple;
    private Graph graphe_ameliore;
    
//---------- CONSTRUCTEURS -----------------------------------------------------

    public Map() {
        //Initialisation
        this.hashMapCases = new HashMap<>();
        this.listeCase = new ArrayList<>();
        this.listeEntite = new ArrayList<>();
        this.listeObjet = new ArrayList<>();
        this.joueur = null;
        this.graphe_simple = new Graph();
        this.graphe_ameliore = new Graph();
    }

    public Graph getGraphe_simple() {
        return graphe_simple;
    }

    public Graph getGraphe_ameliore() {
        return graphe_ameliore;
    }
    
    private void genererGrapheSimple() {
        for(Case c : this.listeCase) {
            graphe_simple.addVertex(c); //Création des sommets
        }
              
        for(Case c1 : this.listeCase) {      
            for(Case c2 : this.listeCase) {
                /* Si les deux cases sont voisines */
                if (Math.abs(c1.getColonne()-c2.getColonne()) + Math.abs(c1.getLigne()-c2.getLigne()) == 1) {
                    switch (c2.getType()) {
                        case Mur:
                            graphe_simple.addEdge(c1,c2); //Création de l'arête
                            graphe_simple.setLabel(c1,c2,2); //Création du label
                            break;
                        case Sol:
                            graphe_simple.addEdge(c1,c2);
                            graphe_simple.setLabel(c1,c2,1);
                            break;
                     }
                }
            }
        }
    }
    
    private void genererGrapheAmeliore() {
        for(Case c : this.listeCase) {
            graphe_ameliore.addVertex(c); //Création des sommets
        }
              
        for(Case c1 : this.listeCase) {      
            for(Case c2 : this.listeCase) {
                /* Si les deux cases sont voisines */
                if (Math.abs(c1.getColonne()-c2.getColonne()) + Math.abs(c1.getLigne()-c2.getLigne()) == 1) {
                    switch (c2.getType()) {
                        case Mur:
                            graphe_ameliore.addEdge(c1,c2); //Création de l'arête
                            graphe_ameliore.setLabel(c1,c2,2); //Création du label
                            break;
                        case MurDur: //Cadence utilise la pelle
                            graphe_ameliore.addEdge(c1,c2);
                            graphe_ameliore.setLabel(c1,c2,2);
                            break;
                        case Sol:
                            graphe_ameliore.addEdge(c1,c2);
                            graphe_ameliore.setLabel(c1,c2,1);
                            break;
                     }
                }
            }
        }
    }
    
    
    //créer la map à partir d'un fichier texte
    
    public void chargerFichier(String adresseFichier) throws IOException {
        Parseur parseur = new Parseur(adresseFichier,this);
        parseur.lecture();
        System.out.println("Niveau chargé.");
        System.out.println("");
       
        /*
        //Il est possible de rajouter ICI des choses se réalisant juste après le chargement de la carte...
        System.out.println("Number of squares : "+listeCase.size());
        System.out.println("");
        
        System.out.println("Number of entites : "+listeEntite.size());
        for(int i=0; i<listeEntite.size(); i++) {
            System.out.println("--> "+listeEntite.get(i).getType());
        }
        System.out.println("");
        System.out.println("Location of Cadence : line "+this.joueur.getCase().getLigne()+", column "+this.joueur.getCase().getColonne());
        System.out.println("Type of the square at the left of Cadence : "+getCase(this.joueur.getCase().getLigne(),this.joueur.getCase().getColonne()-1).getType());
        System.out.println("");
        
        System.out.println("Number of items : "+listeObjet.size());
        int nbDiamonds = 0;
        for(int i=0; i<listeObjet.size(); i++) {
            if (listeObjet.get(i).getType()==Type_Objet.Sortie) {
                System.out.println("--> "+listeObjet.get(i).getType()+ " (line "+listeObjet.get(i).getCase().getLigne()+", column "+listeObjet.get(i).getCase().getColonne()+")");
            } else {
                System.out.println("--> "+listeObjet.get(i).getType()+ "(line "+listeObjet.get(i).getCase().getLigne()+", column "+listeObjet.get(i).getCase().getColonne());
                //System.out.println("--> "+listeObjet.get(i).getType());
            }
            
            if(listeObjet.get(i).getType()==Type_Objet.Diamant) {
                nbDiamonds++;
            }
        }
        System.out.println("(Number of diamonds : "+nbDiamonds+")");
        System.out.println("");
        */

        genererGrapheSimple(); //On génère le graphe correspondant à la carte
        genererGrapheAmeliore(); //On génère le graphe correspondant à la carte quand Cadence à la pelle
        
        /*
        System.out.println("Matrice d'adjacence :");
        for(Case c1 : this.listeCase) {
            for(Case c2 : this.listeCase) {
                if (graphe_simple.getLabel(c1,c2) != null) {
                    System.out.print(graphe_simple.getLabel(c1,c2));
                } else {
                    System.out.print(0);
                }
            }
            System.out.println("");
        }
        System.out.println("");
        */
    }
    
//------------------------------------------------------------------------------

//---------- GETEUR/SETEUR -----------------------------------------------------
    
    /*
    public double maxEuclidian_distance() {
        Case hautGauche = listeCase.get(0);
        Case basDroit = listeCase.get(listeCase.size()-1);
        return Math.sqrt((Math.pow((basDroit.getLigne()-hautGauche.getLigne()),2))+(Math.pow((basDroit.getColonne()-hautGauche.getColonne()),2)));
    }
    */
    
    //Renvoie la case présente à ses coordonnées
    public Case getCase(int ligne,int colonne) {
        return this.hashMapCases.get(new Coordonnees(ligne,colonne));
    }
    
    //Change la case à ses coordonnées
    public void setCase(int ligne, int colonne, Case _case) {
        this.hashMapCases.put(new Coordonnees(ligne,colonne), _case);
        this.listeCase.add(_case);
    }
    
    //Change le type de case à ses coordonnées (lors du minage)
    public void changeTypeCase(Case caseInitiale, Type_Case typeNouvelleCase) {
        Case nouvelleCase = Fabrique_Cases.construireCase(typeNouvelleCase, caseInitiale.getLigne(), caseInitiale.getColonne(), this);
        this.setCase(caseInitiale.getLigne(), caseInitiale.getColonne(), nouvelleCase);
        
        graphe_simple.editVertex(caseInitiale, nouvelleCase);
        graphe_ameliore.editVertex(caseInitiale, nouvelleCase);
        
        this.listeCase.remove(caseInitiale);
    }
    
    //Ajoute une entité à la position donnée
    public void ajouteEntite(int ligne,int colonne, Entite entite) {
        this.getCase(ligne, colonne).setEntite(entite);
        entite.setCase(this.getCase(ligne, colonne));
        if(entite.getType() != Type_Entite.Cadence) {
            this.listeEntite.add(entite);
        }
    }
    
    //Fixe le joueur
    public void setJoueur(Entite_Cadence cadence) {
        this.joueur = cadence;
    }
    
    //Ajoute un objet à la position donnée
    public void ajouteObjet(int ligne,int colonne, Objet objet) {
        this.getCase(ligne, colonne).setObjet(objet);
        objet.setCase(this.getCase(ligne, colonne));
        this.listeObjet.add(objet);
    }
    
    //Renvoie la hashmap des cases
    public HashMap<Coordonnees,Case> getHashMapCases() {
        return this.hashMapCases;
    }
    
    //Fixe le point de départ
    public void setDepart(int numLigne,int numColonne) {
        this.depart = new Coordonnees(numLigne,numColonne);
    }
    
    //Fixe la position de la sortie
    public void setSortie(int numLigne,int numColonne) {
        this.fin = new Coordonnees(numLigne,numColonne);
    }
    
    //Renvoie la position du point de départ
    public Coordonnees getDepart() {
        return this.depart;
    }
    
    //Renvoie la position du point de sortie
    public Coordonnees getSortie() {
        return this.fin;
    }
    
    //Renvoie la liste des entités
    public ArrayList<Entite> getListeEntite() {
        return this.listeEntite;
    }
    
    //Renvoie la liste des cases
    public ArrayList<Case> getListeCase() {
        return this.listeCase;
    }
    
    //Renvoie la liste des objets
    public ArrayList<Objet> getListeObjet() {
        return this.listeObjet;
    }
    
    //Renvoie le joueur
    public Entite_Cadence getJoueur() {
        return this.joueur;
    }
    
//------------------------------------------------------------------------------

}
