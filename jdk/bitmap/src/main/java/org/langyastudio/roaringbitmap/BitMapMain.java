package org.langyastudio.roaringbitmap;

import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.roaringbitmap.RoaringBitmap;

import java.util.Collection;

/**
 * BitMap 测试
 *
 * @author langyastudio
 * @version 1.0.0
 * @since 2023-06-06 08:00
 */
public class BitMapMain {
    public static void main(String[] args) throws RunnerException {
        // jmh 性能测试
        Options options = new OptionsBuilder()
                .include(BitSetsBenchmark.class.getSimpleName())
                .forks(1)
                .output("./logs/Benchmark.log")
                .build();
        Collection<RunResult> results = new Runner(options).run();
        System.out.println("jmh 性能测试结果：" + results);

        usingOr_thenWillGetSetsUnion();
        usingAnd_thenWillGetSetsIntersection();
        usingAndNot_thenWillGetSetsDifference();
        usingXOR_thenWillGetSetsSymmetricDifference();
    }

    /**
     * 并集 - 有一个为1
     */
    static void usingOr_thenWillGetSetsUnion() {
        RoaringBitmap expected = RoaringBitmap.bitmapOf(1, 2, 3, 4, 5, 6, 7, 8);
        RoaringBitmap A        = RoaringBitmap.bitmapOf(1, 2, 3, 4, 5);
        RoaringBitmap B        = RoaringBitmap.bitmapOf(4, 5, 6, 7, 8);

        RoaringBitmap union    = RoaringBitmap.or(A, B);
        System.out.println("expected == union:" + (expected.equals(union))) ;
    }

    /**
     * 交集 - 都为1
     */
    static void usingAnd_thenWillGetSetsIntersection() {
        RoaringBitmap expected = RoaringBitmap.bitmapOf(4, 5);
        //rangeEnd exclusive ending of range [1,2,3,4,5]
        RoaringBitmap A = RoaringBitmap.bitmapOfRange(1, 6);
        RoaringBitmap B = RoaringBitmap.bitmapOf(4, 5, 6, 7, 8);

        RoaringBitmap intersection = RoaringBitmap.and(A, B);
        System.out.println("expected == intersection:" + expected.equals(intersection));
    }

    /**
     * 差集 - 排除另一个位图的值为1的位
     */
    static void usingAndNot_thenWillGetSetsDifference() {
        RoaringBitmap expected = RoaringBitmap.bitmapOf(1, 2, 3);
        RoaringBitmap A = new RoaringBitmap();
        A.add(1L, 6L);
        RoaringBitmap B = RoaringBitmap.bitmapOf(4, 5, 6, 7, 8);

        RoaringBitmap difference = RoaringBitmap.andNot(A, B);
        System.out.println("expected == difference:" + expected.equals(difference));
    }

    /**
     * 异或 - 一个为 0 且 另一个为 1
     */
    static void usingXOR_thenWillGetSetsSymmetricDifference() {
        RoaringBitmap expected = RoaringBitmap.bitmapOf(1, 2, 3, 6, 7, 8);
        RoaringBitmap A = RoaringBitmap.bitmapOfRange(1, 6);
        RoaringBitmap B = RoaringBitmap.bitmapOfRange(4, 9);
        RoaringBitmap xor = RoaringBitmap.xor(A, B);

        System.out.println("expected == xor:" + expected.equals(xor));
    }
}