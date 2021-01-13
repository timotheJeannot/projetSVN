import fr.ufc.m2info.svam.Article;
import fr.ufc.m2info.svam.ArticleDB;
import fr.ufc.m2info.svam.Caisse;
import fr.ufc.m2info.svam.Scanette;
import junit.framework.Assert;

import java.util.*;

public class Automate {

    /** Variable representing the current state */
    private int prop1=0;
    private int prop2=0;
    private int prop3=0;
    private int prop4=0;
    private int prop5=0;
    private int prop6=0;


    public Automate() {
        prop1=0;
        prop2=0;
        prop3=0;
        prop4=0;
        prop5=0;
        prop6=0;
    }


    public void debloquer() {
        if(prop1 == 0){
            prop1 = 2;
        }
        if(prop2 == 0){
            prop2 = 2;
        }
        if(prop6 == 0){
            prop6 = 1;
        }

    }

    public void scanOK(){
        switch (prop1){
            case 2 : prop1 = 3;
                break;
            case 0 : prop1 = 1;
                System.err.println("Error PROP 1 ");;
                break;
        }
        if(prop3 == 1){
            prop3 = 2;
        }

        if(prop4 == 0){
            prop4 = 1;
        }
    }

    public void supprimerOK(int panier) {

        if(prop4 == 1){
            prop4 =0;
        }

        switch (prop2){
            case 2 : prop2 = 3;
            break;
            case 0 : prop2 = 1;
                System.err.println("Error PROP 2" );
                break;
        }
        if (panier == 0 && prop3 == 1) {
            prop3 = 3;
            System.err.println("ERROR Prop3");
        }else{
            prop3 = 1;
        }
    }

    public void transmission(int panier, int state) {
                if(panier > 1 && prop5 == 0){
                    prop5 = 2;
                }
                else if(prop5 == 2){
                    prop5 = 3;
                }
                else{
                    prop5 = 1;
                    System.err.println("ERROR PROP 5  " + state);
                }
        }

        public void payer(){

        }
    }

