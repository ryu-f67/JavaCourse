package student.management.StudentManagement.service;

import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import student.management.StudentManagement.data.Student;
import student.management.StudentManagement.data.StudentCourse;
import student.management.StudentManagement.repository.StudentRepository;

@Service
public class StudentService {

  private StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> searchStudentList() {
    List<Student> allStudents = repository.search();
    // 年齢が30代の受講生のみを抽出。
    return allStudents.stream()
        .filter(student -> student.getAge() >= 30 && student.getAge() < 40)
        .toList();
  }

  public List<StudentCourse> searchStudentCourseList() {
    List<StudentCourse> allStudentCourses = repository.searchStudentCourses();
    // "JAVA"コースの情報のみを抽出。
    return allStudentCourses.stream()
        .filter(course -> Objects.equals(course.getCourseName(), "JAVA"))
        .toList();
  }

}
