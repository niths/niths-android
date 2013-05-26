package main.java.no.niths.domain.school;

import java.util.List;

import main.java.no.niths.domain.DomainObject;

/**
 * Created with IntelliJ IDEA.
 * User: elotin
 * Date: 14.05.13
 * Time: 21:29
 * To change this template use File | Settings | File Templates.
 */


public class Committee implements DomainObject {


    private long id;
    private String name;
    private String description;
    private List<Student> leaders;
    private List<Event> events;
    private List<Student> members;

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

    public List<Student> getLeaders() {
        return leaders;
    }

    public void setLeaders(List<Student> leaders) {
        this.leaders = leaders;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<Student> getMembers() {
        return members;
    }

    public void setMembers(List<Student> members) {
        this.members = members;
    }

    @Override
    public Long getId() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setId(long id) {
        this.id = id;
    }
}
