/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.feevale.jpe.ai.ninepc.v1;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author joaovperin
 */
public class NinePiecesPuzzleV1 implements Runnable {

    public static void main(String[] args) {
        new NinePiecesPuzzleV1().run();
    }

    @Override
    public void run() {
        var g = new Game();
        g.randomize();
        g.print();
    }

    private class Game {

        private final List<Piece> pieces;

        public Game() {
            this.pieces = new ArrayList<>();
        }

        public void randomize() {
            pieces.clear();
            int num = 0;
            pieces.add(new Piece(++num, 0, 0));
            pieces.add(new Piece(++num, 0, 0));
            pieces.add(new Piece(++num, 0, 0));
            pieces.add(new Piece(++num, 0, 0));
            pieces.add(new Piece(++num, 0, 0));
            pieces.add(new Piece(++num, 0, 0));
            pieces.add(new Piece(++num, 0, 0));
            pieces.add(Piece.empty());
        }

        public void print() {
            StringBuilder sb = new StringBuilder();
            // 3 rows
            for (int i = 0; i < 3; i++) {
                // 3 columns
                for (int j = 0; j < 3; j++) {
                    // find piece and append to SB
                    sb.append(getPiece(i, j).stringValue());
                }
                // new line
                sb.append('\n');
            }
            System.out.println(sb.toString());
        }

        public void addPiece(int v, int i, int j) {
            findPiece(i, j).ifPresentOrElse((t) -> new IllegalStateException(), () -> {
                pieces.add(new Piece(v, 0, 0));
            });
        }

        private Piece getPiece(final int i, final int j) {
            return findPiece(i, j).orElseThrow(() -> new IllegalStateException());
        }

        private Optional<Piece> findPiece(final int i, final int j) {
            return pieces.stream().filter(e -> (e.x == i) && (e.y == j)).findFirst();
        }

    }

    private static class Piece {

        int x;
        int y;
        final int value;
        final int idealX;
        final int idealY;

        public Piece(int value, int idealX, int idealY) {
            this.value = value;
            this.idealX = idealX;
            this.idealY = idealY;
            this.x = 0;
            this.y = 0;
        }

        private String stringValue() {
            if (this.value == 0) {
                return "#";
            }
            return String.valueOf(this.value);
        }

        static Piece empty() {
            // 1st row, 3rd column, value zeros
            return new Piece(0, 0, 2);
        }

    }
}
