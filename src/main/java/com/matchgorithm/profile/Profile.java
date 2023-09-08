package com.matchgorithm.profile;

import com.matchgorithm.view.Helper;
import org.fusesource.jansi.Ansi;

import java.util.*;

public class Profile {

    // static and final variables
    private static final String nameFilePath = "profile/names.txt";
    private static final String jobTitleFilePath = "profile/job_titles.txt";
    private static final String companyFilePath = "profile/companies.txt";
    private static final String interestFilePath = "profile/hobbies.txt";

    // class level static variable
    private static int id = 0;

    //Instance Variables
    private final int uniqueId;

    private final Picture pic;
    private final String name;
    private String interest;
    private String jobTitle;
    private String company;
    private int age;
    private int distance;

    //Constructor
    public Profile() {
        // create unique ID for each instance
        this.uniqueId = ++id;

        pic = new Picture();
        name = generateName();
        jobTitle = generateJobTitle();
        company = generateCompany();
        interest = generateInterest();

        Random rand = new Random();
        age = rand.nextInt(30)+ 18;
        distance = rand.nextInt(51);
    }

    public Profile(Picture pic, String name, String interest, String jobTitle, String company) {
        // create unique ID for each instance
        this.uniqueId = ++id;

        this.pic = pic;
        this.name = name;
        this.interest = interest;
        this.jobTitle = jobTitle;
        this.company = company;

        Random rand = new Random();
        age = rand.nextInt(30)+ 18;
        distance = rand.nextInt(51);
    }

    /*
     * INSTANCE FIELD GENERATORS
     */

    // generate name
    String generateName() {
        return Helper.getRandomItemFromFile(nameFilePath, " ");
    }

    // generate jobTitle
    String generateJobTitle() {
        return Helper.getRandomItemFromFile(jobTitleFilePath, ", ");
    }

    // generate company
    String generateCompany() {
        return Helper.getRandomItemFromFile(companyFilePath, "\n");
    }

    String generateInterest() {
        return Helper.getRandomItemFromFile(interestFilePath, ", ");
    }

    // accessor methods

    public int getUniqueId() {return uniqueId;}

    public String getName() {return name;}

    public String getJobTitle() {return jobTitle;}

    public String getCompany() {return company;}

    public String getCareer() {return getJobTitle() + " @ " + getCompany();}

    public int getAge() {return age;}

    public int getDistance() {return distance;}

    // comparator method
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Profile profile = (Profile) other;
        return uniqueId == profile.uniqueId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uniqueId);
    }

    @Override
    public String toString() {
       return Helper.colorText(pic.getPic(), Ansi.Color.MAGENTA) + "\n\n" +
               Helper.colorText(getName(), Ansi.Color.YELLOW) + ", age " + getAge() + "\n" +
               getDistance() + " miles away" + "\n\n" +
               getCareer() + "\n" +
               "I enjoy " + interest + ".\n\n" +
               Helper.colorText(
                       "-----------------------------------------------------------------------------------", Ansi.Color.GREEN);
    }
}