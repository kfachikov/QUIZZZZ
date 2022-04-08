package server.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class MockRandom extends Random {

    public Queue<Object> returnValues = new LinkedList<>();
    public ArrayList<String> calledMethods = new ArrayList<>();
    public ArrayList<Object> params = new ArrayList<>();

    @Override
    public int nextInt() {
        calledMethods.add("nextInt");
        return (int) returnValues.poll();
    }

    @Override
    public int nextInt(int bound) {
        calledMethods.add("nextInt");
        params.add(bound);
        int val = ((int) returnValues.poll()) % bound;
        if (val < 0) {
            val += bound;
        }
        return val;
    }

    @Override
    public long nextLong() {
        calledMethods.add("nextLong");
        return (long) returnValues.poll();
    }
}
