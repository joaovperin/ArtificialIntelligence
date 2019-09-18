/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.feevale.jpe.npp;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author joaovperin
 */
public class NinePiecesPuzzleSolverBruteForce extends AbstractNinePiecesPuzzleSolver {

    public NinePiecesPuzzleSolverBruteForce(GameState initialState) {
        super(initialState);
    }

    /**
     * Algorithm implementation - search with no information (almost a brute
     * force)
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
            for (GameState st : possibleStates) {
                if (!soFar.contains(st)) {
                    soFar.add(st);
                    states.add(st);
                }
            }

            // Checks if it does not have a solution
            if (states.isEmpty()) {
                throw new IllegalStateException("The initial state doesn't not have a solution!");
            }
            // Loops until it's solved or reached an invalid state
        } while (true);

    }

}
