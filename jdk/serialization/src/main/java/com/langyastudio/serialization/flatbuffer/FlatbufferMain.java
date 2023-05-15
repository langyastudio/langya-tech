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

        //返回该String的偏移地址
        int   weaponOneName   = builder.createString("Sword");
        short weaponOneDamage = 3;
        int   weaponTwoName   = builder.createString("Axe");
        short weaponTwoDamage = 5;

        // 使用createWeapon创建Weapon对象，并返回该对象的偏移地址
        int sword = Weapon.createWeapon(builder, weaponOneName, weaponOneDamage);
        int axe   = Weapon.createWeapon(builder, weaponTwoName, weaponTwoDamage);

        // Serialize a name for our monster, called "Orc".
        int name = builder.createString("Orc");

        // 创建一个Vector对象，并且返回它的偏移地址
        byte[] treasure = {0, 1, 13, 12, 4, 5, 6, 7, 8, 9};
        int    inv      = Monster.createInventoryVector(builder, treasure);

        // Place the two weapons into an array, and pass it to the `createWeaponsVector()` method to
        // create a FlatBuffer vector.
        int[] weaps = new int[2];
        weaps[0] = sword;
        weaps[1] = axe;
        // Pass the `weaps` array into the `createWeaponsVector()` method to create a FlatBuffer vector.
        int weapons = Monster.createWeaponsVector(builder, weaps);

        // startMonster声明开始创建Monster对象，使用endMonster声明完成Monster对象
        Monster.startMonster(builder);
        Monster.addPos(builder, Vec3.createVec3(builder, 1.0f, 2.0f, 3.0f));
        Monster.addName(builder, name);
        Monster.addColor(builder, Color.Red);
        Monster.addHp(builder, (short) 300);
        Monster.addInventory(builder, inv);
        Monster.addWeapons(builder, weapons);
        Monster.addEquippedType(builder, Equipment.Weapon);
        Monster.addEquipped(builder, axe);
        int orc = Monster.endMonster(builder);

        // 调用finish方法完成Monster对象
        builder.finish(orc); // You could also call `Monster.finishMonsterBuffer(builder, orc);`.

        // 生成二进制文件
        byte[] buf = builder.sizedByteArray();

        // 至此完成对象数据序列化


        //模拟从获取到二进制数据 进行反序列化对象
        ByteBuffer buffer = ByteBuffer.wrap(buf);

        //根据该二进制数据列生成Monster对象
        Monster monster = Monster.getRootAsMonster(buffer);

        short hp = monster.hp();
        System.out.println(hp);

        short mana = monster.mana();
        System.out.println(mana);
        String resultName = monster.name();
        System.out.println(resultName);

        Vec3  pos = monster.pos();
        float x   = pos.x();
        float y   = pos.y();
        float z   = pos.z();
        System.out.println("X: " + x + "  Y: " + y + "  Z: " + z);

        int invLength = monster.inventoryLength();
        int thirdItem = monster.inventory(2);
        System.out.println(thirdItem);

        int    weaponsLength      = monster.weaponsLength();
        String secondWeaponName   = monster.weapons(1).name();
        short  secondWeaponDamage = monster.weapons(1).damage();
        System.out.println("weaponsLength: " + weaponsLength + "  secondWeaponName: " + secondWeaponName + "  " +
                                   "secondWeaponDamage: " + secondWeaponDamage);
        int unionType = monster.equippedType();
        if (unionType == Equipment.Weapon) {
            Weapon weapon = (Weapon) monster.equipped(new Weapon()); // Requires explicit cast
            // to `Weapon`.
            String weaponName   = weapon.name();    // "Axe"
            short  weaponDamage = weapon.damage(); // 5
            System.out.println("weaponName: " + weaponName + "  weaponDamage: " + weaponDamage);
        }
    }
}