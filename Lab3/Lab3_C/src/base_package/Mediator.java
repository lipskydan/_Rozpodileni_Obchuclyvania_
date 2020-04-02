package base_package;

import java.util.Random;

class Mediator extends Thread {

    Mediator() {

    }

    @Override
    public void run() {
        while (true) {
            putItems();
        }
    }

    private void putItems() {
        try {
            Action.mediator_is_working.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("[Mediator::putItems()] Mediator is putting 2 items on table...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Random random = new Random();
        int i = Math.abs(random.nextInt()) % 3;
        switch (i) {
            case 0:
                Action.table.tabaco  = true;
                System.out.println("[Mediator::putItems()] Tabaco is put");
                break;
            case 1:
                Action.table.paper   = true;
                System.out.println("[Mediator::putItems()] Paper is put");
                break;
            case 2: Action.table.matches = true;
                System.out.println("[Mediator::putItems()] Matches is put");
                break;
        }

        int j = Math.abs(random.nextInt()) % 3;

        while (j == i ){
            j = Math.abs(random.nextInt()) % 3;
        }

        switch (j) {
            case 0:
                Action.table.tabaco  = true;
                System.out.println("[Mediator::putItems()] Tabaco is put");
                break;
            case 1:
                Action.table.paper   = true;
                System.out.println("[Mediator::putItems()] Paper is put");
                break;
            case 2: Action.table.matches = true;
                System.out.println("[Mediator::putItems()] Matches is put");
                break;
        }

        Action.smoker_is_smoking.release();
    }
}
