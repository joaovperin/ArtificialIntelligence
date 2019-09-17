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
 * The heart of the puzzle - Search with some information
 *
 * @author joaovperin
 */
public class NinePiecesPuzzleSearchWithInformationV2 implements Runnable {

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
        long before = System.currentTimeMillis();
        new NinePiecesPuzzleSearchWithInformationV2().run();
        long after = System.currentTimeMillis();
        System.out.printf("Time elapsed: %4d ms.\n", (after - before));
    }

    /**
     * The constructor of the class
     */
    public NinePiecesPuzzleSearchWithInformationV2() {
        this.soFar = new ArrayList<>(POSSIBLE_MAX_SIZE);
    }

    /**
     * Algorithm implementation - search with no information
     */
    @Override
    public void run() {
        final GameState initialState = GameStateSamples.sampleFive();
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
            Debug.ON = false;
            Debug.println("* Processing iteration: " + ++count);
            Debug.println(current.toString());

            // Checks if finished
            if (current.isDone()) {
                System.out.println("****** Solution Found on iteration " + count + "!");
                System.out.println("****** Printing the solution graph...");
                printSolutionGraph(current);
                System.out.println("****** Number of GameState models generated: " + (count) + "!");
                break;
            }

            // Adds the new possibilities on the list
            GameState[] possibleStates = current.getPossibleStates();
            List<GameState> filteredList = new ArrayList<>();
            for (GameState st : possibleStates) {
                if (!soFar.contains(st)) {
                    filteredList.add(st);
                }
            }

            // Calculate the weight to put order on this
            filteredList.stream()
                    .peek(st -> calculateWeight(st))
                    .sorted((st1, st2) -> st1.getWeight() - st2.getWeight())
                    .forEach(st -> {
                        soFar.add(st);
                        states.add(st);
                    });

            // Checks if it does not have a solution
            if (states.isEmpty()) {
                throw new IllegalStateException("The initial state doesn't not have a solution!");
            }
            // Loops until it's solved or reached an invalid state
        } while (true);

    }

    /**
     * Calculates the weight of the solution
     *
     * @param st
     */
    private void calculateWeight(GameState st) {
        int sum = 0;
        // Sum the weight off every piece
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                final int pos = (3 * i) + j;
                sum += calculateWeight(i, j, st.get(pos));
            }
        }
        st.setWeight(sum);
    }

    /**
     * Calculates the weight of a piece'
     *
     * @param index
     * @param piece
     * @return int
     */
    private int calculateWeight(int i, int j, Piece piece) {
        int row = (piece.value - 1) / 3;
        int column = (piece.value - 1) % 3;
        return Math.abs(i - row) + Math.abs(column - j);
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
