package main.java.no.niths.services.domain.school.interfaces;

import main.java.no.niths.domain.school.Student;
import main.java.no.niths.services.TokenBundle;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: elotin
 * Date: 12.05.13
 * Time: 12:38
 * To change this template use File | Settings | File Templates.
 */
public interface StudentsService extends GenericCrudServiceInterface<Student> {
    public Student getStudentByEmail(String email);
}
