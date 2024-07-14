package student.management.StudentManagement.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import student.management.StudentManagement.data.Student;
import student.management.StudentManagement.data.StudentCourse;
import student.management.StudentManagement.service.StudentService;

@RestController
public class StudentContoroller {

  private StudentService service;

  @Autowired
  public StudentContoroller(StudentService service) {
    this.service = service;
  }

  // studentsテーブル内すべてを取得
  @GetMapping("/studentsList")
  public List<Student> getStudentList() {
    return service.searchStudentList();
  }

  // students_coursesテーブル内すべてを取得
  @GetMapping("/studentsCoursesList")
  public List<StudentCourse> getStudentCourseList() {
    return service.searchStudentCourseList();
  }

}
