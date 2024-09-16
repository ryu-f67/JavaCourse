package student.management.StudentManagement.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import student.management.StudentManagement.controller.converter.StudentConverter;
import student.management.StudentManagement.data.Student;
import student.management.StudentManagement.data.StudentCourse;
import student.management.StudentManagement.domain.StudentDetail;
import student.management.StudentManagement.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  @Mock
  private StudentRepository repository;

  @Mock
  private StudentConverter converter;

  private StudentService sut;

  @BeforeEach
  void before() {
    sut = new StudentService(repository, converter);
  }

  @Test
  void 全受講生の一覧検索が動作すること_論理削除を表示() {
    List<Student> studentList = new ArrayList<>();
    List<StudentCourse> studentCourseList = new ArrayList<>();
    when(repository.searchAllStudents()).thenReturn(studentList);
    when(repository.searchAllStudentCourseList()).thenReturn(studentCourseList);

    sut.searchAllStudentList();

    verify(repository, times(1)).searchAllStudents();
    verify(repository, times(1)).searchAllStudentCourseList();
    verify(converter, times(1)).convertStudentDetails(studentList, studentCourseList);

  }

  @Test
  void 全受講生の一覧検索が動作すること_論理削除を非表示() {
    List<Student> studentList = new ArrayList<>();
    List<StudentCourse> studentCourseList = new ArrayList<>();
    when(repository.search()).thenReturn(studentList);
    when(repository.searchAllStudentCourseList()).thenReturn(studentCourseList);

    sut.searchStudentList();

    verify(repository, times(1)).search();
    verify(repository, times(1)).searchAllStudentCourseList();
    verify(converter, times(1)).convertStudentDetails(studentList, studentCourseList);

  }

  @Test
  void IDに対応する受講生情報の検索が動作すること() {
    int id = 999;
    Student student = new Student();
    student.setId(id);
    when(repository.searchStudent(id)).thenReturn(student);
    when(repository.searchStudentCourseList(id)).thenReturn(new ArrayList<>());

    StudentDetail excepted = new StudentDetail(student, new ArrayList<>());

    StudentDetail actual = sut.searchStudent(id);

    verify(repository, times(1)).searchStudent(id);
    verify(repository, times(1)).searchStudentCourseList(id);
    assertThat(actual.getStudent().getId()).isEqualTo(excepted.getStudent().getId());

  }

  @Test
  void 受講生のコース情報の検索が動作すること() {
    sut.searchStudentCourseList();
    verify(repository, times(1)).searchAllStudentCourseList();

  }

  @Test
  void 受講生情報の登録が動作すること() {
    Student student = new Student();
    StudentCourse studentCourse = new StudentCourse();
    List<StudentCourse> studentCourseList = List.of(studentCourse);
    StudentDetail studentDetail = new StudentDetail(student, studentCourseList);

    sut.registerStudent(studentDetail);

    verify(repository, times(1)).insert(student);
    verify(repository, times(1)).insertCourse(studentCourse);

  }

  @Test
  void 受講生情報の登録_初期化処理が行われること() {
    int id = 999;
    Student student = new Student();
    student.setId(id);
    StudentCourse studentCourse = new StudentCourse();

    sut.initStudentsCourse(studentCourse, student.getId());

    assertThat(student.getId()).isEqualTo(id);
    assertThat(studentCourse.getStartAt().getHour()).isEqualTo(LocalDateTime.now().getHour());
    assertThat(studentCourse.getEndAt().getMonth()).isEqualTo(
        LocalDateTime.now().plusMonths(2).getMonth());

  }

  @Test
  void 受講生情報の更新が動作すること() {
    Student student = new Student();
    StudentCourse studentCourse = new StudentCourse();
    List<StudentCourse> studentCourseList = List.of(studentCourse);
    StudentDetail studentDetail = new StudentDetail(student, studentCourseList);

    sut.updateStudent(studentDetail);

    verify(repository, times(1)).updateStudent(student);
    verify(repository, times(1)).updateStudentCourse(studentCourse);

  }

}