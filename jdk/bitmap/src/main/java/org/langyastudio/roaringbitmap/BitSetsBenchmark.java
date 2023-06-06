package org.langyastudio.roaringbitmap;

import org.openjdk.jmh.annotations.*;
import org.roaringbitmap.RoaringBitmap;

import java.util.BitSet;
import java.util.concurrent.TimeUnit;

/**
 * BitMap 性能测试 - 声明基准范围
 *
 * @author jiangjiaxiong
 * @date 2023年06月06日 09:00
 */
@State(Scope.Thread)//每个线程独享
@BenchmarkMode(Mode.All)//测试所有类型
@Warmup(iterations = 1)//预热次数，确保JVM运行状态一致
@Measurement(iterations = 10, time = 5, timeUnit = TimeUnit.SECONDS)//度量，10 次 每次 5 秒
@Threads(8)
@Fork(1)
@OutputTimeUnit(TimeUnit.MILLISECONDS)//输出为毫秒
public class BitSetsBenchmark {
    private              RoaringBitmap rb1;
    private              BitSet        bs1;
    private              RoaringBitmap rb2;
    private              BitSet        bs2;
    private final static int           SIZE = 1_000_000;

    @Benchmark
    public RoaringBitmap roaringBitmapUnion() {
        return RoaringBitmap.or(rb1, rb2);
    }

    @Benchmark
    public BitSet bitSetUnion() {
        BitSet result = (BitSet) bs1.clone();
        result.or(bs2);
        return result;
    }

    @Benchmark
    public RoaringBitmap roaringBitmapIntersection() {
        return RoaringBitmap.and(rb1, rb2);
    }

    @Benchmark
    public BitSet bitSetIntersection() {
        BitSet result = (BitSet) bs1.clone();
        result.and(bs2);
        return result;
    }

    @Benchmark
    public RoaringBitmap roaringBitmapDifference() {
        return RoaringBitmap.andNot(rb1, rb2);
    }

    @Benchmark
    public BitSet bitSetDifference() {
        BitSet result = (BitSet) bs1.clone();
        result.andNot(bs2);
        return result;
    }

    @Benchmark
    public RoaringBitmap roaringBitmapXor() {
        return RoaringBitmap.xor(rb1, rb2);
    }

    @Benchmark
    public BitSet bitSetXOR() {
        BitSet result = (BitSet) bs1.clone();
        result.xor(bs2);
        return result;
    }

    /**
     * 基准数据设置
     */
    @Setup
    public void setup() {
        rb1 = new RoaringBitmap();
        bs1 = new BitSet(SIZE);
        rb2 = new RoaringBitmap();
        bs2 = new BitSet(SIZE);
        for (int i = 0; i < SIZE / 2; i++) {
            rb1.add(i);
            bs1.set(i);
        }
        for (int i = SIZE / 2; i < SIZE; i++) {
            rb2.add(i);
            bs2.set(i);
        }
    }
}
