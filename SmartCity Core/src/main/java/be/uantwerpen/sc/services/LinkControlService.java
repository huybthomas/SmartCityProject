package be.uantwerpen.sc.services;

import be.uantwerpen.sc.models.Link;
import be.uantwerpen.sc.repositories.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Niels on 30/03/2016.
 */
@Service
public class LinkControlService
{
    @Autowired
    private LinkRepository linkRepository;

    public List<Link> getAllLinks()
    {
        return linkRepository.findAll();
    }

    public Link getLink(Long id)
    {
        return linkRepository.findOne(id);
    }
}
