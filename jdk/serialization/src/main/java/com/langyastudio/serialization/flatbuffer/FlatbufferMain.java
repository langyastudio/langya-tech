package com.langyastudio.serialization.flatbuffer;

import com.google.flatbuffers.FlatBufferBuilder;
import com.langyastudio.serialization.flatbuffer.monster.*;

import java.nio.ByteBuffer;

/**
 * {类简要说明}
 * <p>
 * {类详细说明}
 *
 * @author langyastudio
 * @version 1.0.0
 * @since 2023-05-08 23:00
 */
public class FlatbufferMain {
    public static void main(String[] args) {
        //使用FlatBufferBuilder 完成对象序列化
        FlatBufferBuilder builder = new FlatBufferBuilder(1024);

        int nameOffset = builder.createString("Orc");

        //返回该String的偏移地址
        int   weaponOneNameOffset = builder.createString("Sword");
        short weaponOneDamage     = 3;
        int   weaponTwoNameOffset = builder.createString("Axe");
        short weaponTwoDamage     = 5;

        // 使用createWeapon创建Weapon对象，并返回该对象的偏移地址
        int swordOffset = Weapon.createWeapon(builder, weaponOneNameOffset, weaponOneDamage);
        int axeOffset   = Weapon.createWeapon(builder, weaponTwoNameOffset, weaponTwoDamage);

        // Place the two weapons into an array, and pass it to the `createWeaponsVector()` method to
        // create a FlatBuffer vector.
        int[] weaponArr = new int[2];
        weaponArr[0] = swordOffset;
        weaponArr[1] = axeOffset;
        // Pass the `weaps` array into the `createWeaponsVector()` method to create a FlatBuffer vector.
        int weaponsOffset = Monster.createWeaponsVector(builder, weaponArr);

        // 创建一个Vector对象，并且返回它的偏移地址
        byte[] treasure  = {0, 1, 13, 12, 4, 5, 6, 7, 8, 9};
        int    invOffset = Monster.createInventoryVector(builder, treasure);

        //-----------------------------------------------------------------------------------------------
        // 创建对象
        // startMonster声明开始创建Monster对象，使用endMonster声明完成Monster对象
        //-----------------------------------------------------------------------------------------------
        Monster.startMonster(builder);
        Monster.addPos(builder, Vec3.createVec3(builder, 1.0f, 2.0f, 3.0f));
        Monster.addHp(builder, (short) 300);
        Monster.addName(builder, nameOffset);
        Monster.addInventory(builder, invOffset);
        Monster.addColor(builder, Color.Red);
        Monster.addWeapons(builder, weaponsOffset);

        Monster.addEquippedType(builder, Equipment.Weapon);
        Monster.addEquipped(builder, axeOffset);
        int orc = Monster.endMonster(builder);

        //-----------------------------------------------------------------------------------------------
        // 生成二进制文件
        //-----------------------------------------------------------------------------------------------
        // 调用finish方法完成Monster对象
        // You could also call builder.finish(orc)
        Monster.finishMonsterBuffer(builder, orc);

        // 生成二进制文件
        byte[] buf = builder.sizedByteArray();

        //-----------------------------------------------------------------------------------------------
        // 读取二进制文件
        //-----------------------------------------------------------------------------------------------
        //模拟从获取到二进制数据 进行反序列化对象
        ByteBuffer buffer = ByteBuffer.wrap(buf);

        //根据该二进制数据列生成Monster对象
        Monster monster = Monster.getRootAsMonster(buffer);

        Vec3  pos = monster.pos();
        float x   = pos.x();
        float y   = pos.y();
        float z   = pos.z();
        System.out.println("X: " + x + "  Y: " + y + "  Z: " + z);

        short mana = monster.mana();
        System.out.println(mana);
        short hp = monster.hp();
        System.out.println(hp);
        String resultName = monster.name();
        System.out.println(resultName);

        int invLength = monster.inventoryLength();
        int thirdItem = monster.inventory(invLength-1);
        System.out.println(thirdItem);

        System.out.println("color: " + monster.color());

        int    weaponsLength      = monster.weaponsLength();
        String secondWeaponName   = monster.weapons(1).name();
        short  secondWeaponDamage = monster.weapons(1).damage();
        System.out.println("weaponsLength: " + weaponsLength + "  secondWeaponName: " + secondWeaponName + "  " +
                                   "secondWeaponDamage: " + secondWeaponDamage);

        int unionType = monster.equippedType();
        if (unionType == Equipment.Weapon) {
            // Requires explicit cast
            Weapon weapon = (Weapon) monster.equipped(new Weapon());

            // to `Weapon`.
            String weaponName   = weapon.name();    // "Axe"
            short  weaponDamage = weapon.damage(); // 5
            System.out.println("weaponName: " + weaponName + "  weaponDamage: " + weaponDamage);
        }
    }
}