package com.matchgorithm.app;

public enum UserInterfaceStatus {
    MAIN_MENU("Main Menu"),
    SWIPE("Swipe Interface"),
    MATCH_LIST("Match List Interface"),
    MESSENGER("Messenger Interface"),
    EXIT("Exit");

    private final String input;

    UserInterfaceStatus(String input) {
        this.input = input;
    }

    public String getInput() {
        return input;
    }

    public String toString() {
        return getInput();
    }
}