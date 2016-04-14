package be.uantwerpen.sc.models;

/**
 * Created by Niels on 6/04/2016.
 */
public class RobotThread extends Thread {

    private BotEntity botEntity;
    private  boolean initialize = false;

    public RobotThread(BotEntity botEntity){
        this.botEntity = botEntity;
        initialize = true;
        System.out.println(this.getName() + " created.");
    }

    @Override
    public void run() {
        System.out.println(this.getId() + " is running");
        /*try {

            //Lock on his location
            //botEntity.getLinkId().getStartId().getPointLock().acquire();
            // Acquire Lock for next point
           // botEntity.getLinkId().getStopId().getPointLock().acquire();
        } catch (InterruptedException e) {
            System.out.println("received InterruptedException");
            return;
        }*/
        System.out.println(this.toString() + " is going to " + botEntity.getLinkId().getStopId().toString() + ", because its free, but now its locked.");
        try {
            sleep(1000);
        } catch (Exception e) {

        } finally {

            // Release Lock
            //botEntity.getLinkId().getStartId().getPointLock().release();
        }
        System.out.println( botEntity.getLinkId().getStopId().toString().toString() + "is free again.");

    }
}
