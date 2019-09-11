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
public class Piece {

    final int value;

    public Piece(int value) {
        this.value = value;
    }

    public final String stringValue() {
        if (this.isEmptyPiece()) {
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

    public final boolean isEmptyPiece() {
        return this.value == 0;
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
