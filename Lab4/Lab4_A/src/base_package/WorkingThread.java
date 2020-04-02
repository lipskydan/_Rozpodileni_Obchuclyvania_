package base_package;

public class WorkingThread extends Thread {
    private int task;
    private File file;

    public WorkingThread(File file, int task) {
        this.task = task;
        this.file = file;
    }
    @Override
    public void run() {
        switch (task) {
            case 1:
                findPhoneByName("Victoria");
                break;
            case 2:
                findNameByPhone("999");
                break;
            case 3:
                readWriteRecords("Victoria","666", true);
//                readWriteRecords("Julia","999", false);
                break;
            default:
                System.out.println("!!! Unknown task number. !!!");
        }
    }

    private void findPhoneByName(String name) {
        Boolean wasFound = false;

        while (true) {
            file.lockRead();
            System.out.println("[WorkingThread::findPhoneByName()]: Start.");
            for (Record record : file.records) {
                if (record.getName().equals(name)){
                    System.out.println("[WorkingThread::findPhoneByName()]: Found " + record.toString());
                    wasFound = true;
                }
            }

            if (!wasFound) System.out.println("[WorkingThread::findPhoneByName()] phone by name {" + name + "}: is not found ");

            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("[WorkingThread::findPhoneByName()]: Finish.");
            file.unlockRead();
        }
    }

    private void findNameByPhone(String number) {
        Boolean wasFound = false;

        while (true) {
            file.lockRead();
            System.out.println("[WorkingThread::findNameByPhone()]: Start.");
            for (Record record : file.records) {
                if (record.getNumber().equals(number)){
                    System.out.println("[WorkingThread::findNameByPhone()]: Found " + record.toString());
                    wasFound = true;
                }
            }

            if (!wasFound) System.out.println("[WorkingThread::findNameByPhone()] name by phone {" + number + "}: is not found ");

            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("[WorkingThread::findNameByPhone()]: Finish.");
            file.unlockRead();
        }
    }

    private void readWriteRecords(String name, String number, Boolean action) {
        Record record = new Record(name, number);

        while (true) {
            file.lockWrite();

            if (action) System.out.println("[WorkingThread::readWriteRecords()] Write records: Start.");
            else System.out.println("[WorkingThread::readWriteRecords()] Delete records: Start.");

            if (action) file.records.add(record);
            else file.records.remove(record);

            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (action) System.out.println("[WorkingThread::readWriteRecords()] Write records: Finish.");
            else System.out.println("[WorkingThread::readWriteRecords()] Delete records: Finish.");

            file.unlockWrite();
        }
    }
}
