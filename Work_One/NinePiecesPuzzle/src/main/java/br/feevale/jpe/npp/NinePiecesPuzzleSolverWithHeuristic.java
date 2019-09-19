/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.feevale.jpe.npp;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * A puzzle solver using some information
 *
 * @author joaovperin
 */
public class NinePiecesPuzzleSolverWithHeuristic extends AbstractNinePiecesPuzzleSolver {

    /**
     * Class constructor
     *
     * @param initialState
     */
    public NinePiecesPuzzleSolverWithHeuristic(GameState initialState) {
        super(initialState);
    }

    /**
     * Algorithm implementation - search with no information
     */
    @Override
    public void run() {
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
        // Sum the weight of every piece
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                final int pos = (3 * i) + j;
                sum += (calculateWeight(i, j, st.get(pos)) == 0 ? 0 : 1);
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

}
