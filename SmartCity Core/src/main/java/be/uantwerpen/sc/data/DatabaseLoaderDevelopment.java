package be.uantwerpen.sc.data;

import be.uantwerpen.sc.repositories.PointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by Thomas on 25/02/2016.
 */
@Service
@Profile("dev")
public class DatabaseLoaderDevelopment
{
    private final PointRepository pointRepository;

    @Autowired
    public DatabaseLoaderDevelopment(PointRepository pointRepos)
    {
        this.pointRepository = pointRepos;
    }

    @PostConstruct
    private void initDatabase()
    {
        //Check if tables are initialised or empty
        if(true)
        {
            //Tables are initialised, no need to refill database
            return;
        }

        //Initialise point table
        initPointTable();
    }

    private void initPointTable()
    {

    }
}
