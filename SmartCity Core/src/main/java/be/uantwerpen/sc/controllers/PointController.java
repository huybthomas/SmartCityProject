package be.uantwerpen.sc.controllers;

import be.uantwerpen.sc.models.Point;
import be.uantwerpen.sc.services.PointControlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Niels on 23/03/2016.
 */
@RestController
@RequestMapping("/point/")
public class PointController
{
    @Autowired
    private PointControlService pointService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Point> allPoints()
    {
        List<Point> points = pointService.getAllPoints();

        return points;
    }

    @RequestMapping(value = "requestlock/{id}", method = RequestMethod.GET)
    public boolean requestPointLock(@PathVariable("id") Long id)
    {
        synchronized(this)
        {
            Point point = pointService.getPoint(id);

            if(point == null)
            {
                //Point not found
                return false;
            }

            switch(point.getPointLock())
            {
                case 1: //Point already locked
                    return false;
                case 0: //Point not locked -> attempt lock
                    point.setPointLock(1);
                    pointService.save(point);
                    return true;
                default:
                    break;
            }
        }

        return false;
    }

    @RequestMapping(value = "getlock/{id}", method = RequestMethod.GET)
    public boolean getPointStatus(@PathVariable("id") Long id)
    {
        Point point = pointService.getPoint(id);

        if(point == null)
        {
            //Point not found
            return false;
        }

        switch(point.getPointLock())
        {
            case 1:
                return true;
            case 0:
                return false;
            default:
        }
        return true;
    }

    @RequestMapping(value = "setlock/{id}/{value}", method = RequestMethod.GET)
    public boolean setPointStatus(@PathVariable("id") Long id, @PathVariable("value") int value)
    {
        synchronized (this)
        {
            Point point = pointService.getPoint(id);

            if(point == null)
            {
                //Point not found
                return false;
            }

            point.setPointLock(value);
            pointService.save(point);

            return true;
        }
    }
}
