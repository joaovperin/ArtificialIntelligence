/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.feevale.jpe.ai.ninepc;

import br.feevale.jpe.ai.utils.Debug;
import java.util.Arrays;

/**
 *
 * @author joaovperin
 */
public class GameState implements Cloneable {

    private final Piece[] pieces;
    private static final GameState DONE;

    static {
        final Piece[] doneArr = new Piece[9];
        for (int i = 0; i < 9; i++) {
            doneArr[i] = new Piece(i);
        }
        DONE = new GameState(doneArr);
    }

    public GameState(Piece[] pieces) {
        this.pieces = pieces;
    }

    public final boolean isDone() {
        return this.equals(DONE);
    }

    public final GameState[] getPossibleStates() {
        GameState[] states = new GameState[0];

        int emptyPosition = getEmptyPosition();
        switch (emptyPosition) {
            case 0:
                return StateFinder.zeroEmpty(this);
            case 1:
                return StateFinder.oneEmpty(this);
            case 2:
                return StateFinder.twoEmpty(this);
            case 3:
                return StateFinder.threeEmpty(this);
            case 4:
                return StateFinder.fourEmpty(this);
            case 5:
                return StateFinder.fiveEmpty(this);
            case 6:
                return StateFinder.sixEmpty(this);
            case 7:
                return StateFinder.sevenEmpty(this);
            case 8:
                return StateFinder.eightEmpty(this);
        }

        return states;
    }

    private int getEmptyPosition() {
        // Starts looking at the 1st position
        for (int i = 1; i < pieces.length; i++) {
            if (pieces[i].isEmptyPiece()) {
                return i;
            }
        }
        // if not found, it's the first position
        return 0;
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

    public final Piece get(int i) {
        return this.pieces[i];
    }

    public final GameState swap(int i, int j) {
        return this.set(i, get(j).value).setEmpty(j);
    }

    public final GameState set(int i, int value) {
        this.pieces[i] = new Piece(value);
        return this;
    }

    public final GameState setEmpty(int i) {
        return this.set(i, 0);
    }

    @Override
    public final GameState clone() {
        final Piece[] cloneArr = new Piece[9];
        for (int i = 0; i < 9; i++) {
            cloneArr[i] = new Piece(this.pieces[i].value);
        }
        return new GameState(cloneArr);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Arrays.deepHashCode(this.pieces);
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
        final GameState other = (GameState) obj;
        return Arrays.deepEquals(this.pieces, other.pieces);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // Iterates
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                final int pos = (3 * i) + j;
                sb.append(pieces[pos].stringValue());
            }
            // new line
            sb.append('\n');
        }
        return sb.toString();
    }
}
