/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.IA.Monsters;

import cryptofthejavadancer.Model.Entites.Entite;
import cryptofthejavadancer.Model.IA.IA;
import cryptofthejavadancer.Model.IA.Type_Action;
import static cryptofthejavadancer.Model.IA.Type_Action.*;

/**
 * Automate pour les chauve-souris
 * @author jp032952
 */
public class Bat_Automaton extends IA {
    
    public Bat_Automaton(Entite _entite) {
        super(_entite);
    }
    
    @Override
    public Type_Action action() {
        return attendre;
    }
    
}
