package tools;

import dto.KibanaDTO;
import dto.MicroserviceDTO;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SaveToJson {
    public SaveToJson(){

    }

    public void save(KibanaDTO kibanaDTO){
        JSONObject obj = new JSONObject();
        JSONArray jArray = new JSONArray();


        try (FileWriter file = new FileWriter("microservices.json")) {
        ArrayList<MicroserviceDTO> microserviceDTOArrayList = kibanaDTO.getMicroserviceDTOArrayList();

        for (MicroserviceDTO microserviceDTO : microserviceDTOArrayList){
            JSONObject obj2 = new JSONObject();
            obj2.put("microserviceName",microserviceDTO.getName());
            obj2.put("elasticsearchIp",microserviceDTO.getElasticsearchUrlIp());
            obj2.put("elasticsearchAppname",microserviceDTO.getElasticsearchAppname());
            obj2.put("kibanaLast15Link",microserviceDTO.getKibanaLast15Link());
            obj2.put("kibanaTodayLink",microserviceDTO.getKibanaTodayLink());
            obj2.put("elasticsearchQuery",microserviceDTO.getElasticsearchQuery());

            obj2.put("errorKey",microserviceDTO.isErrorKey());
            jArray.put(obj2);
        }
        obj.put("microservices", jArray);

            file.write(obj.toString());
            file.flush();

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        System.out.print(obj);
    }
}
