package edu.unlv.sudo.checkers.model;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Represents a checkers piece
 */
public class Piece {

    private boolean king;
    private Team team;
    private Location location;

    @JsonCreator
    public Piece(@JsonProperty("team") final Team team,
                 @JsonProperty("location") final Location location) {
        this.team = team;
        this.location = location;

        this.king = false;
    }

    public boolean isKing() {
        return king;
    }

    public Team getTeam() {
        return team;
    }

    public Location getLocation() {
        return location;
    }

    void makeKing() {
        this.king = true;
    }

    void setLocation(final Location location) {
        this.location = location;
    }
}
