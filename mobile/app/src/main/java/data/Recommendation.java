package data;

import  java.util.*;

public class Recommendation {
    List<String> RecItems;
    public Recommendation(){}
    public Recommendation(List<String> list){
        RecItems = new ArrayList<>();
        for(String str : list){
            RecItems.add(str);
        }
    }
}
