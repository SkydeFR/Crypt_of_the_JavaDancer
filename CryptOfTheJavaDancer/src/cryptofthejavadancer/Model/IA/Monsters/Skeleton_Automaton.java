/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.IA.Monsters;

import cryptofthejavadancer.Model.Carte.Cases.Case;
import static cryptofthejavadancer.Model.Carte.Cases.Type_Case.Sol;
import cryptofthejavadancer.Model.Entites.Entite;
import cryptofthejavadancer.Model.IA.Directions;
import static cryptofthejavadancer.Model.IA.Directions.*;
import cryptofthejavadancer.Model.IA.IA;
import cryptofthejavadancer.Model.IA.Type_Action;
import static cryptofthejavadancer.Model.IA.Type_Action.*;

/**
 * Automate pour les slimes squelettes
 * @author jp032952
 */
public class Skeleton_Automaton extends IA {
    
    private Case memoire;
    private Directions direction;
    private Case nextCase;
    
    public Skeleton_Automaton(Entite _entite) {
        super(_entite);
        this.memoire = null;
        this.nextCase = null;
    }
    
    @Override
    public Type_Action action() {
        if (memoire == null || memoire == getEntite().getCase()) {
            direction = randomDirection();
        }
        memoire = getEntite().getCase();
        
        //On défini la prochaine case
        switch(direction) {
            case DROITE:
                nextCase = getMap().getCase(getEntite().getCase().getLigne()+1,getEntite().getCase().getColonne());
                break;
            case GAUCHE:
                nextCase = getMap().getCase(getEntite().getCase().getLigne()-1,getEntite().getCase().getColonne());
                break;
            case HAUT:
                nextCase = getMap().getCase(getEntite().getCase().getLigne(),getEntite().getCase().getColonne()-1);
                break;
            case BAS:
                nextCase = getMap().getCase(getEntite().getCase().getLigne(),getEntite().getCase().getColonne()+1);
                break;
            default:
                nextCase = getEntite().getCase();
                break;
        }
        
        //On regarde si on doit attaquer Cadence
        if (nextCase == getMap().getJoueur().getCase()) {
            switch(direction) {
                case DROITE:
                    return interagir_droite;
                case GAUCHE:
                    return interagir_gauche;
                case HAUT:
                    return interagir_haut;
                case BAS:
                    return interagir_bas;
                default:
                    return null;
            }
        }
        
        //On regarde si la prochaine case n'est pas libre
        if (nextCase.getType() != Sol || nextCase.getEntite() != null) {
            //on change de direction
        }
        
        //On se déplace ou on attend
        switch (direction) {
            case DROITE:
                return deplacement_droite;
            case GAUCHE:
                return deplacement_gauche;
            case HAUT:
                return deplacement_haut;
            case BAS:
                return deplacement_bas;
            default:
                return null;
        }
    }
    
    public Directions randomDirection() {
        int random = (int)(1+Math.random()*4);
        switch (random) {
            case 1:
                return DROITE;
            case 2:
                return GAUCHE;
            case 3:
                return HAUT;
            case 4:
                return BAS;
            default:
                return null;
        }
    }
    
}
