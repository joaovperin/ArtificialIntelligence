/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.feevale.jpe.npp;

import java.security.InvalidParameterException;

/**
 * Game type
 */
public enum GameType {

    TYPE_NO_INFORMATION(1, "Search with NO information, like a brute force"),
    TYPE_INFORMATION(2, "Search with some information");

    public final int type;
    public final String description;

    private GameType(int type, String description) {
        this.type = type;
        this.description = description;
    }

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
