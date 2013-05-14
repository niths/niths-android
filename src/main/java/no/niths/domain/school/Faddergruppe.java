package main.java.no.niths.domain.school;

import main.java.no.niths.domain.DomainObject;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: elotin
 * Date: 14.05.13
 * Time: 15:07
 * To change this template use File | Settings | File Templates.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Faddergruppe implements DomainObject {

    @JsonProperty
    private Long id;

    @JsonProperty
    private Integer groupNumber;

    @JsonProperty
    private List<Student> leaders;

    @JsonProperty
    private List<Student> fadderChildren;

    public void setId(Long id) {
        this.id = id;
    }

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
}
