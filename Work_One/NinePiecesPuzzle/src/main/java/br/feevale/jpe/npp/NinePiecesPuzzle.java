/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.feevale.jpe.npp;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

/**
 * Solves a numeric puzzle of nine pieces with the selected algorithm
 *
 * https://github.com/joaovperin/Graphs/blob/master/src/main/java/br/com/jpe/graphs/Main.java
 *
 * @author joaovperin
 */
public class NinePiecesPuzzle implements Runnable {

    /** A calc to count the number of possibilities */
    private static final int MAX_POSSIBLE_SIZE = (9 * 8 * 7 * 6 * 5 * 4 * 3 * 2) + 1;

    /**
     * Shows the help
     */
    private static void showHelp() {
        StringBuilder sb = new StringBuilder();
        sb.append("Solves a numeric puzzle of nine pieces with the selected algorithm");
        sb.append('\n').append('\n');
        sb.append("Accepted parameters:\n");
        sb.append("--interactive").append("\t\t").append("Interactive mode").append('\n');
        sb.append("-it").append("\t\t\t").append("Alias for --interactive").append('\n');
        sb.append('\n');
        sb.append("--type=number").append("\t\t").append("Type of algorithm to use for solving").append('\n');
        sb.append("-t=number").append("\t\t").append("Alias for --type").append('\n');
        sb.append('\n');
        sb.append("--puzzle").append("\t\t").append("Pass the numbers to mount the initial game state").append('\n');
        sb.append("-p").append("\t\t").append("Alias for --puzzle").append('\n');
        sb.append('\n');
        sb.append("--help").append("\t\t\t").append("Shows this help").append('\n');
        sb.append("-h").append("\t\t\t").append("Alias for --help").append('\n');
        sb.append("\nOptions:").append('\n');
        for (GameType type : GameType.values()) {
            sb.append(type.type).append(" - ").append(type.description).append('\n');
        }
        sb.append("\n").append("Examples:").append("\n");
        sb.append("java -cp NinePiecesPuzzle.jar br.feevale.jpe.npp.NinePiecesPuzzle -t=1");
        sb.append('\n');
        sb.append("java -cp NinePiecesPuzzle.jar br.feevale.jpe.npp.NinePiecesPuzzle --type=2");
        sb.append('\n');
        System.out.println(sb.toString());
    }

    /** An array with all the states tried so far */
    private final List<GameState> soFar;

    /**
     * Main application's entry point
     *
     * @param args
     */
    public static void main(String[] args) {
        /*
         * 6, 1, 4,
         * 7, 5, 2,
         * 3, 0, 8
         */
        // Test stuff
        args = "-p 6 1 4 7 5 2 3 0 8".split(" ");
        // Calls help if not provided any args
        if (args == null || args.length == 0) {
            showHelp();
            return;
        }
        // Runs the search with no information
        new NinePiecesPuzzle(args).run();
    }

    private GameType type = GameType.TYPE_INFORMATION;
    private GameState initialState;

    /**
     * The constructor of the class
     *
     * @param args Command line args
     */
    public NinePiecesPuzzle(String[] args) {
        this.soFar = new ArrayList<>(MAX_POSSIBLE_SIZE);
        boolean acceptingNumbers = false;
        final List<String> numbers = new ArrayList<>(9);
        for (String arg : args) {
            // If accepting numbers (look -p parameter bellow)
            if (acceptingNumbers) {
                if (numbers.size() < 9) {
                    numbers.add(arg);
                }
            } else {
                StringTokenizer st = new StringTokenizer(arg, "=");
                String key = st.nextToken();
                switch (key) {
                    // Interactive mode
                    case "-it":
                    case "--interactive":
                        System.out.println("Interactive mode not implemented (yet).");
                        return;
                    // Selects the type of the algorithm
                    case "-t":
                    case "--type":
                        type = GameType.fromInput(st.nextToken());
                        break;
                    // Selects the type of the algorithm
                    case "-p":
                    case "--puzzle":
                        acceptingNumbers = true;
                        continue;
                    // Shows help
                    case "-h":
                    case "--help":
                        showHelp();
                        return;
                    // Default for unrecognized args
                    default:
                        System.out.printf("Unrecognized token '%s' was ignored.", key);
                        break;
                }
            }
        }
        // Validates number of pieces
        if (numbers.size() != 9) {
            throw new InvalidParameterException("You have to provide 9 numbers.");
        }
        // Creates the initial state
        initialState = new GameState(0,
                numbers.stream().map(e -> new Piece(Integer.parseInt(e))).collect(Collectors.
                        toList()).toArray(new Piece[] {}));
    }

    /**
     * Algorithm implementation - search with no information
     */
    @Override
    public void run() {
//
//        System.out.println("GameType:" + type.name());
//        System.out.println("GameState:");
//        initialState.print();;

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
