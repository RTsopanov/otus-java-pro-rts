package rts;

import java.util.concurrent.atomic.AtomicBoolean;

public class ServerValueHolder {
    private volatile long lastValue = 0;
    private final AtomicBoolean hasUnconsumedValue = new AtomicBoolean(false);

    public void update(long newValue) {
        this.lastValue = newValue;
        this.hasUnconsumedValue.set(true);
    }

    public long consume() {
        if (hasUnconsumedValue.compareAndSet(true, false)) {
            return lastValue;
        }
        return 0L;
    }
}