package server.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class RandomUtils {
    public List<Long> distinctList(Random random, long count, long upper) {
        List<Long> result = new ArrayList<>();
        if (upper < count) {
            throw new IllegalArgumentException();
        }
        for (long i = 0; i < upper; i++) {
            result.add(i);
        }
        Collections.shuffle(result, random);
        return result.stream().limit(count).collect(Collectors.toList());
    }
}
