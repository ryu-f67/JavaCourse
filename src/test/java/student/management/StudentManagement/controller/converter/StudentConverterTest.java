package student.management.StudentManagement.controller.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import student.management.StudentManagement.data.Student;
import student.management.StudentManagement.data.StudentCourse;
import student.management.StudentManagement.domain.StudentDetail;

class StudentConverterTest {

  private StudentConverter sut;

  @BeforeEach
  void before() {
    sut = new StudentConverter();
  }

  @Test
  void 受講生情報のリストと受講生コース情報のリストを渡して受講生詳細のリストが作成できること() {
    Student student = createStudent();

    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setId(1);
    studentCourse.setStudentId(10);
    studentCourse.setCourseName("AWS");
    studentCourse.setStartAt(LocalDateTime.now());
    studentCourse.setEndAt(LocalDateTime.now().plusMonths(2));

    List<Student> studentList = List.of(student);
    List<StudentCourse> studentCourseList = List.of(studentCourse);

    List<StudentDetail> actual = sut.convertStudentDetails(studentList, studentCourseList);

    assertThat(actual.get(0).getStudent()).isEqualTo(student);
    assertThat(actual.get(0).getStudentCourseList()).isEqualTo(studentCourseList);

  }

  @Test
  void 受講生情報のリストと受講生コース情報のリストを渡したときに紐づかない受講生コース情報は除外されること() {
    Student student = createStudent();

    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setId(1);
    studentCourse.setStudentId(100);
    studentCourse.setCourseName("AWS");
    studentCourse.setStartAt(LocalDateTime.now());
    studentCourse.setEndAt(LocalDateTime.now().plusMonths(2));

    List<Student> studentList = List.of(student);
    List<StudentCourse> studentCourseList = List.of(studentCourse);

    List<StudentDetail> actual = sut.convertStudentDetails(studentList, studentCourseList);

    assertThat(actual.get(0).getStudent()).isEqualTo(student);
    assertThat(actual.get(0).getStudentCourseList()).isEmpty();

  }

  private static Student createStudent() {
    Student student = new Student();
    student.setId(10);
    student.setName("中田 翔");
    student.setFurigana("なかた しょう");
    student.setNickname("たいしょう");
    student.setMail("test@example.com");
    student.setArea("愛知県");
    student.setAge(35);
    student.setGender("男性");
    student.setRemark("");
    student.setDeleted(false);
    return student;
  }

}
