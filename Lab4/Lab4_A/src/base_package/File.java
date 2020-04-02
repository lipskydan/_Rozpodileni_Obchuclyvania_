package base_package;

import java.util.ArrayList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class File {
    public ArrayList<Record> records;
    private ReadWriteLock lock;

    public File() {
        records = initializeStartRecords();
        lock = new ReentrantReadWriteLock();
    }
    private ArrayList<Record> initializeStartRecords() {
        ArrayList<Record> res = new ArrayList<>();
        res.add(new Record("Anton","123"));
        res.add(new Record("Bob","456"));
        res.add(new Record("Vasia", "789"));
        res.add(new Record("Julia", "999"));
        return res;
    }

    public void lockRead() {
        lock.readLock().lock();
    }
    public void unlockRead() {
        lock.readLock().unlock();
    }
    public void lockWrite() {
        lock.writeLock().lock();
    }
    public void unlockWrite() {
        lock.writeLock().unlock();
    }
}
