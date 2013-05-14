package main.java.no.niths.services.domain.school.interfaces;

import main.java.no.niths.domain.school.Faddergruppe;
import main.java.no.niths.domain.school.Student;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: elotin
 * Date: 14.05.13
 * Time: 15:15
 * To change this template use File | Settings | File Templates.
 */
public interface FaddergruppeService {
    public Faddergruppe getFaddergruppeForStudent(Student student);

    public List<Faddergruppe> getFaddergrupperForLeader(Student leader);
}
