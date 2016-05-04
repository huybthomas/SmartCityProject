package be.uantwerpen.sc.controllers;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Niels on 4/05/2016.
 */
@RestController
public class JobController {


    public void sendJob(String robotUri, String job){

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.postForObject( robotUri, job, String.class);
        System.out.println(result);

    }
}
