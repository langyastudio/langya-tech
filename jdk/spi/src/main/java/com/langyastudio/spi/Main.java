package com.langyastudio.spi;

import java.util.List;

/**
 * @author jiangjiaxiong
 * @date 2023年05月24日 13:00
 */
public class Main {
    public static void main(String[] args) {
        AnimalManagerLoader animalManagerLoader = AnimalManagerLoader.getInstance();
        List<IAnimalSay>    animalSays          = animalManagerLoader.getAnimalSays();
        for (IAnimalSay animalSay : animalSays) {
            animalSay.say();
        }
    }
}
