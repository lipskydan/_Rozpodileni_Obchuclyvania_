package base_package;

public class MyReadWriteLock {
    private Integer readSemaphore;
    private Integer writeSemaphore;

    public MyReadWriteLock() {
        readSemaphore = 1;
        writeSemaphore = 1;
    }

    public void readLock() {
        while (readSemaphore == 0) {}
        synchronized (readSemaphore) { readSemaphore = 0; }
    }

    public void readUnlock() {
        synchronized (readSemaphore){ readSemaphore = 1; }
    }

    public void writeLock() {
        while (readSemaphore == 0) {}
        synchronized (readSemaphore) { readSemaphore = 0; }

        while (writeSemaphore == 0) {}
        synchronized (writeSemaphore) { writeSemaphore = 0; }
    }

    public void writeUnlock() {
        synchronized (readSemaphore) { readSemaphore = 1; }
        synchronized (writeSemaphore) { writeSemaphore = 1; }
    }
}