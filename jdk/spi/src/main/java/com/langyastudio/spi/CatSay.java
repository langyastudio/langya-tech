package com.langyastudio.spi;

/**
 * 猫咪的声纹
 *
 * @author jiangjiaxiong
 * @date 2023年05月24日 13:00
 */
public class CatSay implements IAnimalSay{
    @Override
    public void say() {
        System.out.println("miao miao ~");
    }
}
