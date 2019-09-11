/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.feevale.jpe.ai.ninepc.v2;

import br.feevale.jpe.ai.utils.Debug;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author joaovperin
 */
public class NinePiecesPuzzle implements Runnable {

    private static final int POSSIBLE_MAX_SIZE = (9 * 8 * 7 * 6 * 5 * 4 * 3 * 2) + 1;
    private final List<GameState> soFar;
    private final GameState initialState;
    private final Queue<GameState> states;
    private final AtomicLong count;

    public static void main(String[] args) {
        NinePiecesPuzzle puzzle = new NinePiecesPuzzle(GameStateSamples.sampleThree());
        puzzle.start();
    }

    public NinePiecesPuzzle(GameState initialState) {
        this.initialState = initialState;
        this.soFar = new ArrayList<>(POSSIBLE_MAX_SIZE);
        this.states = new ConcurrentLinkedQueue<>();
        this.count = new AtomicLong(0);
        states.add(initialState);
    }

    public void start() {
        do {
            // Get's one state from the queue
            GameState current = states.poll();
            long val = count.incrementAndGet();
            Debug.println("Processing state: " + val);
//            Debug.println(current.toString());

            // Checks if finished
            if (current.isDone()) {
                System.out.println("Solution Found on state " + val + "!");
                break;
            }

            new Thread(() -> {
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
            }).start();

            // Loops until it's solved or reached an invalid state
        } while (true);

    }

    @Override
    public void run() {

//        System.out.println("INITIAL STATE:");
//        initialState.print();
    }

}
