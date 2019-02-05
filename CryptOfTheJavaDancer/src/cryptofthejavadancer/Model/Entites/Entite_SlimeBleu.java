package cryptofthejavadancer.Model.Entites;

import cryptofthejavadancer.Model.IA.Global.IA_Immobile;
import cryptofthejavadancer.Model.IA.Monsters.BlueSlime_Automaton;

/**
 * Slime Bleu
 * @author Matthieu
 */
public class Entite_SlimeBleu extends Entite_Monstre {

//---------- CONSTRUCTEURS -----------------------------------------------------

    public Entite_SlimeBleu() {
        super(2);
        this.setIA(new BlueSlime_Automaton(this));
    }
    
//------------------------------------------------------------------------------

//---------- GETEUR/SETEUR -----------------------------------------------------

    @Override
    public Type_Entite getType() {
        return Type_Entite.SlimeBleu;
    }
    
//------------------------------------------------------------------------------

    @Override
    public int getGainOr() {
        return 7;
    }


}
