package com.aircraft.codelab.pioneer.locking;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 2021-11-03
 *
 * @author tao.zhang
 * @since 1.0
 */
public class SpinLockTest {
    private AtomicReference<Thread> cas = new AtomicReference<>();

    @Test
    void casTest() {

    }
}
