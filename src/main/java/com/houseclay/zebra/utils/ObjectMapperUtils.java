package com.houseclay.zebra.utils;

import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ObjectMapperUtils {

    public String extractPreferredLocationfromList(ArrayList<String> preferredLocations){
        StringBuilder preferredLocString = new StringBuilder();
        for(String str : preferredLocations) {
            preferredLocString.append(str+"-");
        }
        return preferredLocString.substring(0,preferredLocString.length()-1);
    }
}
