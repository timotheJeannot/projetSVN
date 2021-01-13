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
    private Map<String, Boolean> map1;
    private Map<String, Boolean> map2;
    private Map<String, Boolean> map3;
    private Map<String, Boolean> map4;
    private Map<String, Boolean> map5;
    private Map<String, Boolean> map6;




    public Automate() {
        prop1=0;
        prop2=0;
        prop3=0;
        prop4=0;
        prop5=0;
        prop6=0;
        map1 = new HashMap<String, Boolean>();
        map1.put("E0 UnLocking_OK E1", false);
        map1.put("E2 UnLocking_KO E3", false);
        map1.put("E0 Scan_OK E2", false);
        map2 = new HashMap<String, Boolean>();
        map2.put("E0 UnLocking_OK E1", false);
        map2.put("E2 UnLocking_KO E3", false);
        map2.put("E0 Remove_OK E2", false);
        map3 = new HashMap<String, Boolean>();;
        map3.put("E0 [Panier=1]Remove_OK E1", false);
        map3.put("E1 Scan_OK E0", false);
        map3.put("E1 Remove_KO E3", false);
        map4 = new HashMap<String, Boolean>();;
        map4.put("E1 Remove_OK E0", false);
        map4.put("E0 Scan_OK E1", false);
        map5 = new HashMap<String, Boolean>();;
        map5.put("E0 [Panier<1]? E1", false);
        map5.put("E0 Controle_KO[Transmission:1] E2", false);
        map5.put("E2 [Panier>=1]? E3", false);
        map6 = new HashMap<String, Boolean>();;
        map6.put("E0 Unlocking_OK E1", false);
        map6.put("E1 Controle_OK[Transmission:1] E3 ", false);
        map6.put("E1 Payment E2 ", false);
        map6.put("E3 Controle_KO[Transmission:1] E4", false);
        map6.put("E3 Payment E2", false);
    }


    public void debloquer() {
        switch (prop1){
            case 0 : prop1 = 1;
                map1.put("E0 UnLocking_OK E1", true);
                break;
            case 2 : prop1 = 3;
                map1.put("E2 UnLocking_KO E3", true);
                System.err.println("Error PROP 1 ");;
                break;
        }
        switch (prop2){
            case 0 : prop2 = 1;
                map2.put("E0 UnLocking_OK E1", true);
                break;
            case 2 : prop2 = 3;
                map2.put("E2 UnLocking_KO E3", true);
                System.err.println("Error PROP 2 ");;
                break;
        }
        if(prop6 == 0){
            map6.put("E0 Unlocking_OK E1", true);
            prop6 = 1;
        }

    }

    public void reset(){
        prop1=0;
        prop2=0;
        prop3=0;
        prop4=0;
        prop5=0;
        prop6=0;
    }

    public void scanOK(){
        if (prop1 == 0){
            prop1 = 2;
            map1.put("E0 Scan_OK E2", true);
        }

        if(prop3 == 1){
            map3.put("E1 Scan_OK E0", true);
            prop3 = 0;
        }

        if(prop4 == 0){
            map4.put("E0 Scan_OK E1", true);
            prop4 = 1;
        }
    }

    public void supprimerOK(int panier) {

        if(prop4 == 1){
            prop4 =0;
            map4.put("E1 Remove_OK E0", true);
        }

        if (prop2 == 0 ){
            prop2 = 2;
            map2.put("E0 Remove_OK E2", true);

        }

        switch (prop3){
            case 1 : prop3 = 3;
                map3.put("E1 Remove_KO E3", true);
                System.err.println("ERROR PROP 3");
                break;
            case 0 : if(panier == 1) {
                        map3.put("E0 [Panier=1]Remove_OK E1", true);
                        prop3 = 1;
                    }
                break;

        }
    }

    public void transmission(int panier, int r) {

                if(panier >= 1 && prop5 == 0 && r == 1 ){
                    map5.put("E0 [Panier<1]? E1", true);
                    prop5 = 2;

                }
                else if(prop5 == 2 && r == 1){
                    map5.put("E2 [Panier>=1]? E3", true);
                    prop5 = 3;
                }
                else if(prop5 == 0 && r == 1){
                    map5.put("E0 Controle_KO[Transmission:1] E1", true);
                    prop5 = 1;
                    System.err.println("ERROR PROP 5  ");
                }

                switch (prop6){
                    case 1 :
                        if(r == 1){
                            prop6 = 3;
                            map6.put("E1 Controle_OK[Transmission:1] E3 ", true);
                        }
                    break;
                    case 3 :
                        if(r == 1){
                            prop6 = 4;
                            map6.put("E3 Controle_KO[Transmission:1] E4", true);
                            System.err.println("ERROR PROP 6  ");
                        }
                        break;
                }
    }

        public void payer(){
            if(prop6 == 1 || prop6 == 3){
                if(prop6 == 1 ){
                    map6.put("E1 Payment E2 ", true);
                }
                if(prop6 == 3){
                    map6.put("E3 Payment E2", true);
                }
                prop6 = 2;
            }
        }

        public void couverture(Map<String,Boolean> map, String s){
            System.out.println(s);
            for (Map.Entry mapentry : map.entrySet()) {
                    System.out.println("clé: "+mapentry.getKey()
                            + " | valeur: " + mapentry.getValue());
            }
        }
        public void display(){
            couverture(map1,"Propriété 1 ");
            couverture(map2,"Propriété 2 ");
            couverture(map3,"Propriété 3 ");
            couverture(map4,"Propriété 4 ");
            couverture(map5,"Propriété 5 ");
            couverture(map6,"Propriété 6");
        }
    }

