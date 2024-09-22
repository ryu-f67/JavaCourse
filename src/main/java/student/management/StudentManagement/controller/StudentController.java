package student.management.StudentManagement.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import student.management.StudentManagement.data.StudentCourse;
import student.management.StudentManagement.domain.StudentDetail;
import student.management.StudentManagement.exception.TestException;
import student.management.StudentManagement.service.StudentService;

/**
 * 受講生の検索や登録、更新を行うREST APIとして実行されるControllerです。
 */
@Validated
@RestController
public class StudentController {

  private StudentService service;

  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  /**
   * 全受講生の検索を行います。
   * 論理削除を行った受講生も表示されます。
   *
   * @return 受講生一覧のリスト(全件)
   */
  @GetMapping("/allStudentsList")
  public List<StudentDetail> getAllStudentList() {

    return service.searchAllStudentList();
  }

  /**
   * 全受講生の検索を行います。
   * 論理削除を行った受講生は表示されません。
   *
   * @return 受講生一覧のリスト(全件)
   */
  @GetMapping("/studentsList")
  public List<StudentDetail> getStudentList() {

    return service.searchStudentList();
  }

  /**
   * 受講生コース情報の検索を行います。
   * 論理削除を行った受講生のコース一覧も表示されます。
   *
   * @return 受講生コース情報一覧のリスト(全件)
   */
  @GetMapping("/studentsCoursesList")
  public List<StudentCourse> getStudentCourseList() {

    return service.searchStudentCourseList();
  }

  /**
   * 受講生を登録します。
   *
   * @param studentDetail 受講生詳細情報
   * @return 受講生詳細情報
   */
  @PostMapping("/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent(
      @RequestBody @Valid StudentDetail studentDetail) {
    StudentDetail responseStudentDetail = service.registerStudent(studentDetail);
    return ResponseEntity.ok(responseStudentDetail);
  }

  /**
   * 受講生を検索します。
   * クエリパラメータに対応する受講生の情報を表示します。
   *
   * @param name 受講生の名前
   * @param area 受講生の居住地
   * @param courseName 受講コース名
   * @return 受講生詳細情報
   */
  @GetMapping("/student")
  public List<StudentDetail> getStudent(
      @RequestParam(name = "id", required = false) Integer id,
      @RequestParam(name = "name", required = false) String name,
      @RequestParam(name = "area", required = false) String area,
      @RequestParam(name = "course", required = false) String courseName) {
    return service.searchStudent(id, name, area, courseName);
  }

  /**
   * 受講生情報の更新をします。
   * キャンセルフラグもここで操作します。(論理削除)
   *
   * @param studentDetail　受講生詳細情報
   * @return 受講生詳細情報
   */
  @PutMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(@RequestBody @Valid StudentDetail studentDetail) {
    service.updateStudent(studentDetail);
    return ResponseEntity.ok("更新処理が完了しました。");
  }

  // 例外発生用のメソッド
  @GetMapping("/testException")
  public List<StudentDetail> raiseException() throws TestException {
    throw new TestException("errorが発生しました。");
  }

}
