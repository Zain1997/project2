package nl.amazingamazigh.amazigh;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import java.util.ArrayList;


public class SplashScreen extends Activity {

    private final int DISPLAY_LENGTH = 3000;
    dbHandler db = new dbHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        ArrayList<ArrayList> answersList = new ArrayList<>();

        ArrayList<Translation> dieren01List = new ArrayList<>();
        ArrayList<Translation> dieren02List = new ArrayList<>();
        ArrayList<Translation> insectenList = new ArrayList<>();
        ArrayList<Translation> fruitList = new ArrayList<>();
        ArrayList<Translation> groenteList = new ArrayList<>();
        ArrayList<Translation> etenList = new ArrayList<>();
        ArrayList<Translation> kledingList = new ArrayList<>();
        ArrayList<Translation> kleurenList = new ArrayList<>();
        ArrayList<Translation> weerjaarList = new ArrayList<>();

        //region QUESTION ARRAYS PER CATEGORY

        //Dieren01
        dieren01List.add( new Translation("ezel", "donkey", "burro", "âne", "Esel", "aɣyul"));             //1
        dieren01List.add( new Translation("paard", "horse", "caballo", "cheval", "Pferd", "ayis"));        //2
        dieren01List.add( new Translation("schaap", "sheep", "oveja", "mouton", "Schaf", "icerri"));       //3
        dieren01List.add( new Translation("geit", "goat", "cabra", "chèvre", "Ziege", "tɣaṭṭ"));            //4
        dieren01List.add( new Translation("kip", "chicken", "pollo", "poulet", "Huhn", "tyaziḍt"));        //5
        dieren01List.add( new Translation("konijn", "rabbit", "conejo", "lapin", "Kaninchen", "aqninni")); //6
        dieren01List.add( new Translation("koe", "cow", "vaca", "vache", "Kuh", "tafunast"));              //7
        dieren01List.add( new Translation("hond", "dog", "perro", "chien", "Hund", "ayḍi"));               //8
        dieren01List.add( new Translation("vogel", "bird", "pájaro", "oiseau", "Vogel", "agḍiḍ"));         //9
        dieren01List.add( new Translation("kat", "cat", "gato", "chat", "Katze", "mucc"));                 //10
        dieren01List.add( new Translation("muis", "mouse", "ratón", "souris", "Maus", "aɣerda"));          //11
        dieren01List.add( new Translation("egel", "hedgehog", "erizo", "hérisson", "Igel", "insi"));       //12
        dieren01List.add( new Translation("vis", "fish", "pescado", "poisson", "Fisch", "aslem"));         //13
        dieren01List.add( new Translation("kikker", "frog", "rana", "grenouille", "Frosch", "aqaqriw"));   //14
        dieren01List.add( new Translation("jakhals", "jackal", "chacal", "chacal", "Schakal", "uccen"));   //15

        //Fruit
        fruitList.add(new Translation("appel", "apple", "manzana", "pomme", "Apfel", "tateffaht"));                                     //1
        fruitList.add(new Translation("peer", "pear", "pera", "poire", "Birne", "tafirast"));                                           //2
        fruitList.add(new Translation("abrikoos", "apricot", "albaricoque", "abricot", "Aprikose", "rmecmac"));                         //3
        fruitList.add(new Translation("perzik", "peach", "melocotón", "pêche", "Pfirsich", "rxux"));                                    //4
        fruitList.add(new Translation("druiven", "grapes", "uvas", "raisins", "Trauben", "aḍil"));                                      //5
        fruitList.add(new Translation("watermeloen", "watermelon", "sandía", "pastèque", "Wassermelone", "ddellaɛ"));                   //6
        fruitList.add(new Translation("honingmeloen", "honey dew melon", "melón de miel", "melon de miel", "Honigmelone", "abettix"));  //7
        fruitList.add(new Translation("granaatappel", "pomegranate", "granada", "grenade", "Granatapfel", "arremman"));                 //8
        fruitList.add(new Translation("vijg", "fig", "higo", "figue", "Feige", "tazart"));                                              //9
        fruitList.add(new Translation("cactusvijg", "cactus pear", "higo chumbo", "figue de barbarie", "Kaktusfeige", "tahendict"));    //10
        fruitList.add(new Translation("pruim", "prum", "ciruela", "prune", "Pflaume", "rbarquq"));                                      //11
        fruitList.add(new Translation("sinaasappel", "orange", "naranja", "orange", "Orange", "taleccint"));                            //12
        fruitList.add(new Translation("dadel", "date", "fecha", "date", "Dattel", "tini"));                                             //13
        fruitList.add(new Translation("citroen", "lemon", "limón", "citron", "Zitrone", "llaymun"));                                    //14
        fruitList.add(new Translation("bananen", "bananas", "plátanos", "bananes", "Bananen", "banan"));                                //15

        //Insecten
        insectenList.add(new Translation("slak", "snail", "caracol", "escargot", "Schnecke", "aɣlal"));                         //1
        insectenList.add(new Translation("regenworm", "earthworm", "lombriz", "ver de terre", "Regenwurm", "adan n tmurt"));    //2
        insectenList.add(new Translation("larve", "larva", "larva", "larve", "Larve", "takecca"));                              //3
        insectenList.add(new Translation("vlieg", "fly", "mosca", "mouche", "Fliege", "iẓi"));                                  //4
        insectenList.add(new Translation("bij", "bee", "abeille", "Biene", "bij", "zizwit"));                                   //5
        insectenList.add(new Translation("mug", "mosquito", "mosquito", "moustique", "Mücke", "nnamus"));                       //6
        insectenList.add(new Translation("mier", "ant", "hormiga", "fourmi", "Ameise", "tikedfet"));                            //7
        insectenList.add(new Translation("vlinder", "butterfly", "mariposa", "papillon", "Schmetterling", "aferṭṭu"));           //8
        insectenList.add(new Translation("spin", "spider", "araña", "araignée", "Spinne", "qundɣa"));                           //9
        insectenList.add(new Translation("sprinkhaan", "grasshopper", "saltamontes", "sauterelle", "Heuschrecke", "burxes"));   //10
        insectenList.add(new Translation("rups", "caterpillar", "oruga", "chenille", "Raupe", "bugḍif"));                       //11
        insectenList.add(new Translation("wesp", "wasp", "avispa", "guêpe", "Wespe", "yaẓẓa"));                                  //12
        insectenList.add(new Translation("naaktslak", "slug", "babosa", "limace", "Nacktschnecke", "buccel"));                  //13
        insectenList.add(new Translation("bloedzuiger", "leech", "sanguijuela", "sangsue", "Blutsauger", "tiḍḍa"));             //14
        insectenList.add(new Translation("bidsprinkhaan", "mantis", "mantis", "mante", "Gottesanbeterin", "tamraebt"));         //15

        //Groente
        groenteList.add(new Translation("kikkererwten", "chickpeas", "garbanzos", "pois chiches", "Kichererbsen", "rḥimez"));  //1
        groenteList.add(new Translation("linzen", "lentils", "lentejas", "lentilles", "Linsen", "leɛdes"));                    //2
        groenteList.add(new Translation("bonen", "beans", "frijoles", "haricots", "Bohnen", "llubeyyet"));                     //3
        groenteList.add(new Translation("tomaat", "tomato", "tomate", "tomate", "Tomate", "ttumatic"));                        //4
        groenteList.add(new Translation("olijven", "olives", "aceitunas", "olives", "Oliven", "zzitun"));                      //5
        groenteList.add(new Translation("aardappel", "potato", "patata", "pomme de terre", "Kartoffel", "baṭaṭa"));             //6
        groenteList.add(new Translation("ui", "onion", "cebolla", "oignon", "Zwiebel", "rebser"));                             //7
        groenteList.add(new Translation("knoflook", "garlic", "ajo", "ail", "Knoblauch", "ticcart"));                          //8
        groenteList.add(new Translation("mais", "corn", "maíz", "maïs", "Mais", "dra"));                                       //9
        groenteList.add(new Translation("paprika", "bell pepper", "pimiento", "poivron", "Paprika", "rferfer"));               //10
        groenteList.add(new Translation("pompoen", "pumpkin", "calabaza", "citrouille", "Kürbis", "taxsact"));                 //11
        groenteList.add(new Translation("wortel", "carrot", "zanahoria", "carotte", "Karotte", "xizzu"));                      //12
        groenteList.add(new Translation("doperwten", "peas", "chícharos", "petit pois", "Erbsen", "tinifin"));                 //13
        groenteList.add(new Translation("tuinbonen", "broad beans", "habas", "fèves", "Saubohnen", "ibawen"));                 //14
        groenteList.add(new Translation("spinazie", "spinach", "espinacas", "épinards", "Spinat", "ṭebi"));                     //15

        //dieren02
        dieren02List.add( new Translation("patrijs", "partridge", "perdiz", "perdrix", "Rebhuhn", "asekkur"));                   //1
        dieren02List.add( new Translation("uil", "owl", "búho", "chouette", "Eule", "muka"));                                    //2
        dieren02List.add( new Translation("gekko", "gecko", "geco", "gecko", "Gecko", "aḥamchel"));                              //3
        dieren02List.add( new Translation("schorpioen", "scorpion", "escorpión", "scorpion", "Skorpion", "tɣirdent"));           //4
        dieren02List.add( new Translation("stekelvarken", "porcupine", "puerco espín", "porc-épic", "Stachelschwein", "aruy"));  //5
        dieren02List.add( new Translation("haas", "hare", "liebre", "lièvre", "Hase", "ayaṛziz"));                               //6
        dieren02List.add( new Translation("vos", "fox", "zorro", "renard", "Fuchs", "icɛb"));                                   //7
        dieren02List.add( new Translation("windhond", "greyhound", "galgo", "levrette", "Windhund", "uccay"));                  //8
        dieren02List.add( new Translation("hyena", "hyena", "hiena", "hyène", "Hyäne", "ifis"));                                //9
        dieren02List.add( new Translation("wildzwijn", "boar", "jabali", "sanglier", "Wildschwein", "ilf"));                    //10
        dieren02List.add( new Translation("slang", "snake", "serpiente", "serpent", "Schlange", "afiɣra"));                     //11
        dieren02List.add( new Translation("schildpad", "turtle", "tortuga", "tortue", "Schildkröte", "icfar"));                 //12
        dieren02List.add( new Translation("kameleon", "chameleon", "camaleón", "caméléon", "Chamäleon", "tata"));               //13
        dieren02List.add( new Translation("zwaluw", "swallow", "golondrina", "hirondelle", "Schwalbe", "tiflillest"));          //14
        dieren02List.add( new Translation("duif", "pigeon", "paloma", "pigeon", "Taube", "adbir"));                             //15

        //Eten
        etenList.add(new Translation("eieren", "eggs", "huevos", "œufs", "Eier", "timejjalin"));        //1
        etenList.add(new Translation("olie", "oil", "aceite", "huile", "Öl", "zzict"));                 //2
        etenList.add(new Translation("koekjes", "cookies", "galletas", "biscuits", "Kekse", "ɣayiṭa"));  //3
        etenList.add(new Translation("thee", "tea", "té", "thé", "Tee", "atay"));                        //4
        etenList.add(new Translation("koffie", "coffee", "café", "café", "Kaffee", "rqehwa"));           //5
        etenList.add(new Translation("melk", "milk", "leche", "lait", "Milch", "aɣi"));                  //6 karnemelk <-> melk?
        etenList.add(new Translation("yoghurt", "yogurt", "yogur", "yaourt", "Joghurt", "accil"));       //7
        etenList.add(new Translation("water", "water", "agua", "eau", "Wasser", "aman"));                //8
        etenList.add(new Translation("brood", "bread", "pan", "pain", "Brot", "aɣrum"));                 //9
        etenList.add(new Translation("honing", "honey", "miel", "miel", "Honig", "tamment"));            //10
        etenList.add(new Translation("suiker", "sugar", "azúcar", "sucre", "Zucker", "sseqqur"));        //11
        etenList.add(new Translation("zout", "salt", "sal", "sel", "Salz", "tamellaḥt"));                //12
        etenList.add(new Translation("vlees", "meat", "carne", "viande", "Fleisch", "aysum"));           //13
        etenList.add(new Translation("vissen", "fish", "pescado", "poisson", "Fisch", "iselman"));       //14
        etenList.add(new Translation("boter", "butter", "mantequilla", "beurre", "Butter", "ddhen"));    //15

        //Kleding
        kledingList.add(new Translation("broek", "pants", "pantalones", "pantalon", "Hose", "ssarwal"));                        //1
        kledingList.add(new Translation("trui", "sweater", "suéter", "chandail", "Pullover", "amaryul"));                       //2
        kledingList.add(new Translation("onderhemd", "undershirt", "camiseta", "maillot de corps", "Unterhemd", "camisita"));   //3 hemd?
        kledingList.add(new Translation("jas", "jacket", "chaqueta", "veste", "Jacke", "aqabud"));                              //4
        kledingList.add(new Translation("schoenen", "shoes", "zapatos", "chaussures", "Schuhe", "tisila"));                     //5
        kledingList.add(new Translation("laarzen", "boots", "botas", "bottes", "Stiefel", "ihakusen"));                         //6
        kledingList.add(new Translation("muts", "cap", "gorro", "bonnet", "Kappe", "tcaccict"));                                //7
        kledingList.add(new Translation("jurk", "dress", "vestido", "robe", "Kleid", "tablust"));                               //8
        kledingList.add(new Translation("rok", "skirt", "falda", "jupe", "Rock", "farda"));                                     //9
        kledingList.add(new Translation("slippers", "flip flops", "chanclas", "claquette", "Flip Flops", "tcinklat"));          //10
        kledingList.add(new Translation("handschoenen", "gloves", "guantes", "gants", "Handschuhe", "iwantisan"));              //11
        kledingList.add(new Translation("sokken", "socks", "calcetines", "chaussettes", "Socken", "tqacir"));                   //12
        kledingList.add(new Translation("overhemd", "shirt", "camisa", "chemise", "Hemd", "taqmijjat"));                        //13
        kledingList.add(new Translation("sjaal", "scarf", "bufanda", "écharpe", "Schal", "ahewwak"));                           //14
        kledingList.add(new Translation("riem", "belt", "cinturón", "ceinture", "Gürtel", "abyas"));                            //15

//        //Kleuren
        kleurenList.add(new Translation("rood", "red", "rojo", "rouge", "rot", "azegg-aɣ"));                //1
        kleurenList.add(new Translation("geel", "yellow", "amarillo", "jaune", "gelb", "awraɣ"));           //2
        kleurenList.add(new Translation("blauw", "blue", "azul", "bleu", "blau", "aḥmaymi"));               //3
        kleurenList.add(new Translation("zwart", "black", "negro", "noir", "schwarz", "abarcan"));          //4
        kleurenList.add(new Translation("wit", "white", "blanco", "blanc", "weiß", "acemlal"));             //5
        kleurenList.add(new Translation("bruin", "brown", "marrón", "brun", "braun", "ahemruni"));          //6
        kleurenList.add(new Translation("groen", "green", "verde", "vert", "grün", "aziza"));               //7
        kleurenList.add(new Translation("oranje", "orange", "naranja", "orange", "orange", "aletcini"));    //8
        kleurenList.add(new Translation("bont", "colourful", "vistoso", "coloré", "bunt", "akarkac"));      //9
        kleurenList.add(new Translation("paars", "purple", "morado", "mauve", "lila", "azbaybi"));          //10

        //Weer en Jaargetijden
        weerjaarList.add(new Translation("lente", "spring", "primavera", "printemps","Frühling","tafsut"));                  //1
        weerjaarList.add(new Translation("zomer", "summer", "verano", "été","Sommer","anebdu"));                             //2
        weerjaarList.add(new Translation("herfst", "autumn", "otoño", "automne","Herbst","leiglief"));                       //3
        weerjaarList.add(new Translation("winter", "winter", "invierno", "hiver","Winter","tajarst"));                       //4
        weerjaarList.add(new Translation("regen", "rain", "lluvia", "pluie","Regen","anẓar"));                               //5
        weerjaarList.add(new Translation("sneeuw", "snow", "nieve", "neige","Schnee","adfel"));                              //6
        weerjaarList.add(new Translation("wind", "wind", "viento", "vent","Wind","asemmiḍ"));                                //7
        weerjaarList.add(new Translation("regenboog", "rainbow", "arco iris", "arc en ciel","Regenbogen","taslit n unzar")); //8
        weerjaarList.add(new Translation("maan", "moon", "luna", "lune","Mond","yur"));                                      //9
        weerjaarList.add(new Translation("zon", "sun", "sol", "soleil","Sonne","tfuct"));                                    //10
        weerjaarList.add(new Translation("halvemaan", "crescent", "creciente", "croissant","Halbmond","taziri"));            //11
        weerjaarList.add(new Translation("donder", "thunder", "trueno", "tonnerre","Donner","ajjaj"));                       //12
        weerjaarList.add(new Translation("ster", "star", "estrella", "vedette","Star","itri"));                              //13
        weerjaarList.add(new Translation("mist", "fog", "niebla", "brouillard","Nebel","tayyut"));                           //14
        weerjaarList.add(new Translation("wolken", "clouds", "nubes", "nuages","Wolken","asinu"));                           //15

        //endregion

        answersList.add(dieren01List); //category 1
        answersList.add(fruitList); //category 2
        answersList.add(insectenList); //category 3
        answersList.add(groenteList); //category 4
        answersList.add(dieren02List); //category 5
        answersList.add(etenList); //category 6
        answersList.add(kledingList); //category 7
        answersList.add(kleurenList); //category 8
        answersList.add(weerjaarList); //category 9

        for(int i = 0; i < answersList.size(); i++) {
            for(int y = 0; y < answersList.get(i).size(); y++) {
                db.addRecord(i + 1, (Translation)answersList.get(i).get(y));
            }
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                overridePendingTransition(R.anim.slide_in_forward, R.anim.slide_out_forward);
                SplashScreen.this.finish();
            }
        }, DISPLAY_LENGTH);
    }
}