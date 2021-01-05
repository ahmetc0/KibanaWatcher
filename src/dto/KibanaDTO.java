package dto;

import dto.MicroserviceDTO;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class KibanaDTO {
    private ArrayList<MicroserviceDTO> microserviceDTOArrayList;

    public KibanaDTO(JSONObject kibanaInformations){
        microserviceDTOArrayList = new ArrayList<>();
        try {
            JSONArray jsonArray = (JSONArray) kibanaInformations.get("microservices");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject service = (JSONObject) jsonArray.get(i);
                microserviceDTOArrayList.add(new MicroserviceDTO(service.getString("microserviceName"),
                                service.getString("elasticsearchIp"),
                                service.getString("elasticsearchAppname"),
                                service.getString("kibanaLast15Link"),
                                service.getString("kibanaTodayLink"),
                                service.getString("elasticsearchQuery"),
                                service.getBoolean("errorKey")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public KibanaDTO(ArrayList<MicroserviceDTO> microserviceDTOArrayList){
        this.microserviceDTOArrayList = microserviceDTOArrayList;

    }

    public ArrayList<MicroserviceDTO> getMicroserviceDTOArrayList() {
        return microserviceDTOArrayList;
    }

    public void setMicroserviceDTOArrayList(ArrayList<MicroserviceDTO> microserviceDTOArrayList) {
        this.microserviceDTOArrayList = microserviceDTOArrayList;
    }
}
