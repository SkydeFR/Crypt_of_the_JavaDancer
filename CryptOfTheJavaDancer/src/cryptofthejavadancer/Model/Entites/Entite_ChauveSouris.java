package cryptofthejavadancer.Model.Entites;

import cryptofthejavadancer.Model.IA.Global.IA_Immobile;
import cryptofthejavadancer.Model.IA.Monsters.Bat_Automaton;

/**
 * Chauve Souris
 * @author Matthieu
 */
public class Entite_ChauveSouris extends Entite_Monstre {

//---------- CONSTRUCTEURS -----------------------------------------------------

    public Entite_ChauveSouris() {
        super(1);
        this.setIA(new Bat_Automaton(this));
    }
    
//------------------------------------------------------------------------------

//---------- GETEUR/SETEUR -----------------------------------------------------

    @Override
    public Type_Entite getType() {
        return Type_Entite.ChauveSouris;
    }
    
//------------------------------------------------------------------------------

    @Override
    public int getGainOr() {
        return 5;
    }


}
