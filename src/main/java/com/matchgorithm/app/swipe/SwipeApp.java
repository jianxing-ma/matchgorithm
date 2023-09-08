package com.matchgorithm.app.swipe;

import com.matchgorithm.profile.Picture;
import com.matchgorithm.profile.Profile;
import com.matchgorithm.app.UserInterfaceStatus;
import com.matchgorithm.app.AppInterface;
import com.matchgorithm.view.Helper;
import org.fusesource.jansi.Ansi;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class SwipeApp implements AppInterface {
    // static fields
    private static final String swipeAppInterfaceOptions = "\nSwipe Left(L) | Super-Like(S) | Swipe Right\n"
            + "                   Exit(X)\n"
            + "                   Enter: ";
    private static final String MATCHED_PROMPT_FILEPATH= "prompt_messages/matched_banner.txt";
    private static final int SWIPE_RIGHT_MATCH_POSSIBILITY = 50;
    private static final int SUPER_LIKE_MATCH_POSSIBILITY = 25;

    // instance variables;
    UserInterfaceStatus userInterfaceStatus = UserInterfaceStatus.SWIPE;
    List<Profile> matches;

    // constructor
    public SwipeApp(List<Profile> matches) {
        this.matches = matches;
    }


    // business methods

    @Override
    public void execute() {

        // calibrate userInterfaceStatus to the current one
        userInterfaceStatus = UserInterfaceStatus.SWIPE;

        // Generate new bot profile to present to user

        Picture.initializePicList();

        Profile profile = new Profile();
        System.out.println(profile);

        // view: Present user options
        Helper.printColor(swipeAppInterfaceOptions, Ansi.Color.GREEN);

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().trim().toUpperCase();

        Random rand = new Random();

        // delegate user input to specific actions
        switch (input){
            case "S":
                int chanceRight = rand.nextInt(100);
                if (chanceRight >= SUPER_LIKE_MATCH_POSSIBILITY){
                    match(profile);
                }
                break;
            case "R":
                int chanceSuper = rand.nextInt(100);
                if (chanceSuper >= SWIPE_RIGHT_MATCH_POSSIBILITY) {
                    match(profile);
                }
                break;
            case "X":
                userInterfaceStatus = UserInterfaceStatus.MAIN_MENU;
                break;
        }
    }

    // When profiles match, add matched profile to matchList,
    // and allow option to go to MessengerApp interface or continue
    private void match(Profile profile) {
        matches.add(0, profile);
        Helper.printFile(MATCHED_PROMPT_FILEPATH, Ansi.Color.MAGENTA);
        Helper.printColor(
                "Press [M]essenger to chat or Enter to continue swiping...\n", Ansi.Color.GREEN);

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().trim().toUpperCase();
        if ("M".equals(input)) {
            userInterfaceStatus = UserInterfaceStatus.MATCH_LIST;
        }
    }

    // accessor methods

    @Override
    public UserInterfaceStatus updateUserInterfaceStatus() {
        return this.userInterfaceStatus;
    }

}