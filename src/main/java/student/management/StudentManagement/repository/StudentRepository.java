package student.management.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import student.management.StudentManagement.data.Student;
import student.management.StudentManagement.data.StudentCourse;

@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM students")
  List<Student> searchAllStudents();

  @Select("SELECT * FROM students WHERE is_deleted=false")
  List<Student> search();

  @Select("SELECT * FROM students WHERE id=#{id}")
  Student searchStudent(String id);

  @Select("SELECT * FROM students_courses")
  List<StudentCourse> searchStudentCourses();

  @Select("SELECT * FROM students_courses WHERE student_id=#{studentId}")
  List<StudentCourse> searchStudentCourse(String studentId);

  @Insert(
      "INSERT students(id, name, furigana, nickname, mail, area, age, gender, remark, is_deleted)"
          + "VALUES(#{id}, #{name}, #{furigana}, #{nickname}, #{mail}, #{area}, #{age}, #{gender}, #{remark}, false)")
  void insert(Student student);

  @Insert(
      "INSERT students_courses(student_id, course_name, start_at, end_at)"
          + "VALUES(#{studentId}, #{courseName}, #{startAt}, #{endAt})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insertCourse(StudentCourse studentCourse);

  @Update(
      "UPDATE students SET name=#{name}, furigana=#{furigana}, nickname=#{nickname}, mail=#{mail}, "
          + "area=#{area}, age=#{age}, gender=#{gender}, remark=#{remark}, is_deleted=#{isDeleted} "
          + "WHERE id = #{id}")
  void updateStudent(Student student);

  @Update(
      "UPDATE students_courses SET course_name=#{courseName} WHERE id = #{id}")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void updateStudentCourse(StudentCourse studentCourse);

}
