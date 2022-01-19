package com.langyastudio.functional;

/**
 * 接收三个入参
 *
 * @author jiangjiaxiong
 * @date 2021年07月28日 9:55
 */
@FunctionalInterface
public interface TriFunction <T, U, K, R>
{
    R apply(T t, U u, K k);
}
