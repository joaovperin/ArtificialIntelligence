/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.feevale.jpe.ai.ninepc;

import br.feevale.jpe.ai.utils.Debug;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author joaovperin
 */
public class NinePiecesPuzzleV2 implements Runnable {

    public static void main(String[] args) {
        new NinePiecesPuzzleV2().run();
    }

    @Override
    public void run() {
        final long size = (9 * 8 * 7 * 6 * 5 * 4 * 3 * 2) + 1;
        List<GameState> states = new ArrayList<>(9);

        final var initialState = GameState.sampleTwo();
        System.out.println("INITIAL STATE:");
        initialState.print();

    }

//    private class Game {}
    private static class GameState {

        private static GameState sampleOne() {
            return new GameState(Piece.createArray(
                    0, 1, 2,
                    3, 4, 5,
                    6, 7, 8
            ));
        }

        private static GameState sampleTwo() {
            return new GameState(Piece.createArray(
                    1, 0, 4,
                    2, 5, 3,
                    8, 7, 6
            ));
        }

        private final Piece[] pieces;
        private static final Piece[] DONE;

        static {
            DONE = new Piece[9];
            for (int i = 0; i < 9; i++) {
                DONE[i] = new Piece(i);
            }
        }

        public GameState(Piece[] pieces) {
            this.pieces = pieces;
        }

        public void print() {
            StringBuilder sb = new StringBuilder();
            // Iterates
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    final int pos = (3 * i) + j;
                    sb.append(pieces[pos].stringValue());
                    Debug.printf("J=%d, I=%d, P=%d\n", j, i, pos);
                }
                // new line
                sb.append('\n');
            }
            System.out.println(sb.toString());
        }

    }

    private static class Piece {

        final int value;

        public Piece(int value) {
            this.value = value;
        }

        private String stringValue() {
            if (this.value == 0) {
                return "#";
            }
            return String.valueOf(this.value);
        }

        public static Piece[] createArray(int... values) {
            Piece[] arr = new Piece[values.length];
            for (int i = 0; i < values.length; i++) {
                arr[i] = new Piece(values[i]);
            }
            return arr;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 53 * hash + this.value;
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Piece other = (Piece) obj;
            if (this.value != other.value) {
                return false;
            }
            return true;
        }

    }
}
