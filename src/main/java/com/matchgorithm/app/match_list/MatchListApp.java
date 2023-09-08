package com.matchgorithm.app.match_list;

import com.matchgorithm.profile.Profile;
import com.matchgorithm.app.UserInterfaceStatus;
import com.matchgorithm.app.AppInterface;
import com.matchgorithm.view.Helper;
import org.fusesource.jansi.Ansi;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class MatchListApp implements AppInterface {

    // instance variables
    private UserInterfaceStatus userInterfaceStatus = UserInterfaceStatus.MATCH_LIST;
    private final List<Profile> matches;
    private final MatchList matchList;

    // Messenger related variables
    private Profile selectedProfile;
    private final HashMap<Profile, StringBuilder> chatMap = new HashMap<>();

    // constructor
    public MatchListApp(List<Profile> matches) {
        this.matches = matches;
        this.matchList = new MatchList(matches);
    }

    // business method

    @Override
    public void execute() {
        // calibrate userInterfaceStatus to the current one
        userInterfaceStatus = UserInterfaceStatus.MATCH_LIST;

        matchList.showMatchList();

        Scanner in = new Scanner(System.in);

        String input = in.nextLine().toUpperCase();

        // go to messenger interface
        int choice;
        try {
            choice = Integer.parseInt(input);

            if (choice >= 0) {
                int lastPage = matches.size() / MatchList.MATCHES_PER_PAGE;

                if ((matchList.getCurrentPage() < lastPage
                        && choice < MatchList.MATCHES_PER_PAGE)
                        | (matchList.getCurrentPage() == lastPage
                        && choice < matches.size() % MatchList.MATCHES_PER_PAGE)) {

                    selectedProfile = matchList.selectedMatch(choice);
                    System.out.println(selectedProfile);

                    runMessenger();
                }
                // end of messenger interface, back to show matchList
            }
        }
        catch (IllegalArgumentException e) {
        }

        // exit to main menu
        if ("X".equals(input)) {
            userInterfaceStatus = UserInterfaceStatus.MAIN_MENU;
        }
        // browse pages
        matchList.flipPage(input);
    }


    // user chat with bot
    // bot read stored messages from txt file
    // store conversation as value in String map
    public void runMessenger() {
        StringBuilder stringBuilder = new StringBuilder();

        if (chatMap.containsKey(selectedProfile)) {
            stringBuilder = chatMap.get(selectedProfile);
            // prints previous message history
            System.out.println(stringBuilder);
        }

        // store current date
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String currentDate = "\n" + dtf.format(LocalDate.now()) + "\n";
        System.out.println(currentDate);
        stringBuilder.append(currentDate);

        Helper.printColor("[Enter message or bye to exit chat]\n\n", Ansi.Color.BLUE);

        Scanner scanner = new Scanner(System.in);
        String input = "";

        while (!input.toUpperCase().contains("BYE")) {

            // user input time stamp
            dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
            String userMessageTime = dtf.format(LocalTime.now()) + "  ";
            System.out.print(userMessageTime);

            // user message
            Helper.printColor("You: ", Ansi.Color.GREEN);
            input = scanner.nextLine();
            String userMessage = Helper.colorText("You: ", Ansi.Color.GREEN) + input + "\n";

            // bot input time stamp
            String botMessageTime = dtf.format(LocalTime.now()) + "  ";
            System.out.print(botMessageTime);

            // bot message
            String botReply = Helper.colorText(selectedProfile.getName(), Ansi.Color.YELLOW) + ": " +
                    Helper.getRandomItemFromFile("messenger/follow_up_message.txt", "\n") + "\n";
            System.out.print(botReply);

            // store conversation
            stringBuilder.append(userMessageTime + userMessage + botMessageTime + botReply);
        }

        String endOfChatMessage = "\n\n       BYE!       \n"
                + "******************\n"
                + "* Done with chat *\n"
                + "******************\n\n";
        Helper.printColor(endOfChatMessage, Ansi.Color.GREEN);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // save chat history to chatMap
        chatMap.put(selectedProfile, stringBuilder);
    }



    // accessor methods
    @Override
    public UserInterfaceStatus updateUserInterfaceStatus() {
        return this.userInterfaceStatus;
    }
}

