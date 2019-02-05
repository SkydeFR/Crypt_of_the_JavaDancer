/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.IA.Monsters.BlueSlime;

import cryptofthejavadancer.Model.Carte.Cases.Case;
import static cryptofthejavadancer.Model.Carte.Cases.Type_Case.*;
import cryptofthejavadancer.Model.Entites.Entite;
import cryptofthejavadancer.Model.IA.IA;
import cryptofthejavadancer.Model.IA.Type_Action;
import static cryptofthejavadancer.Model.IA.Type_Action.*;

/**
 * Automate pour les slimes bleus
 * @author jp032952
 */
public class BlueSlime_Automaton extends IA {
    
    private int memoire;
    private Case nextCase;
    
    public BlueSlime_Automaton(Entite _entite) {
        super(_entite);
        this.memoire = 0;
        this.nextCase = null;
    }
    
    @Override
    public Type_Action action() {
        memoire++;
        
        //On défini la prochaine case
        switch(memoire) {
            case 1:
                nextCase = getMap().getCase(getEntite().getCase().getLigne()+1,getEntite().getCase().getColonne());
                break;
            case 3:
                nextCase = getMap().getCase(getEntite().getCase().getLigne()-1,getEntite().getCase().getColonne());
                break;
            default:
                nextCase = getEntite().getCase();
                break;
        }
        
        //On regarde si on doit attaquer Cadence
        if (nextCase == getMap().getJoueur().getCase()) {
            switch(memoire) {
                case 1:
                    return interagir_bas;
                case 3:
                    return interagir_haut;
                default:
                    return null;
            }
        }
        
        //On regarde si la prochaine case est libre
        if (nextCase.getType() != Sol || nextCase.getEntite() != null) {
            memoire++;
            if (memoire > 4) {
                memoire = 1;
            }
        }
        
        //On se déplace ou on attend
        switch(memoire) {
            case 1:
                return deplacement_bas;
            case 2:
                return attendre;
            case 3:
                return deplacement_haut;
            case 4:
                memoire = 0;
                return attendre;
            default:
                return null;
        }
    }
    
}
