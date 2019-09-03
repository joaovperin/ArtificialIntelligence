/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.feevale.jpe.ai.ninepc;

/**
 *
 * @author joaovperin
 */
public class StateFinder {

    // x 1 2
    // 3 4 5
    // 6 7 8
    public static final GameState[] zeroEmpty(GameState sample) {
        return new GameState[]{
            sample.clone().swap(0, 1),
            sample.clone().swap(0, 3)
        };
    }

    // 0 x 2
    // 3 4 5
    // 6 7 8
    public static final GameState[] oneEmpty(GameState sample) {
        return new GameState[]{
            sample.clone().swap(1, 0),
            sample.clone().swap(1, 2),
            sample.clone().swap(1, 4)
        };
    }

    // 0 1 x
    // 3 4 5
    // 6 7 8
    public static final GameState[] twoEmpty(GameState sample) {
        return new GameState[]{
            sample.clone().swap(2, 1),
            sample.clone().swap(2, 5)
        };
    }

    // 0 1 2
    // x 4 5
    // 6 7 8
    public static final GameState[] threeEmpty(GameState sample) {
        return new GameState[]{
            sample.clone().swap(3, 0),
            sample.clone().swap(3, 4),
            sample.clone().swap(3, 6)
        };
    }

    // 0 1 2
    // 3 x 5
    // 6 7 8
    public static final GameState[] fourEmpty(GameState sample) {
        return new GameState[]{
            sample.clone().swap(4, 1),
            sample.clone().swap(4, 7),
            sample.clone().swap(4, 3),
            sample.clone().swap(4, 5)
        };
    }

    // 0 1 2
    // 3 4 x
    // 6 7 8
    public static final GameState[] fiveEmpty(GameState sample) {
        return new GameState[]{
            sample.clone().swap(5, 3),
            sample.clone().swap(5, 8),
            sample.clone().swap(5, 4)
        };
    }

    // 0 1 2
    // 3 4 5
    // x 7 8
    public static final GameState[] sixEmpty(GameState sample) {
        return new GameState[]{
            sample.clone().swap(6, 3),
            sample.clone().swap(6, 7)
        };
    }

    // 0 1 2
    // 3 4 5
    // 6 x 8
    public static final GameState[] sevenEmpty(GameState sample) {
        return new GameState[]{
            sample.clone().swap(7, 4),
            sample.clone().swap(7, 6),
            sample.clone().swap(7, 8)
        };
    }

    // 0 1 2
    // 3 4 5
    // 6 7 x
    public static final GameState[] eightEmpty(GameState sample) {
        return new GameState[]{
            sample.clone().swap(8, 7),
            sample.clone().swap(8, 5)
        };
    }

}
