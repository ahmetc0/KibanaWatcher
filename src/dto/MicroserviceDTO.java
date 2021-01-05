package dto;

import java.awt.*;
import java.util.ArrayList;

public class MicroserviceDTO {
    private String name;
    private String kibanaLast15Link = "";
    private String kibanaTodayLink = "";
    private String elasticsearchUrlIp = "";
    private String elasticsearchAppname = "";
    private String elasticsearchQuery = "";
    private int iconDigitNumber = 2;
    private boolean errorKey;

    public MicroserviceDTO(String name, String elasticsearchIp, String elasticsearchAppname, String kibanaLast15Link, String kibanaTodayLink, String elasticsearchQuery, boolean errorKey) {
        this.name = name;
        this.elasticsearchUrlIp = elasticsearchIp;
        this.elasticsearchAppname = elasticsearchAppname;
        this.kibanaLast15Link = kibanaLast15Link;
        this.kibanaTodayLink = kibanaTodayLink;
        this.elasticsearchQuery = elasticsearchQuery;
        this.errorKey = errorKey;
    }

    public MicroserviceDTO(String name, String elasticsearchIp, String elasticsearchAppname, String kibanaLast15Link, String kibanaTodayLink, String elasticsearchQuery) {
        this.name = name;
        this.elasticsearchUrlIp = elasticsearchIp;
        this.elasticsearchAppname = elasticsearchAppname;
        this.kibanaLast15Link = kibanaLast15Link;
        this.kibanaTodayLink = kibanaTodayLink;
        this.elasticsearchQuery = elasticsearchQuery;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKibanaLast15Link() {
        return kibanaLast15Link;
    }

    public void setKibanaLast15Link(String kibanaLast15Link) {
        this.kibanaLast15Link = kibanaLast15Link;
    }

    public String getKibanaTodayLink() {
        return kibanaTodayLink;
    }

    public void setKibanaTodayLink(String kibanaTodayLink) {
        this.kibanaTodayLink = kibanaTodayLink;
    }

    public String getElasticsearchUrlIp() {
        return elasticsearchUrlIp;
    }

    public void setElasticsearchUrlIp(String elasticsearchUrlIp) {
        this.elasticsearchUrlIp = elasticsearchUrlIp;
    }

    public String getElasticsearchAppname() {
        return elasticsearchAppname;
    }

    public void setElasticsearchAppname(String elasticsearchAppname) {
        this.elasticsearchAppname = elasticsearchAppname;
    }

    public String getElasticsearchQuery() {
        return elasticsearchQuery;
    }

    public void setElasticsearchQuery(String elasticsearchQuery) {
        this.elasticsearchQuery = elasticsearchQuery;
    }

    public int getIconDigitNumber() {
        return iconDigitNumber;
    }

    public void setIconDigitNumber(int iconDigitNumber) {
        this.iconDigitNumber = iconDigitNumber;
    }

    public boolean isErrorKey() {
        return errorKey;
    }

    public void setErrorKey(boolean errorKey) {
        this.errorKey = errorKey;
    }
}
