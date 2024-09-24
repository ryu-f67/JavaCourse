package student.management.StudentManagement.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import student.management.StudentManagement.controller.converter.StudentConverter;
import student.management.StudentManagement.data.Student;
import student.management.StudentManagement.data.StudentCourse;
import student.management.StudentManagement.domain.StudentDetail;
import student.management.StudentManagement.exception.InvalidStudentIdListException;
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
   * @return 受講生一覧のリスト(全件)
   */
  public List<StudentDetail> searchAllStudentList() {
    List<Student> studentList = repository.searchAllStudents();
    List<StudentCourse> studentCourseList = repository.searchAllStudentCourseList();
    return converter.convertStudentDetails(studentList, studentCourseList);
  }

  /**
   * 全受講生の検索を行います。
   * 論理削除を行った受講生は表示されません。
   *
   * @return 受講生一覧のリスト(全件)
   */
  public List<StudentDetail> searchStudentList() {
    List<Student> studentList = repository.search();
    List<StudentCourse> studentCourseList = repository.searchAllStudentCourseList();
    return converter.convertStudentDetails(studentList, studentCourseList);
  }

  /**
   * 受講生検索です。
   * IDに対応する受講生情報を取得した後、その受講生に対応した受講生コース情報を取得し設定します。
   *
   * @param id 受講生ID
   * @return 受講生詳細情報
   */
  public StudentDetail searchStudentById(int id) {
    Student student = repository.searchStudentById(id);
    List<StudentCourse> studentCourseList = repository.searchStudentCourseList(student.getId());
    return new StudentDetail(student, studentCourseList);
  }

  /**
   * 受講生IDを検索します。 受講生の名前に対応する受講生情報を取得した後、その受講生のIDを取得し設定します。
   *
   * @param name 受講生の名前
   * @return 受講生IDリスト
   */
  public List<Integer> searchStudentByName(String name) {
    List<Student> studentList = repository.searchStudentByName(name);
    List<Integer> studentIdList = new ArrayList<>();
    for (Student student : studentList) {
      studentIdList.add(student.getId());
    }
    return studentIdList;
  }

  /**
   * 受講生IDを検索します。 受講生の居住地に対応する受講生情報を取得した後、その受講生のIDを取得し設定します。
   *
   * @param area 受講生の居住地
   * @return 受講生IDリスト
   */
  public List<Integer> searchStudentByArea(String area) {
    List<Student> studentList = repository.searchStudentByArea(area);
    List<Integer> studentIdList = new ArrayList<>();
    for (Student student : studentList) {
      studentIdList.add(student.getId());
    }
    return studentIdList;
  }

  /**
   * 受講生IDを検索します。 受講コースに対応する受講生情報を取得した後、その受講生のIDを取得し設定します。
   *
   * @param courseName 受講コース名
   * @return 受講生IDリスト
   */
  public List<Integer> searchStudentByCourseName(String courseName) {
    List<StudentCourse> studentCourseList = repository.searchCourseListByCourseName(courseName);
    List<Integer> studentIdList = new ArrayList<>();
    for (StudentCourse studentCourse : studentCourseList) {
      studentIdList.add(studentCourse.getStudentId());
    }
    return studentIdList;
  }

  /**
   * 受講生検索です。 リクエストパラメータに対応した受講生情報を取得し設定します。
   *
   * @param name       受講生の名前
   * @param area       受講生の居住地
   * @param courseName 受講コース名
   * @return 絞り込み後の受講生詳細情報
   */
  public List<StudentDetail> searchStudent(String name, String area, String courseName) {
    List<Integer> studentIdList = new ArrayList<>();
    for (Student student : repository.searchAllStudents()) {
      studentIdList.add(student.getId());
    }
    if (name != null) {
      studentIdList.retainAll(searchStudentByName(name));
    }
    if (area != null) {
      studentIdList.retainAll(searchStudentByArea(area));
    }
    if (courseName != null) {
      studentIdList.retainAll(searchStudentByCourseName(courseName));
    }

    if (studentIdList.isEmpty()) {
      throw new InvalidStudentIdListException("検索結果に一致するものが見つかりませんでした。");
    }

    List<Student> studentList = new ArrayList<>();
    for (Integer studentId : studentIdList) {
      studentList.add(repository.searchStudentById(studentId));
    }
    List<StudentCourse> studentCourseList = repository.searchStudentCourseList(studentIdList);
    return converter.convertStudentDetails(studentList, studentCourseList);
  }

  /**
   * 受講生のコース情報の検索を行います。
   * すべてのコース情報が表示されます。
   *
   * @return 受講生コース一覧のリスト(全件)
   */
  public List<StudentCourse> searchStudentCourseList() {
    return repository.searchAllStudentCourseList();
  }

  /**
   * 受講生情報を登録します。
   * 受講生情報と受講生コース情報を個別に登録します。
   *
   * @param studentDetail 受講生詳細
   * @return 登録情報を付与した受講生詳細
   */
  @Transactional
  public StudentDetail registerStudent(StudentDetail studentDetail) {
    Student student = studentDetail.getStudent();

    repository.insert(student);
    studentDetail.getStudentCourseList().forEach(studentCourse -> {
      initStudentsCourse(studentCourse, student.getId());
      repository.insertCourse(studentCourse);
    });
    return studentDetail;
  }

  /**
   * 受講生コース情報を登録する際の初期情報を設定する。
   *
   * @param studentCourse 受講生コース情報
   * @param id 受講生のID
   */
  void initStudentsCourse(StudentCourse studentCourse, int id) {
    LocalDateTime now = LocalDateTime.now();

    studentCourse.setStudentId(id);
    studentCourse.setStartAt(now);
    studentCourse.setEndAt(now.plusMonths(2));
  }

  /**
   * 受講生詳細の更新を行います。
   * 受講生情報と受講生コース情報をそれぞれ更新します。
   *
   * @param studentDetail 受講生詳細
   */
  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    repository.updateStudent(studentDetail.getStudent());
    studentDetail.getStudentCourseList()
        .forEach(studentCourse -> repository.updateStudentCourse(studentCourse));
  }
}
