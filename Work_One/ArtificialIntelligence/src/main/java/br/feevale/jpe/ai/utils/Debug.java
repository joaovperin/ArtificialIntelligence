/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.feevale.jpe.ai.utils;

/**
 *
 * @author joaovperin
 */
public class Debug {

    public static boolean ON = false;

    public static void print(String msg) {
        exec(() -> System.out.print(msg));
    }

    public static void println(String msg) {
        exec(() -> System.out.println(msg));
    }

    public static void printf(String fmt, Object... args) {
        exec(() -> System.out.printf(fmt, args));
    }

    private static void exec(Runnable run) {
        if (ON) {
            run.run();
        }
    }

}
