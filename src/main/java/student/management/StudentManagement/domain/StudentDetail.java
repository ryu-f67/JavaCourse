package student.management.StudentManagement.domain;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import student.management.StudentManagement.data.Student;
import student.management.StudentManagement.data.StudentCourse;

@Getter
@Setter
public class StudentDetail {

  private Student student;
  private List<StudentCourse> studentCourses;

}
