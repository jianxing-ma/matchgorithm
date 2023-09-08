package com.matchgorithm.app.match_list;

import com.matchgorithm.profile.Profile;
import com.matchgorithm.view.Helper;
import org.fusesource.jansi.Ansi;

import java.util.List;

public class MatchList {
    /*
     * Basic functionalities:
     *      - store a list of matched profiles
     *      - allow user to browse through the list / select the profile
     *      - enter a conversation interface once user selects the profile
     *      - allow user to exit the chat box and back to the list
     */

    // constant field
    public static final int MATCHES_PER_PAGE = 10;  // show 10 matches per page

    // instance variables
    private final List<Profile> matches;

    private int currentPage = 0;    // set default page to first page


    // constructor
    public MatchList(List<Profile> matches) {
        this.matches = matches;
    }


    /*
     * business methods
     */

    // view method: show the matches on the current page
    void showMatchList() {

        int totalPages = matches.size() / MATCHES_PER_PAGE;

        int matchesShown =
                currentPage == totalPages ?
                        matches.size() % MATCHES_PER_PAGE : 10;

        Helper.printColor("\n**************************************************************************\n",
                Ansi.Color.MAGENTA);

        for (int i = 0; i < matchesShown; i++) {
            Profile profile = matches.get(i + currentPage * 10);

            Helper.printColor(i + ". ", Ansi.Color.GREEN);
            Helper.printColor(profile.getName(), Ansi.Color.MAGENTA);
            System.out.printf(" (id_%s): age %s, %s miles, %s\n",
                    profile.getUniqueId(), profile.getAge(),
                    profile.getDistance(), profile.getCareer());
        }

        Helper.printColor("\n**************************************************************************\n",
                Ansi.Color.MAGENTA);

        Helper.printColor("Page " + (getCurrentPage() + 1) + "/" + (totalPages+1) + "\n", Ansi.Color.MAGENTA);

        showMatchListInterfaceOptions();
    }

    // view method: show user options in the "Matches" interface
    void showMatchListInterfaceOptions() {
        String options = "               OPTIONS\n "
                + "-------------------------------------\n"
                + "Enter id number to view and chat (0-9)\n"
                + "   Previous page(P) | Next Page(N)\n"
                + "               Exit(X)\n"
                + "-------------------------------------\n"
                + "                Enter: ";
        Helper.printColor(options, Ansi.Color.GREEN);
    }

    // model method: allow user to browse through pages of the MatchList
    void flipPage(String input) {
        Ansi ansi = new Ansi();

        switch (input.toUpperCase()) {
            case "P":   // Previous page
                if (currentPage > 0) {
                    currentPage--;
                    showMatchList();
                }
                else {
                    Helper.printColor("\nThis is the first page.\n", Ansi.Color.RED);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case "N":   // Next page
                if ((currentPage + 1) * MATCHES_PER_PAGE < matches.size()) {
                    currentPage++;
                    showMatchList();
                }
                else {
                    System.out.println(ansi.fgRed().bold().a("\nThis is the last page.\n").reset());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }

    // model method: returns the selected profile
    Profile selectedMatch(int choice) {

        int indexOfChoice = choice + getCurrentPage() * MATCHES_PER_PAGE;
        return matches.get(indexOfChoice);
    }

    // accessor method
    public int getCurrentPage() {
        return currentPage;
    }
}