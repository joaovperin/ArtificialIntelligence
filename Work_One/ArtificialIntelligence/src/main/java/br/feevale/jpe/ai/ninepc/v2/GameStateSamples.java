/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.feevale.jpe.ai.ninepc.v2;


/**
 *
 * @author joaovperin
 */
public class GameStateSamples {

    // 0 iterations
    public static GameState sampleOne() {
        return new GameState(Piece.createArray(
                0, 1, 2,
                3, 4, 5,
                6, 7, 8
        ));
    }

    // 1 iteration
    public static GameState sampleTwo() {
        return new GameState(Piece.createArray(
                1, 0, 2,
                3, 4, 5,
                6, 7, 8
        ));
    }

    // 1 iteration
    public static GameState sampleThree() {
        return new GameState(Piece.createArray(
                1, 6, 8,
                0, 3, 5,
                4, 7, 2
        ));
    }

    // 120k+ iterations
    public static GameState sampleFour() {
        return new GameState(Piece.createArray(
                1, 0, 4,
                2, 5, 3,
                8, 7, 6
        ));
    }

}
