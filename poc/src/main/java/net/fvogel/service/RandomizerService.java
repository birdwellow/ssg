package net.fvogel.service;

import org.springframework.stereotype.Service;

@Service
public class RandomizerService {

    public int getRandomInt() {
        return (int) Math.random() * 1000;
    }

    public int getRandomInt(int range) {
        return (int) (Math.random() * range);
    }

    public int getRandomInt(int lowerBound, int upperBound) {
        return (int) (Math.random() * (upperBound - lowerBound)) + lowerBound;
    }

    public <E extends Enum<E>> E getRandomEnumConstant(Class<E> enumClass) {
        E[] constants = enumClass.getEnumConstants();
        int index = getRandomInt(constants.length);
        return constants[index];
    }

}
