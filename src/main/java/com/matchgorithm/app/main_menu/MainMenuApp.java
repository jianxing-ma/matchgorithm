package com.matchgorithm.app.main_menu;

import com.matchgorithm.app.AppInterface;
import com.matchgorithm.app.UserInterfaceStatus;
import com.matchgorithm.view.Helper;
import org.fusesource.jansi.Ansi;

import java.util.Scanner;

public class MainMenuApp implements AppInterface {

    // static variables
    private static final String WELCOME_BANNER_PATH = "prompt_messages/welcome_banner.txt";
    private static final String MAIN_MENU_OPTIONS_PATH = "prompt_messages/main_menu_options.txt";

    // Instance fields
    UserInterfaceStatus userInterfaceStatus = UserInterfaceStatus.MAIN_MENU;

    // constructor
    public MainMenuApp() {
    }

    // business methods

    @Override
    public void execute() {

        // calibrate userInterfaceStatus to the current one
        userInterfaceStatus = UserInterfaceStatus.MAIN_MENU;

        // print Welcome banner
        Helper.printFile(WELCOME_BANNER_PATH, Ansi.Color.MAGENTA);

        // present user options in main menu
        Helper.printColor("                         Swipe(S) | Matches(M)\n" +
                "                               Exit(X)\n" +
                "                               Enter: ", Ansi.Color.GREEN);

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().trim().toUpperCase();

        // direct user to certain interface according to input choice
        switch (input) {
            case "S":
                userInterfaceStatus = UserInterfaceStatus.SWIPE;
                break;
            case "M":
                userInterfaceStatus = UserInterfaceStatus.MATCH_LIST;
                break;
            case "X":
                userInterfaceStatus = UserInterfaceStatus.EXIT;
                break;
        }
    }

    @Override
    public UserInterfaceStatus updateUserInterfaceStatus() {
        return this.userInterfaceStatus;
    }
}