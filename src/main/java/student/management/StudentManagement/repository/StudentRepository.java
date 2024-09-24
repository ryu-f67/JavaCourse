package student.management.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import student.management.StudentManagement.data.Student;
import student.management.StudentManagement.data.StudentCourse;


/**
 * 受講生テーブルと受講生コース情報テーブルに紐づくRepositoryです。
 */
@Mapper
public interface StudentRepository {

  /**
   * すべての受講生情報を検索します。
   * (論理削除した受講生を含む。)
   *
   * @return 受講生情報のリスト(全件)
   */
  List<Student> searchAllStudents();

  /**
   * すべての受講生情報を検索します。
   * (論理削除した受講生を除く。)
   *
   * @return 受講生情報のリスト(全件)
   */
  List<Student> search();

  /**
   * 受講生IDに紐づく受講生情報を検索します。
   *
   * @param id 受講生ID
   * @return 受講生IDに紐づく受講生情報
   */
  Student searchStudentById(int id);

  /**
   * 受講生の名前に紐づく受講生情報を検索します。
   *
   * @param name 受講生の名前
   * @return 受講生の名前に紐づく受講生情報
   */
  List<Student> searchStudentByName(String name);

  /**
   * 受講生の名前に紐づく受講生情報を検索します。
   *
   * @param area 受講生の居住地
   * @return 受講生の居住地に紐づく受講生情報
   */
  List<Student> searchStudentByArea(String area);

  /**
   * すべての受講生コース情報を検索します。
   * (論理削除した受講生受講生のコース情報を含む。)
   *
   * @return 受講生コース情報のリスト(全件)
   */
  List<StudentCourse> searchAllStudentCourseList();

  /**
   * 受講生IDに紐づく受講生コース情報を検索します。
   *
   * @param studentId 受講生ID
   * @return 受講生IDに紐づく受講生コース情報のリスト
   */
  List<StudentCourse> searchStudentCourseList(Integer studentId);

  /**
   * 受講生IDに紐づく受講生コース情報を検索します。
   *
   * @param studentId 受講生ID(リスト)
   * @return 受講生IDに紐づく受講生コース情報のリスト
   */
  List<StudentCourse> searchStudentCourseList(List<Integer> studentId);

  /**
   * 受講コース名に紐づく受講生コース情報を検索します。
   *
   * @param courseName 受講コース名
   * @return 受講コース名に紐づく受講生コース情報のリスト
   */
  List<StudentCourse> searchCourseListByCourseName(String courseName);

  /**
   * 受講生情報を登録します。
   *
   * @param student 受講生情報
   */
  void insert(Student student);

  /**
   * 受講生コース情報を登録します。
   *
   * @param studentCourse 受講生コース情報
   */
  void insertCourse(StudentCourse studentCourse);

  /**
   * 受講生情報を更新します。
   *
   * @param student 受講生
   */
  void updateStudent(Student student);

  /**
   * 受講生IDに紐づく受講生コース情報を更新します。
   *
   * @param studentCourse 受講生コース情報(コース名)
   */
  void updateStudentCourse(StudentCourse studentCourse);

}
