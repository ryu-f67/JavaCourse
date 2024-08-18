package student.management.StudentManagement.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import student.management.StudentManagement.controller.converter.StudentConverter;
import student.management.StudentManagement.data.Student;
import student.management.StudentManagement.data.StudentCourse;
import student.management.StudentManagement.domain.StudentDetail;
import student.management.StudentManagement.repository.StudentRepository;

/**
 * 受講生情報を扱うサービスです。 受講生の検索や登録、更新を行います。
 */
@Service
public class StudentService {

  private StudentRepository repository;
  private StudentConverter converter;

  @Autowired
  public StudentService(StudentRepository repository, StudentConverter converter) {
    this.repository = repository;
    this.converter = converter;
  }

  /**
   * 全受講生の検索を行います。
   * 論理削除を行った受講生も表示されます。
   *
   * @return 受講生一覧(全件)
   */
  public List<StudentDetail> searchAllStudentList() {
    List<Student> studentList = repository.searchAllStudents();
    List<StudentCourse> studentCoursesList = repository.searchStudentCourses();
    return converter.convertStudentDetails(studentList, studentCoursesList);
  }

  /**
   * 全受講生の検索を行います。
   * 論理削除を行った受講生は表示されません。
   *
   * @return 受講生一覧(全件)
   */
  public List<StudentDetail> searchStudentList() {
    List<Student> studentList = repository.search();
    List<StudentCourse> studentCoursesList = repository.searchStudentCourses();
    return converter.convertStudentDetails(studentList, studentCoursesList);
  }

  /**
   * 受講生検索です。
   * IDに対応する受講生情報を取得した後、その受講生に対応した受講生コース情報を取得し設定します。
   *
   * @param id 受講生ID
   * @return 受講生
   */
  public StudentDetail searchStudent(String id) {
    Student student = repository.searchStudent(id);
    List<StudentCourse> studentCourses = repository.searchStudentCourse(student.getId());
    return new StudentDetail(student, studentCourses);
  }

  /**
   * 受講生のコース情報の検索を行います。
   * すべてのコース情報が表示されます。
   *
   * @return 受講生コース一覧(全件)
   */
  public List<StudentCourse> searchStudentCourseList() {
    return repository.searchStudentCourses();
  }

  /**
   * 受講生情報を登録します。
   *
   * @param studentDetail 受講生と受講コースの情報
   */
  @Transactional
  public StudentDetail registerStudent(StudentDetail studentDetail) {
    repository.insert(studentDetail.getStudent());
    for (StudentCourse studentCourse : studentDetail.getStudentCourses()) {
      studentCourse.setStudentId(studentDetail.getStudent().getId());
      studentCourse.setStartAt(LocalDateTime.now());
      studentCourse.setEndAt(LocalDateTime.now().plusMonths(2));
      repository.insertCourse(studentCourse);
    }
    return studentDetail;
  }

  /**
   * 受講生情報を更新します。
   *
   * @param studentDetail 受講生とコースの情報
   */
  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    repository.updateStudent(studentDetail.getStudent());
    for (StudentCourse studentCourse : studentDetail.getStudentCourses()) {
      repository.updateStudentCourse(studentCourse);
    }
  }
}
