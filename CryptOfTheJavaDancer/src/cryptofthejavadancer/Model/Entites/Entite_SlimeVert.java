package cryptofthejavadancer.Model.Entites;

import cryptofthejavadancer.Model.IA.Global.IA_Immobile;
import cryptofthejavadancer.Model.IA.Monsters.GreenSlime_Automaton;

/**
 * Slime Vert
 * @author Matthieu
 */
public class Entite_SlimeVert extends Entite_Monstre {

//---------- CONSTRUCTEURS -----------------------------------------------------

    public Entite_SlimeVert() {
        super(1);
        this.setIA(new GreenSlime_Automaton(this));
    }
    
//------------------------------------------------------------------------------

//---------- GETEUR/SETEUR -----------------------------------------------------

    @Override
    public Type_Entite getType() {
        return Type_Entite.SlimeVert;
    }
    
//------------------------------------------------------------------------------

    @Override
    public int getGainOr() {
        return 5;
    }


}
