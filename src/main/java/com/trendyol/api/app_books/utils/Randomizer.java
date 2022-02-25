package com.trendyol.api.app_books.utils;

import com.trendyol.api.app_books.exception.RandomException;

/**
 * Here is a small chance that any test fails and return 500 Error.
 * The API might return an HTTP 500 Internal Server Error. This is never intended,
 * and a test that encounters it should fail appropriately
 */
public class Randomizer {
    private Randomizer() {
    }

    private static int MIN = 0;
    private static int MAX = 100;
    private static int CHANCE_TO_SUCCESS = 95;

    public static void shuffle() {
        final int c = randomize();

        if (c > CHANCE_TO_SUCCESS) {
            throw new RandomException();
        }
    }

    private static int randomize() {
        return (int) Math.floor(Math.random() * (MAX - MIN + 1) + MIN);
    }
}
