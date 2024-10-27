package com.example.sports;

public class Player {
    private int id;
    private String name;
    private String category;
    private int matchesPlayed;
    private int scores;

    public Player(int id, String name, String category, int matchesPlayed, int scores) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.matchesPlayed = matchesPlayed;
        this.scores = scores;
    }
}
