package be.uantwerpen.sc.tools.pathplanning;

import java.util.List;

/**
 * Created by Thomas on 22/07/2016.
 */
public interface PathPlanner
{
    List<Object> calculatePath(AbstractMap map, int start, int stop);
}
