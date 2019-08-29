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
public class BinaryTreeVertex {

    private static final String SEPARATOR = " . ";

    private static int count = 0;

    private final BinaryTreeVertex parentVertex;
    private final int depth;

    private BinaryTreeVertex leftChild;
    private BinaryTreeVertex rightChild;

    private final String value;

    public BinaryTreeVertex() {
        this(null, String.valueOf(count++));
    }

    public BinaryTreeVertex(BinaryTreeVertex parent) {
        this(parent, String.valueOf(count++));
    }

    public BinaryTreeVertex(String value) {
        this(null, value);
    }

    public BinaryTreeVertex(BinaryTreeVertex parentVertex, String value) {
        this.parentVertex = parentVertex;
        this.value = value;
        this.depth = (this.parentVertex == null) ? 0 : this.parentVertex.depth + 1;
    }

    public BinaryTreeVertex createLeftChild() {
        this.leftChild = new BinaryTreeVertex(this);
        return leftChild;
    }

    public BinaryTreeVertex createLeftChild(String value) {
        this.leftChild = new BinaryTreeVertex(this, value);
        return leftChild;
    }

    public BinaryTreeVertex createRightChild() {
        this.rightChild = new BinaryTreeVertex(this);
        return rightChild;
    }

    public BinaryTreeVertex createRightChild(String value) {
        this.rightChild = new BinaryTreeVertex(this, value);
        return rightChild;
    }

    public String printChildren() {
        var sb = new StringBuilder();

        Stack<BinaryTreeVertex> s = new Stack<>();
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

        Stack<BinaryTreeVertex> s = new Stack<>();
        s.add(this);

        var r = this;
        int d = r.depth;

        while (r.leftChild != null) {
            s.add(r.leftChild);
            r = r.leftChild;
        }

        while (!s.empty()) {
            r = s.pop();
            sb.append("L").append(r.depth);
            // Checks depth changed to print a new line
            if (d != r.depth) {
                d = r.depth;
                sb.append('\n');
            }

            while (r.rightChild != null) {
                sb.append("R").append(r.depth);
                // Checks depth changed to print a new line
                if (d != r.depth) {
                    d = r.depth;
                    sb.append('\n');
                }
                r = r.rightChild;
            }
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        var sb = new StringBuilder(this.value);

        BinaryTreeVertex r = this;
        while (r.parentVertex != null) {
            r = r.parentVertex;
            sb.append(SEPARATOR).append(r.value);
        }

        return sb.toString();
    }

}
