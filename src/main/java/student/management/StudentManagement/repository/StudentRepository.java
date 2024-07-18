package student.management.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import student.management.StudentManagement.data.Student;
import student.management.StudentManagement.data.StudentCourse;

@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM students")
  List<Student> search();

  @Select("SELECT * FROM students_courses")
  List<StudentCourse> searchStudentCourses();

  @Insert(
      "INSERT students(id, name, furigana, nickname, mail, area, age, gender, remark, is_deleted)"
          + "VALUES(#{id}, #{name}, #{furigana}, #{nickname}, #{mail}, #{area}, #{age}, #{gender}, #{remark}, false)")
  void insert(Student student);

}
