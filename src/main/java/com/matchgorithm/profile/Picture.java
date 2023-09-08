package com.matchgorithm.profile;

import com.matchgorithm.view.Helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.io.File.*;

public class Picture {

    //Instance Variables
    private String pic;

    private static List<String> pictures = new ArrayList<>();
    private static final String pictureFilePath = "profile/pictures/";

    public static void initializePicList() {
        for (int i = 1; i < 6; i++) {
            pictures.add(Helper.readFromResourceFile(
                    pictureFilePath + "pic" + String.valueOf(i) + ".txt"));
        }
    }

    //Constructor
    public Picture() {
        Random rand = new Random();
        String element = pictures.get(rand.nextInt(pictures.size()));
        setPic(element);
    }

    //Business Method
    public String getPic() {
        return pic;
    }

    private void setPic(String pic) {
        this.pic = pic;
    }
}