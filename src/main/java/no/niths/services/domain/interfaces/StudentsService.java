package main.java.no.niths.services.domain.interfaces;

import main.java.no.niths.domain.Student;
import main.java.no.niths.services.TokenBundle;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: elotin
 * Date: 12.05.13
 * Time: 12:38
 * To change this template use File | Settings | File Templates.
 */
public interface StudentsService {
    List<Student> getStudents(int offset, int max, TokenBundle tokens);

    Student updateStudent(Student student);

    Student getStudentById(Long id);

    Student getStudentByEmail(String email);
}
