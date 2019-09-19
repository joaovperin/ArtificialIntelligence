/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.feevale.jpe.npp;

import java.security.InvalidParameterException;

/**
 * Game type
 *
 * @author joaovperin
 */
public enum GameType {

    TYPE_NO_INFORMATION(1, "Search with NO information, like a brute force"),
    TYPE_INFORMATION_COST(2, "Search with information - By cost"),
    TYPE_INFORMATION_HEURISTIC(3, "Search with information - By heuristic");

    /** Game type */
    public final int type;
    /** Description of the type */
    public final String description;

    /**
     * Enum instance constructor
     *
     * @param type
     * @param description
     */
    private GameType(int type, String description) {
        this.type = type;
        this.description = description;
    }

    /**
     * Returns an enum member from a string input
     *
     * @param input
     * @return GameType
     */
    public static final GameType fromInput(String input) {
        int intinput = Integer.parseInt(input);
        for (GameType t : values()) {
            if (t.type == intinput) {
                return t;
            }
        }
        throw new InvalidParameterException("Invalid GameType solver! Please see the help");
    }

}
