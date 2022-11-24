package org.example;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

public class Main {
    public static void main(String[] args) {
        Clock clock = Clock.fixed(Instant.EPOCH, ZoneOffset.UTC);

    }
}