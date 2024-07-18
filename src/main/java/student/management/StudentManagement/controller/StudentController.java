package student.management.StudentManagement.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import student.management.StudentManagement.controller.converter.StudentConverter;
import student.management.StudentManagement.data.Student;
import student.management.StudentManagement.data.StudentCourse;
import student.management.StudentManagement.domain.StudentDetail;
import student.management.StudentManagement.service.StudentService;

@Controller
public class StudentController {

  private StudentService service;
  private StudentConverter converter;

  @Autowired
  public StudentController(StudentService service, StudentConverter converter) {
    this.service = service;
    this.converter = converter;
  }

  @GetMapping("/studentsList")
  public String getStudentList(Model model) {
    List<Student> students = service.searchStudentList();
    List<StudentCourse> studentCourses = service.searchStudentCourseList();

    model.addAttribute("studentList", converter.convertStudentDetails(students, studentCourses));
    return "studentList";
  }

  // students_coursesテーブル内すべてを取得
  @GetMapping("/studentsCoursesList")
  public String getStudentCourseList(Model model) {
    List<StudentCourse> studentCourses = service.searchStudentCourseList();

    model.addAttribute("studentsCourseList", studentCourses);
    return "studentsCourseList";
  }

  @GetMapping("/newStudent")
  public String newStudent(Model model) {
    model.addAttribute("studentDetail", new StudentDetail());
    return "registerStudent";
  }

  @PostMapping("/registerStudent")
  public String registerStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result) {
    if (result.hasErrors()) {
      return "registerStudent";
    }
    System.out.println("ID:" + studentDetail.getStudent().getId());
    System.out.println("名前:" + studentDetail.getStudent().getName());
    System.out.println("ふりがな:" + studentDetail.getStudent().getFurigana());
    System.out.println("ニックネーム:" + studentDetail.getStudent().getNickname());
    System.out.println("メールアドレス:" + studentDetail.getStudent().getMail());
    System.out.println("地域:" + studentDetail.getStudent().getArea());
    System.out.println("年齢:" + studentDetail.getStudent().getAge());
    System.out.println("性別:" + studentDetail.getStudent().getGender());
    System.out.println("備考:" + studentDetail.getStudent().getRemark());

    service.registerStudent(studentDetail);

    return "redirect:/studentsList";
  }

}
