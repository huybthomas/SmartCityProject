package be.uantwerpen.sc.formatters;

import be.uantwerpen.sc.models.sim.SimWorker;
import be.uantwerpen.sc.repositories.SimWorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

/**
 * Created by Thomas on 03/04/2016.
 */
@Component
public class SimWorkerFormatter implements Formatter<SimWorker>
{
    @Autowired
    private SimWorkerRepository simWorkerRepository;

    public SimWorker parse(final String text, final Locale locale) throws ParseException
    {
        if(text != null && !text.isEmpty())
        {
            return simWorkerRepository.findOne(new Long(text));
        }
        else
        {
            return null;
        }
    }

    public String print(final SimWorker object, final Locale locale)
    {
        return (object != null ? object.getId().toString() : "");
    }
}
