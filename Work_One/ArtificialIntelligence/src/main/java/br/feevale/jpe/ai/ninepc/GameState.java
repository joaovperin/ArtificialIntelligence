/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.feevale.jpe.ai.ninepc;

import java.util.Arrays;

/**
 * A class of objects that represents an Game State
 *
 * @author joaovperin
 */
public class GameState {

    /** A static instance counter to let me count the achieved states */
    private static int idCounter = 1;

    /** ID of the state who created this one */
    private final int parentId;
    /** ID of the current state */
    private final int id;
    /** The pieces organization */
    private final Piece[] pieces;

    /** A copy of the state of a done game */
    private static final GameState DONE_STATE;

    /**
     * Static constructor to create a sample of the done state
     */
    static {
        final Piece[] doneArr = new Piece[9];
        for (int i = 0; i < doneArr.length; i++) {
            doneArr[i] = new Piece(i);
        }
        DONE_STATE = new GameState(0, doneArr);
    }

    /**
     * The game state constructor
     *
     * @param parentId
     * @param pieces
     */
    public GameState(int parentId, Piece[] pieces) {
        this.id = idCounter++;
        this.parentId = parentId;
        this.pieces = pieces;
    }

    /**
     * Returns true if it's done
     *
     * @return boolean
     */
    public final boolean isDone() {
        return this.equals(DONE_STATE);
    }

    /**
     * Returns all the possible state that can be achieved from this
     *
     * @return GameState[]
     */
    public final GameState[] getPossibleStates() {
        int emptyPosition = findEmptyPosition();
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
        // Illegal state! Should never happen, but just in case...
        throw new IllegalStateException("Could not find the empty space on the puzzle!");
    }

    /**
     * Find the index of the empty position on the puzzle state
     *
     * @return int
     */
    private int findEmptyPosition() {
        // Starts looking at the 1st position
        for (int i = 1; i < pieces.length; i++) {
            if (pieces[i].isEmptyPiece()) {
                return i;
            }
        }
        // if not found, it's the first position
        return 0;
    }

    /**
     * Print the puzzle
     */
    public final void print() {
        StringBuilder sb = new StringBuilder();
        // Iterates over the puzzle state
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                final int pos = (3 * i) + j;
                sb.append(pieces[pos].stringValue());
            }
            // New line
            sb.append('\n');
        }
        System.out.println(sb.toString());
    }

    /**
     * Returns the piece on the selected index
     *
     * @param index
     * @return Piece
     */
    public final Piece get(int index) {
        return this.pieces[index];
    }

    /**
     * Swap two pieces, from i0 to i1 (and vice-versa)
     *
     * @param index Index to set as empty
     * @param emptyPieceIndex Index of the empty position
     * @return GameState
     */
    public final GameState swap(int index, int emptyPieceIndex) {
        return this.set(index, get(emptyPieceIndex).value).setEmpty(emptyPieceIndex);
    }

    /**
     * Set a value on the selected index
     *
     * @param index
     * @param value
     * @return GameState
     */
    public final GameState set(int index, int value) {
        this.pieces[index] = new Piece(value);
        return this;
    }

    /**
     * Set the piece on the selected index as empty
     *
     * @param index
     * @return GameState
     */
    public final GameState setEmpty(int index) {
        return this.set(index, 0);
    }

    /**
     * Create a child state as an exact copy of this, except of the parent id.
     * ...the parent ID will be THIS instance ID.
     *
     * It's obviously, because we're creating a child, so this is the parent
     * lol.
     *
     * @return GameState
     */
    public final GameState createChild() {
        final Piece[] childArray = new Piece[9];
        for (int i = 0; i < childArray.length; i++) {
            childArray[i] = new Piece(this.pieces[i].value);
        }
        return new GameState(id, childArray);
    }

    /**
     * Returns the state parent's ID
     *
     * @return int
     */
    public final int getParentId() {
        return parentId;
    }

    /**
     * Returns the state id
     *
     * @return int
     */
    public final int getId() {
        return id;
    }

    /**
     * Generates an unique hashCode for this instance
     *
     * @return int
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Arrays.deepHashCode(this.pieces);
        return hash;
    }

    /**
     * Returns true if the object is equal to this instance
     *
     * @param obj
     * @return boolean
     */
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

    /**
     * Create a String representation of this object
     *
     * @return String
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(20).append("#").append(id)
                .append("\t(").append(parentId).append(")\n");
        // Iterates over the puzzle state
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                final int pos = (3 * i) + j;
                sb.append(pieces[pos].stringValue());
            }
            // New line
            sb.append('\n');
        }
        return sb.toString();
    }
}
