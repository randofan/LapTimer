package com.davsong.laptimer;

import java.util.concurrent.atomic.AtomicLong;

public interface TimerInterface {
    AtomicLong currentCentiseconds = new AtomicLong();
}
