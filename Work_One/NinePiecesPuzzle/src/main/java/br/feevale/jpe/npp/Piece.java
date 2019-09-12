/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.feevale.jpe.npp;

/**
 * An object class to represent a single Piece of the puzzle
 *
 * @author joaovperin
 */
public class Piece {

    /** The piece's value */
    final int value;

    /**
     * A constructor that receives the value of the piece
     *
     * @param value
     */
    public Piece(int value) {
        this.value = value;
    }

    /**
     * Returns a String representation of the piece
     *
     * @return String
     */
    public final String stringValue() {
        if (this.isEmptyPiece()) {
            return "#";
        }
        return String.valueOf(this.value);
    }

    /**
     * Create an array of pieces from a set of integers
     *
     * @param values
     * @return Piece[]
     */
    public static Piece[] createArray(int... values) {
        Piece[] arr = new Piece[values.length];
        for (int i = 0; i < values.length; i++) {
            arr[i] = new Piece(values[i]);
        }
        return arr;
    }

    /**
     * Returns true if this piece is the empty one
     *
     * @return boolean
     */
    public final boolean isEmptyPiece() {
        return this.value == 0;
    }

    /**
     * Generates an unique hashCode for this instance
     *
     * @return int
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.value;
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
        final Piece other = (Piece) obj;
        if (this.value != other.value) {
            return false;
        }
        return true;
    }

}
