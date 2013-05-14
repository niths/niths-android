package main.java.no.niths.domain;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created with IntelliJ IDEA.
 * Student: elotin
 * Date: 10.05.13
 * Time: 12:47
 * To change this template use File | Settings | File Templates.
 */

public class Role {

    @JsonProperty
    Long id;

    @JsonProperty
    String RoleName;
}
