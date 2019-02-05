/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.IA.Cadence;

import cryptofthejavadancer.Model.Entites.Entite;
import cryptofthejavadancer.Model.IA.IA;
import cryptofthejavadancer.Model.IA.Type_Action;
import static cryptofthejavadancer.Model.IA.Type_Action.*;

/**
 * IA permettant de tester manuellement différentes actions
 * @author jp032952
 */
public class IA_Test extends IA {
    
    private int tour = 0;
    private Type_Action actionRealise;
    
    public IA_Test(Entite _entite) {
        super(_entite);
    }

    @Override
    public Type_Action action() {
        tour++;

        switch (tour)
        {
          case 1:
          case 2:
            actionRealise = deplacement_droite;
            break;
          case 3:
            actionRealise = deplacement_bas;
            break;
          case 4:
            actionRealise = ramasser;
            break;
          case 5:
          case 6:
          case 7:
            actionRealise = deplacement_bas;
            break;
          case 8:
          case 9:
          case 10:
            actionRealise = deplacement_gauche;
            break;
          case 11:
          case 12:
          case 13:
            actionRealise = deplacement_bas;
            break;
          case 14:
          case 15:
          case 16:
            actionRealise = deplacement_gauche;
            break;
          default:
            actionRealise = attendre;      
        }
        
        return actionRealise;
    }
    
}
