package com.langyastudio.springboot;

/**
 * 狗狗的声纹
 *
 * @author jiangjiaxiong
 * @date 2023年05月24日 13:00
 */
public class DogSay implements IAnimalSay{
    public void say() {
        System.out.println("wang wang ~");
    }
}
