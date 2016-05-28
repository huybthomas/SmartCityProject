package be.uantwerpen.sc.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Niels on 12/05/2016.
 */
@RestController
public class SimulationController
{
    public void setSimulation(String robotUri, boolean simulate){

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.postForObject(robotUri, simulate, String.class);
        System.out.println(result);
    }
}
