package main.java.no.niths.domain.school;

import main.java.no.niths.domain.DomainObject;

import java.util.List;

public class Faddergruppe implements DomainObject {


    private Long id;
    private Integer groupNumber;
    private List<Student> leaders;
    private List<Student> fadderChildren;

    public Integer getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(Integer groupNumber) {
        this.groupNumber = groupNumber;
    }

    public List<Student> getLeaders() {
        return leaders;
    }

    public void setLeaders(List<Student> leaders) {
        this.leaders = leaders;
    }

    public List<Student> getFadderChildren() {
        return fadderChildren;
    }

    public void setFadderChildren(List<Student> fadderChildren) {
        this.fadderChildren = fadderChildren;
    }

    @Override
    public Long getId() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setId(Long id) {
        this.id = id;
    }
}
