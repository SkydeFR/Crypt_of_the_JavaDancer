package cryptofthejavadancer.Model.IA.Global;

import cryptofthejavadancer.Model.Entites.Entite;
import cryptofthejavadancer.Model.IA.IA;
import cryptofthejavadancer.Model.IA.Type_Action;

/**
 * Une IA relativement simple...
 * @author Matthieu
 */
public class IA_Immobile extends IA {

//---------- CONSTRUCTEURS -----------------------------------------------------

    public IA_Immobile(Entite _entite) {
        super(_entite);
    }

//------------------------------------------------------------------------------

    @Override
    public Type_Action action() {
        return Type_Action.attendre; //To change body of generated methods, choose Tools | Templates.
    }
}
