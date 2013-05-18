package main.java.no.niths.domain.school;

import main.java.no.niths.domain.DomainObject;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * Created by elotin on 18.05.13.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Location implements DomainObject {

    @JsonProperty
    private Long id;
    @JsonProperty
    private String place;
    @JsonProperty
    private Double latitude;
    @JsonProperty
    private Double longitude;
    @JsonProperty

    private List<Event> events;

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
