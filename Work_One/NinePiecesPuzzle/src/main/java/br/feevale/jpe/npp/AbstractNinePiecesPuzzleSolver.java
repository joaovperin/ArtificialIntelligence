/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.feevale.jpe.npp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author joaovperin
 */
public abstract class AbstractNinePiecesPuzzleSolver implements Runnable {

    /** A calc to count the number of possibilities */
    protected static final int MAX_POSSIBLE_SIZE = (9 * 8 * 7 * 6 * 5 * 4 * 3 * 2) + 1;

    /** An array with all the states tried so far */
    protected List<GameState> soFar;
    protected final GameState initialState;

    public AbstractNinePiecesPuzzleSolver(GameState initialState) {
        this.soFar = new ArrayList<>(MAX_POSSIBLE_SIZE);
        this.initialState = initialState;
    }

    /**
     * Print the decision graph that reached the result.
     *
     * @param lastNode
     */
    public final void printSolutionGraph(GameState lastNode) {
        // A list with the states
        final List<GameState> list = new ArrayList<>();
        list.add(lastNode);
        // Iterates from the last node
        GameState current = lastNode;
        while (true) {
            final int parentId = current.getParentId();
            Optional<GameState> optional = soFar.stream().filter(e -> e.getId() == parentId).findFirst();
            // If it's the end
            if (!optional.isPresent()) {
                break;
            }
            current = optional.get();
            list.add(current);
        }
        // Prints all elements
        list.stream().
                sorted((e1, e2) -> e1.getId() - e2.getId()).
                forEach(System.out::println);
        System.out.println("****** Number of useful changes to complete the puzzle: " + (list.size() - 1) + "!");
    }

}
