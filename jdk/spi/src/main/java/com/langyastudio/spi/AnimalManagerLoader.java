package com.langyastudio.spi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

/**
 * @author jiangjiaxiong
 * @date 2023年05月24日 13:00
 */
public class AnimalManagerLoader {
    private final        List<IAnimalSay>    animalSays;
    private static final AnimalManagerLoader INSTANCE = new AnimalManagerLoader();

    public static AnimalManagerLoader getInstance() {
        return INSTANCE;
    }

    private AnimalManagerLoader() {
        animalSays = load();
    }

    List<IAnimalSay> getAnimalSays() {
        return this.animalSays;
    }

    /**
     * 通过SPI加载实现类
     */
    private List<IAnimalSay> load() {
        ArrayList<IAnimalSay> animalSays = new ArrayList<>();

        Iterator<IAnimalSay> iterator = ServiceLoader.load(IAnimalSay.class).iterator();
        while (iterator.hasNext()) {
            animalSays.add(iterator.next());
        }

        return animalSays;
    }
}