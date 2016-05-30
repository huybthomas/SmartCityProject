package be.uantwerpen.sc.tools.simulators.vehicles.cars.smartcar.handlers;

/**
 * Created by Thomas on 28/05/2016.
 */
public class DriveHandler
{
    private float speed;
    private float currentPosition;
    private float targetPosition;
    private boolean paused;
    private boolean driving;
    private QueueHandler queue;

    private static final float MMPD = 0.46f;    //mm per degree
    private static final float MMRD = 2f;       //mm per degree to rotate

    public DriveHandler()
    {
        this.speed = 70;

        this.currentPosition = 0.0f;
        this.targetPosition = 0.0f;

        this.paused = false;
        this.driving = false;

        this.queue = new QueueHandler();
    }

    public DriveHandler(float speed)
    {
        this();

        this.speed = speed;
    }

    public void newDriveDistanceCommand(float distance)
    {
        //Total turning angle for the wheels
        float targetPosition = distance/MMPD;

        this.queue.addElement(targetPosition);
    }

    public void newTurnAngleCommand(float angle)
    {
        //Total turning distance for wheel
        float targetPosition = angle*MMRD/MMPD;

        this.queue.addElement(targetPosition);
    }

    public void setSpeed(float speed)
    {
        this.speed = speed;
    }

    public void setPaused(boolean flag)
    {
        this.paused = flag;
    }

    public boolean isPaused()
    {
        return this.paused;
    }

    public void abortDriving()
    {
        this.driving = false;
    }

    public double getCurrentPosition()
    {
        return this.currentPosition;
    }

    /**
     * Get the travelled distance since the last drive command
     * @return Distance in mm
     */
    public double getTravelledDistance()
    {
        return this.currentPosition * MMPD/2;   //Calculate position in mm
    }

    /**
     * @param elapsedTime Time elapsed since last update in ms.
     * @return Returns true if target position is reached for the first time.
     */
    public boolean updatePosition(double elapsedTime)
    {
        //Speed --> distance/s
        double rotation = ((Math.abs(speed)/1000) * elapsedTime)/MMPD;

        if(this.targetPosition < 0)
        {
            //Drive backwards
            rotation = -rotation;
        }

        if(Math.abs(this.currentPosition) >= Math.abs(this.targetPosition))
        {
            driving = false;

            //Target position already reached
            return false;
        }

        if(!paused && driving)
        {
            if(Math.abs(this.currentPosition + rotation) >= Math.abs(this.targetPosition))
            {
                this.currentPosition = this.targetPosition;

                //Target position is reached
                return true;
            }
            else
            {
                this.currentPosition += rotation;

                //Target position is not reached yet
                return false;
            }
        }

        return false;
    }

    public void flushAllDriveTasks()
    {
        this.queue.flush();
    }

    public boolean startNextDriveTask()
    {
        if(!this.queue.isEmpty() && !this.driving)
        {
            this.currentPosition = 0.0f;

            this.targetPosition = (float)this.queue.getNextElement();

            this.driving = true;

            return true;
        }
        else
        {
            return false;
        }
    }
}
