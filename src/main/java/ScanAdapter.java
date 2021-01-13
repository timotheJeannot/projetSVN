import fr.ufc.m2info.svam.Article;
import fr.ufc.m2info.svam.ArticleDB;
import fr.ufc.m2info.svam.Caisse;
import fr.ufc.m2info.svam.Scanette;
import junit.framework.Assert;

import java.util.*;

/**
 * Adapter used to bridge the gap between the model level and the implementation level.
 */
public class ScanAdapter {

    // array of all articles
    final public Article[] allArticles = {
            new Article(3560070048786l,0.87, "Cookies choco"),
            new Article(3017800238592l,2.20,"Daucy Curry vert de légumes riz graines de courge et tournesol"),
            new Article(3560070976478l,1.94,"Poulet satay et son riz"),
            new Article(3046920010856l,2.01,"Lindt Excellence Citron à la pointe de Gingembre"),
            new Article(8715700110622l,0.96,"Ketchup"),
            new Article(3570590109324l,7.48,"Vin blanc Arbois Vieilles Vignes"),
            new Article(3520115810259l,8.49,"Mont d'or moyen Napiot"),
            new Article(3270190022534l,0.58,"Pâte feuilletée"),
            new Article(8718309259938l,4.65,"Soda stream saveur agrumes"),
            new Article(3560071097424l,2.40,"Tartelettes carrées fraise"),
            new Article(3017620402678l,1.86,"Nutella 220g"),
            new Article(3245412567216l,1.47,"Pain de mie"),
            new Article(45496420598l,54.99,"Jeu switch Minecraft"),
            new Article(3560070139675l,1.94,"Boîte de 110 mouchoirs en papier"),
            new Article(3020120029030l,1.70,"Cahier Oxford 90 pages petits carreaux"),
            new Article(7640164630021l,229.90,"Robot éducatif Thymio"),
            new Article(5410188006711l,2.15, "Tropicana Tonic Breakfast")
    };

    // scanner
    ArticleDB dbScanette = newArticleDBScanette();
    Scanette scan = new Scanette(dbScanette);

    // cash register
    ArticleDB dbCaisse = newArticleDBCaisse();
    Caisse caisse = new Caisse(dbCaisse);

    List<Long> basket = new ArrayList<Long>();


    /**
     * Resets the scanner and the cash register with new instances. 
     */
    public void reset() {
        scan = new Scanette(dbScanette);
        caisse = new Caisse(dbCaisse);
        basket.clear();
    }

    public int debloquer() {
        basket.clear();
        return scan.debloquer();
    }

    public void abandon() {
        scan.abandon();
        basket.clear();
    }

    public int scanAchatOK() {
        int i = (int) (Math.random() * (allArticles.length - 2));
        int j = (int) (Math.random() * basket.size());
        basket.add(j, allArticles[i].getCodeEAN13());
        return scan.scanner(allArticles[i].getCodeEAN13());
    }
    public int scanRefInconnue() {
        int i = (int) (Math.random() * 2);
        return scan.scanner(allArticles[allArticles.length-1-i].getCodeEAN13());
    }

    public int supprimerOK() {
        Set<Article> set = scan.getArticles();
        Assert.assertTrue(set.size() > 0);
        int i = (int) (Math.random() * set.size());
        Iterator<Article> it = set.iterator();
        Article a = it.next();
        while (it.hasNext() && i > 1) {
            i--;
            a = it.next();
        }
        basket.remove(basket.indexOf(a.getCodeEAN13()));
        return scan.supprimer(a.getCodeEAN13());
    }

    public int supprimerKO() {
        return scan.supprimer(0l);
    }


    public int transmission() {
        return scan.transmission(caisse);
    }

    public int relectureOK() {
        int i=0, r=0;
        while (i < basket.size() && i < 12) {
            r = scan.scanner(basket.get(i));
            i++;
        }
        return r;
    }

    public int relectureKO() {
        return scan.scanner(0l);
    }

    public int ouvrirSession() {
        return caisse.ouvrirSession();
    }

    public int fermerSession() {
        return caisse.fermerSession();
    }

    public int ajouterAchat() {
        int i = (int) (Math.random() * allArticles.length);
        basket.add(0, allArticles[i].getCodeEAN13());
        return caisse.scanner(allArticles[i].getCodeEAN13());
    }
    public int supprimerAchat() {
        long b = basket.remove(0);
        return caisse.supprimer(b);
    }

    public double payer() {
        double d = 0.0;
        for (long code : basket) {
            for (Article a : allArticles) {
                if (a.getCodeEAN13() == code) {
                    d += a.getPrixUnitaire();
                    break;
                }
            }
        }
        return caisse.payer(Math.ceil(d));
    }


    /**
     * Initializes a database for the scanners with all articles except the 2 with a barcode ending with "1".
     */
    private ArticleDB newArticleDBScanette() {
        Set<Article> r = new HashSet<Article>();
        for (Article a : allArticles) {
            if (a.getCodeEAN13() % 10 != 1) {
                r.add(a);
            }
        }
        ArticleDB db = new ArticleDB();
        db.init(r);
        return db;
    }

    /**
     * Initializes a database for the cash registers with all articles (no exception).
     */
    private ArticleDB newArticleDBCaisse() {
        Set<Article> r = new HashSet<Article>();
        for (Article a : allArticles) {
            r.add(a);
        }
        ArticleDB db = new ArticleDB();
        db.init(r);
        return db;
    }

}