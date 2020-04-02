package base_package;

class Smoker extends Thread{
    private int has_item;

    Smoker(int has_item) {
       this.has_item = has_item;
    }

    @Override
    public void run() {
        while (true) {
            lookAtTheTable();
        }
    }

    private void lookAtTheTable() {
        try {
            Action.smoker_is_smoking.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        switch (has_item) {
            case 0: // has tabaco
                if (Action.table.paper && Action.table.matches) {
                    Action.table.paper = false;
                    Action.table.matches = false;

                    makeCigarAndSmoke("Tabaco");

                    Action.mediator_is_working.release();
                } else Action.smoker_is_smoking.release();
                break;
            case 1: // has paper
                if (Action.table.tabaco && Action.table.matches) {
                    Action.table.tabaco = false;
                    Action.table.matches = false;

                    makeCigarAndSmoke("Paper");

                    Action.mediator_is_working.release();
                } else Action.smoker_is_smoking.release();
                break;
            case 2: // has matches
                if (Action.table.tabaco && Action.table.paper) {
                    Action.table.tabaco = false;
                    Action.table.paper = false;

                    makeCigarAndSmoke("Matches");

                    Action.mediator_is_working.release();
                } else Action.smoker_is_smoking.release();
                break;
        }
    }

    private void makeCigarAndSmoke(String whoSmoke) {
        try {
            System.out.println("[Smoker::lookAtTheTable()] " + whoSmoke + " smoker is smoking");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
