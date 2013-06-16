package main.java.no.niths.domain.school;

import com.google.gson.annotations.SerializedName;
import main.java.no.niths.domain.DomainObject;
import org.joda.time.DateTime;

public class Event implements DomainObject {

    @SerializedName("id")
    private long id;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("startTime")
    private DateTime startTime;
    @SerializedName("endTime")
    private DateTime endTime;
    @SerializedName("tags")
    private String tags;
    @SerializedName("location")
    private Location location;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public Long getId() {
        return id;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setId(long id) {
        this.id = id;
    }

    public DateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(DateTime startTime) {
        this.startTime = startTime;
    }

    public DateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(DateTime endTime) {
        this.endTime = endTime;
    }
}
