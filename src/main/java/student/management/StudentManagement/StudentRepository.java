package student.management.StudentManagement;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM students")
  List<Student> findAllStudents();

  @Select("SELECT * FROM students_courses")
  List<StudentCourse> findAllStudentCourses();
}
