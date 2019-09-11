/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.feevale.jpe.ai.utils;

import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A helper class to do things when debugging
 *
 * @author joaovperin
 */
public class Debug {

    /** A simple way to check if you want debug level */
    public static boolean ON = false;

    /**
     * Print a string message
     *
     * @param msg
     */
    public static void print(String msg) {
        exec(() -> System.out.print(msg));
    }

    /**
     * Print a string message with a newline at the end
     *
     * @param msg
     */
    public static void println(String msg) {
        exec(() -> System.out.println(msg));
    }

    /**
     * Print a formated messsage
     *
     * @param format
     * @param args
     */
    public static void printf(String format, Object... args) {
        exec(() -> System.out.printf(format, args));
    }

    /**
     * Execute a runnable if (and just if) the DEBUG flag is ON.
     *
     * @param runnable
     */
    public static void exec(Runnable runnable) {
        if (ON) {
            runnable.run();
        }
    }

    /**
     * Execute a callable if (and just if) the DEBUG flag is ON.
     *
     * @param runnable
     */
    public static <T> Optional<T> call(Callable<T> callable) {
        if (ON) {
            try {
                return Optional.ofNullable(callable.call());
            } catch (Exception ex) {
                Logger.getLogger(Debug.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        // Fallback to an empty Optional
        return Optional.empty();
    }

}
