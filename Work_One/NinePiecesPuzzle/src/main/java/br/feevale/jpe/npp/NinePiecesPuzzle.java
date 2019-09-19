/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.feevale.jpe.npp;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

/**
 * Solves a numeric puzzle of nine pieces with the selected algorithm
 *
 * https://github.com/joaovperin/Graphs/blob/master/src/main/java/br/com/jpe/graphs/Main.java
 *
 * @author joaovperin
 */
public class NinePiecesPuzzle {

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
//        args = "--type=1 -p 6 1 4 7 5 2 3 0 8".split(" ");
        // Calls help if not provided any args
        if (args == null || args.length == 0) {
            showHelp();
            return;
        }
        long before = System.currentTimeMillis();
        // Runs the app
        NinePiecesPuzzle.create(args).run();
        long after = System.currentTimeMillis();
        System.out.printf("Time elapsed: %4d ms.\n", (after - before));

    }

    /**
     * Creates a instance of puzzle solver using cmd-line received args
     *
     * @param args Command line args
     * @return NinePiecesPuzzleSolver
     */
    public static final NinePiecesPuzzleSolver create(String[] args) {

        // default game type
        GameType type = GameType.TYPE_NO_INFORMATION;
        Debug.ON = false;

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
                    // Verbose mode
                    case "-v":
                    case "--verbose":
                        Debug.ON = true;
                        continue;
                    // Interactive mode
                    case "-it":
                    case "--interactive":
                        throw new InvalidParameterException("Interactive mode not implemented (yet).");
//                        return createInteractively();
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
                        System.exit(0);
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
        GameState initialState = new GameState(0,
                numbers.stream().map(e -> new Piece(Integer.parseInt(e))).collect(Collectors.
                        toList()).toArray(new Piece[]{}));

        // Search with NO information (brute force)
        if (GameType.TYPE_NO_INFORMATION.equals(type)) {
            return new NinePiecesPuzzleSolverBruteForce(initialState);
        }
        // Search with information
        if (GameType.TYPE_INFORMATION_COST.equals(type)) {
            return new NinePiecesPuzzleSolverWithCoust(initialState);
        }
        // Search with information
        if (GameType.TYPE_INFORMATION_HEURISTIC.equals(type)) {
            return new NinePiecesPuzzleSolverWithHeuristic(initialState);
        }
        throw new IllegalArgumentException("Could not find a suitable game solver! Sorry.");
    }

    /**
     * Creates a instance of puzzle solver interactively
     *
     * @return NinePiecesPuzzleSolver
     */
    private static final NinePiecesPuzzleSolver createInteractively() {
        return new NinePiecesPuzzleSolverWithCoust(GameStateSamples.sampleFive());
    }

    /**
     * Shows the help
     */
    private static void showHelp() {
        StringBuilder sb = new StringBuilder();
        sb.append("Solves a numeric puzzle of nine pieces with the selected algorithm");
        sb.append('\n').append('\n');
        sb.append("Accepted parameters:\n");
        sb.append("--verbose").append("\t\t").append("Verbose mode - echo everything").append('\n');
        sb.append("-v").append("\t\t\t").append("Alias for --verbose").append('\n');
        sb.append('\n');
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
        sb.append("java -cp NinePiecesPuzzle-1.0.jar br.feevale.jpe.npp.NinePiecesPuzzle -t=1");
        sb.append('\n');
        sb.append("java -cp NinePiecesPuzzle-1.0.jar br.feevale.jpe.npp.NinePiecesPuzzle --type=2");
        sb.append('\n');
        sb.append("java -jar NinePiecesPuzzle-1.0.jar -v -t=2 -p 6 1 4 7 5 2 3 0 8");
        sb.append('\n');
        System.out.println(sb.toString());
    }

}
