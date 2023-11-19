package org.example.impl;

import org.example.Train;

public class TrainImpl implements Train {

    private final String name;
    private final int maxPassengers;

    public TrainImpl(String name, int maxPassengers) {
        this.name = name;
        this.maxPassengers = maxPassengers;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getMaxPassengers() {
        return this.maxPassengers;
    }
}
