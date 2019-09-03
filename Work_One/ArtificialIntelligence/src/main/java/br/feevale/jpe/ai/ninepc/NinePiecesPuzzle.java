/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.feevale.jpe.ai.ninepc;

import br.feevale.jpe.ai.utils.Debug;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author joaovperin
 */
public class NinePiecesPuzzle implements Runnable {

    private static final int POSSIBLE_MAX_SIZE = (9 * 8 * 7 * 6 * 5 * 4 * 3 * 2) + 1;
    private final List<GameState> soFar;

    public static void main(String[] args) {
        new NinePiecesPuzzle().run();
    }

    public NinePiecesPuzzle() {
        this.soFar = new ArrayList<>(POSSIBLE_MAX_SIZE);
    }

    @Override
    public void run() {
        final var initialState = GameStateSamples.sampleThree();
        final Queue<GameState> states = new ConcurrentLinkedQueue<>();
        states.add(initialState);

        System.out.println("INITIAL STATE:");
        initialState.print();

        long count = 0;

        GameState current;
        do {
            // Get's one state from the queue
            current = states.poll();
//            Debug.println("Processing state: " + ++count);
//            Debug.println(current.toString());

            // Checks if finished
            if (current.isDone()) {
                System.out.println("Solution Found on state " + count + "!");
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

}
