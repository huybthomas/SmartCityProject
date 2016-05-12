package be.uantwerpen.sc.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Niels on 11/05/2016.
 */
@RestController
public class SimCommandController {

    public void sendCommand(String robotUri, String command){

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.postForObject(robotUri, command, String.class);
        System.out.println(result);

    }
}
