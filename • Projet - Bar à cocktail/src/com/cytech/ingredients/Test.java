package com.cytech.ingredients;

/*utile pour JSON*/
import java.io.*;
import java.util.Calendar;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.ArrayList;

import java.util.Arrays;

import java.util.HashMap;

public class Test {



    public static  ArrayList<Cocktail> readJSONCocktails(FileReader file) throws IOException, ParseException {
        ArrayList<Cocktail> COCKTAILS = new ArrayList<Cocktail>();

        JSONParser jsonparser = new JSONParser();
        Object obj= jsonparser.parse(file);
        JSONObject jsonobject=(JSONObject)obj;

        JSONArray array = (JSONArray) jsonobject.get("Cocktails");
        // Pour chaque Cocktails
        for(int i=0;i<array.size();i++){
            HashMap<Boisson,Integer> recetteC = new HashMap<Boisson,Integer>();
            JSONObject cockt=(JSONObject) array.get(i);

            String nomC = (String)  cockt.get("nom");
            String couleurC = (String)  cockt.get("couleur");

            JSONArray arrayIngredient = (JSONArray) cockt.get("ingredients");
            //Pour chaq ingrédient
            for(int k = 0; k < arrayIngredient.size(); k++) {
                JSONObject boiss = (JSONObject) arrayIngredient.get(k);

                  String nomB = (String) boiss.get("nom");
                  String couleurB = (String) boiss.get("couleur");
                  int contenanceB = (int) boiss.get("contenance");
                  double prixMlB = (double) boiss.get("prixMl");

                  if(null == boiss.get("degreSucre")) // si c'est un alcool
                  {
                      double degre = (double) boiss.get("degreAlcool");
                      BoissonAlcoolisee x = new BoissonAlcoolisee(nomB,couleurB,prixMlB,degre);
                      recetteC.put(x,contenanceB); // ajout
                  } else {
                      double degre = (double) boiss.get("degreSucre");
                      BoissonNonAlcoolisee x = new BoissonNonAlcoolisee(nomB,couleurB,prixMlB,degre);
                      recetteC.put(x,contenanceB); // ajout
                  }
            }

            Cocktail coco = new Cocktail(nomC,recetteC);
            COCKTAILS.add(coco);

        }

        return COCKTAILS;
    }

}
