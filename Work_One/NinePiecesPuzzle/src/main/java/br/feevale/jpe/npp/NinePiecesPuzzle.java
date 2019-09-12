/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.feevale.jpe.npp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * The heart of the puzzle
 *
 * @author joaovperin
 */
public class NinePiecesPuzzle {

    /** A calc to count the number of possibilities */
    private static final int POSSIBLE_MAX_SIZE = (9 * 8 * 7 * 6 * 5 * 4 * 3 * 2) + 1;

    /** An array with all the states tried so far */
    private final List<GameState> soFar;

    /**
     * Main application's entry point
     *
     * @param args
     */
    public static void main(String[] args) {
        // Runs the search with no information
        new NinePiecesPuzzle().searchWithNoInformation();
    }

    /**
     * The constructor of the class
     */
    public NinePiecesPuzzle() {
        this.soFar = new ArrayList<>(POSSIBLE_MAX_SIZE);
    }

    /**
     * Algorithm implementation - search with no information
     */
    public void searchWithNoInformation() {
        final var initialState = GameStateSamples.sampleFive();
        final Queue<GameState> states = new ConcurrentLinkedQueue<>();
        states.add(initialState);
        soFar.add(initialState);

        System.out.println("****** INITIAL STATE:");
        initialState.print();

        // A simple counter to track how many iterations we need to reach the state
        int count = 0;

        GameState current;
        do {
            // Get's one state from the queue
            current = states.poll();
            // HEY! IF YOU WANT TO SEE WHAT'S HAPPENNING,
            //...just SET DEBUG TO TRUE 
            Debug.ON = true;
            Debug.println("* Processing iteration: " + ++count);
            Debug.println(current.toString());

            // Checks if finished
            if (current.isDone()) {
                System.out.println("****** Solution Found on iteration " + count + "!");
                System.out.println("****** Printing the solution graph...");
                printSolutionGraph(current);
                break;
            }

            // Adds the new possibilities on the list
            GameState[] possibleStates = current.getPossibleStates();
            for (var st : possibleStates) {
                if (!soFar.contains(st)) {
                    soFar.add(st);
                    states.add(st);
                }
            }

            // Checks if it does not have a solution
            if (states.isEmpty()) {
                throw new IllegalStateException("The initial state doest not have a solution!");
            }
            // Loops until it's solved or reached an invalid state
        } while (true);

    }

    /**
     * Print the decision graph that reached the result.
     *
     * @param lastNode
     */
    private void printSolutionGraph(GameState lastNode) {
        // A list with the states
        final List<GameState> list = new ArrayList<>();
        list.add(lastNode);
        // Iterates from the last node
        GameState current = lastNode;
        while (true) {
            final int parentId = current.getParentId();
            Optional<GameState> optional = soFar.stream().filter(e -> e.getId() == parentId).findFirst();
            // If it's the end
            if (optional.isEmpty()) {
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
