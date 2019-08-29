/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.feevale.jpe.ai.core;

import java.util.Stack;

/**
 * Binary Tree Vertex!
 *
 * @author joaovperin
 */
public class Vertex {

    private static final String SEPARATOR = " . ";

    private static int count = 0;

    private final Vertex parentVertex;
    private final int depth;

    private Vertex leftChild;
    private Vertex rightChild;

    private final String value;

    public Vertex() {
        this(null, String.valueOf(count++));
    }

    public Vertex(Vertex parent) {
        this(parent, String.valueOf(count++));
    }

    public Vertex(String value) {
        this(null, value);
    }

    public Vertex(Vertex parentVertex, String value) {
        this.parentVertex = parentVertex;
        this.value = value;
        this.depth = (this.parentVertex == null) ? 0 : this.parentVertex.depth + 1;
    }

    public Vertex createLeftChild() {
        this.leftChild = new Vertex(this);
        return leftChild;
    }

    public Vertex createLeftChild(String value) {
        this.leftChild = new Vertex(this, value);
        return leftChild;
    }

    public Vertex createRightChild() {
        this.rightChild = new Vertex(this);
        return rightChild;
    }

    public Vertex createRightChild(String value) {
        this.rightChild = new Vertex(this, value);
        return rightChild;
    }

    public String printChildren() {
        var sb = new StringBuilder();

        Stack<Vertex> s = new Stack<>();
        s.add(this);

        var r = this;

        while (r.leftChild != null) {
            s.add(r.leftChild);
            r = r.leftChild;
        }

        while (!s.empty()) {
            r = s.pop();
            sb.append(r.value).append(" X ");

            while (r.rightChild != null) {
                sb.append(r.rightChild.value).append(SEPARATOR);
                r = r.rightChild;
            }
        }

        return sb.toString();
    }

    public String printChildrenWithDepth() {
        var sb = new StringBuilder();

        Stack<Vertex> s = new Stack<>();
        s.add(this);

        var r = this;

        while (r.leftChild != null) {
            s.add(r.leftChild);
            r = r.leftChild;
        }

        while (!s.empty()) {
            r = s.pop();
            sb.append(r.value).append(" X ");

            while (r.rightChild != null) {
                sb.append(r.rightChild.value).append(SEPARATOR);
                r = r.rightChild;
            }
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        var sb = new StringBuilder(this.value);

        Vertex r = this;
        while (r.parentVertex != null) {
            r = r.parentVertex;
            sb.append(SEPARATOR).append(r.value);
        }

        return sb.toString();
    }

}
