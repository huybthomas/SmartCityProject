package be.uantwerpen.sc.tools;

import be.uantwerpen.sc.models.map.MapJson;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Niels on 20/04/2016.
 */
@Component
public class GsonParser {

    public MapJson fromJson()  throws IOException {
        JsonReader jsonReader = new JsonReader(new FileReader("omapjson.json"));
        Gson gson = new GsonBuilder().create();
        MapJson mapJson = gson.fromJson(jsonReader, MapJson.class);
        System.out.println(mapJson);

        return mapJson;
    }

    public void toJson(MapJson mapJson){
        Gson gson = new GsonBuilder().create();
        //code voor eventueel nar file te schrijven
    }
}
