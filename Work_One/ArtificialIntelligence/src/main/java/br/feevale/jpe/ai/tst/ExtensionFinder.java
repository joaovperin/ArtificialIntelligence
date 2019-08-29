/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.feevale.jpe.ai.tst;

import br.feevale.jpe.ai.core.BinaryTreeVertex;

/**
 *
 * @author joaovperin
 */
public class ExtensionFinder implements Runnable {

    public static void main(String[] args) {
        new ExtensionFinder().run();
    }

    @Override
    public void run() {
        BinaryTreeVertex root = createTree();

        System.out.println(root.printChildrenWithDepth());

    }

    private BinaryTreeVertex createTree() {
        BinaryTreeVertex root = new BinaryTreeVertex("0");

        BinaryTreeVertex v1 = root.createLeftChild("1");
        BinaryTreeVertex v1_1 = v1.createLeftChild("1_1");
        BinaryTreeVertex v1_2 = v1.createRightChild("1_2");
        BinaryTreeVertex v1_2_1 = v1_2.createLeftChild("1_2_1");
        BinaryTreeVertex v1_1_1 = v1_1.createLeftChild("1_1_1");

        BinaryTreeVertex v2 = root.createRightChild("2");
        BinaryTreeVertex v2_1 = v2.createRightChild("2_2");

//        System.out.println(v2_1);

        return root;
    }

}
