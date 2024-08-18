package student.management.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import student.management.StudentManagement.data.Student;
import student.management.StudentManagement.data.StudentCourse;


/**
 * 受講生テーブルと受講生コース情報テーブルに紐づくRepositoryです。
 */
@Mapper
public interface StudentRepository {

  /**
   * すべての受講生情報を検索します。 (論理削除した受講生を含む。)
   *
   * @return 受講生情報(全件)
   */
  List<Student> searchAllStudents();

  /**
   * すべての受講生情報を検索します。
   * (論理削除した受講生を除く。)
   *
   * @return 受講生情報(全件)
   */
  List<Student> search();

  /**
   * 受講生IDに紐づく受講生情報を検索します。
   *
   * @param id 受講生ID
   * @return 受講生IDに紐づく受講生情報
   */
  Student searchStudent(String id);

  /**
   * すべての受講生コース情報を検索します。
   * (論理削除した受講生受講生のコース情報を含む。)
   *
   * @return 受講生コース情報(全件)
   */
  @Select("SELECT * FROM students_courses")
  List<StudentCourse> searchStudentCourseList();

  /**
   * 受講生IDに紐づく受講生コース情報を検索します。
   *
   * @param studentId 受講生ID
   * @return 受講生IDに紐づく受講生コース情報
   */
  @Select("SELECT * FROM students_courses WHERE student_id=#{studentId}")
  List<StudentCourse> searchStudentCourse(String studentId);

  /**
   * 受講生情報を登録します。
   *
   * @param student 受講生情報
   */
  @Insert(
      "INSERT students(id, name, furigana, nickname, mail, area, age, gender, remark, is_deleted)"
          + "VALUES(#{id}, #{name}, #{furigana}, #{nickname}, #{mail}, #{area}, #{age}, #{gender}, #{remark}, false)")
  void insert(Student student);

  /**
   * 受講生コース情報を登録します。
   *
   * @param studentCourse 受講生コース情報
   */
  @Insert(
      "INSERT students_courses(student_id, course_name, start_at, end_at)"
          + "VALUES(#{studentId}, #{courseName}, #{startAt}, #{endAt})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insertCourse(StudentCourse studentCourse);

  /**
   * 受講生情報を更新します。
   *
   * @param student 受講生
   */
  @Update(
      "UPDATE students SET name=#{name}, furigana=#{furigana}, nickname=#{nickname}, mail=#{mail}, "
          + "area=#{area}, age=#{age}, gender=#{gender}, remark=#{remark}, is_deleted=#{isDeleted} "
          + "WHERE id = #{id}")
  void updateStudent(Student student);

  /**
   * 受講生IDに紐づく受講生コース情報を更新します。
   *
   * @param studentCourse 受講生コース情報(コース名)
   */
  @Update(
      "UPDATE students_courses SET course_name=#{courseName} WHERE id = #{id}")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void updateStudentCourse(StudentCourse studentCourse);

}
