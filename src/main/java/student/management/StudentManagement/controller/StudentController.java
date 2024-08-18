package student.management.StudentManagement.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import student.management.StudentManagement.data.StudentCourse;
import student.management.StudentManagement.domain.StudentDetail;
import student.management.StudentManagement.service.StudentService;

/**
 * 受講生の検索や登録、更新を行うREST APIとして実行されるControllerです。
 */
@RestController
public class StudentController {

  private StudentService service;

  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  /**
   * 全受講生の検索を行います。 論理削除を行った受講生も表示されます。
   *
   * @return 受講生一覧(全件)
   */
  @GetMapping("/allStudentsList")
  public List<StudentDetail> getAllStudentList() {

    return service.searchAllStudentList();
  }

  /**
   * 全受講生の検索を行います。
   * 論理削除を行った受講生は表示されません。
   *
   * @return 受講生一覧(全件)
   */
  @GetMapping("/studentsList")
  public List<StudentDetail> getStudentList() {

    return service.searchStudentList();
  }

  /**
   * 受講生コース情報の検索を行います。 論理削除を行った受講生のコース一覧も表示されます。
   *
   * @return 受講生コース情報一覧(全件)
   */
  @GetMapping("/studentsCoursesList")
  public List<StudentCourse> getStudentCourseList() {

    return service.searchStudentCourseList();
  }

  /**
   * 受講生を登録します。
   *
   * @param studentDetail 受講生と受講コースの情報
   * @return 受講生と受講コースの情報
   */
  @PostMapping("/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent(@RequestBody StudentDetail studentDetail) {
    StudentDetail responseStudentDetail = service.registerStudent(studentDetail);
    return ResponseEntity.ok(responseStudentDetail);
  }

  /**
   * 受講生を検索します。
   * IDに対応する受講生の情報を表示します。
   *
   * @param id 受講生ID
   * @return 受講生
   */
  @GetMapping("/student/{id}")
  public StudentDetail getStudent(@PathVariable String id) {
    return service.searchStudent(id);
  }

  /**
   * 受講生情報の更新をします。
   * キャンセルフラグもここで操作します。(論理削除)
   *
   * @param studentDetail　受講生と受講コースの情報
   * @return 受講生と受講コースの情報
   */
  @PutMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(@RequestBody StudentDetail studentDetail) {
    service.updateStudent(studentDetail);
    return ResponseEntity.ok("更新処理が完了しました。");
  }

}
