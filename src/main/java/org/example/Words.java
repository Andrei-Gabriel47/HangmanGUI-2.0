package org.example;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Words
{
    //<categories,wordslist>
    private HashMap<String,String[]> wordsList;
    //for selectin a random categorie from the HashMap
    private ArrayList<String> categories;
    public Words()
    {
          wordsList = new HashMap<>();
            categories = new ArrayList<>();
            wordsList.put("Animals", new String[]{"ZEBRA", "ELEPHANT", "CAT", "DOLPHIN","LION","BEAR"});
            wordsList.put("Food",new String[]{"PIZZA","PASTA","BURGER","SHAORMA","ICECREAM"});
            wordsList.put("Colour",new String[]{"BLACK","WHITE","MAGENTA","PURPLE","RED","PINK"});
            wordsList.put("Jobs",new String[]{"DOCTOR","POLICEMAN","LAWYER","FIREMAN","TEACHER","NUTRITIONIST"});
            wordsList.put("Sports",new String[]{"FOOTBALL","BASKETBALL","HANDBALL","VOLEY","SNOOKER"});
            wordsList.put("Fruits",new String[]{"ORANGE","GRAPEFRUIT","WATERMELON","APPLE","BANANA","STRAWBERY"});
            categories.add("Animals");categories.add("Food");
            categories.add("Colour");categories.add("Jobs");
            categories.add("Sports");categories.add("Fruits");
    }
    public String[] LoadChallange()
    {
        Random random = new Random();
        int randomIndex = random.nextInt(categories.size());
        String category = categories.get(randomIndex);
        String[] categoryValues = wordsList.get(category);
        String wordGame = categoryValues[random.nextInt(categoryValues.length)];
        return  new String[]{category,wordGame};
    }
}
