package com.langyastudio.memoryleak;

import java.util.ArrayList;
import java.util.List;

/**
 * 模拟内存泄漏
 *
 * @author shkstart
 * @create 21:20
 */
public class MemoryLeak {
    static List list = new ArrayList();

    public void oomTests() {
        Object obj = new Object();
        list.add(obj);
    }

}