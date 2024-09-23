package student.management.StudentManagement.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
  void IDに対応する受講生IDの検索が動作すること() {
    Student student = createStudent();
    when(repository.searchStudentById(student.getId())).thenReturn(student);

    List<Integer> excepted = List.of(student.getId());

    List<Integer> actual = sut.searchStudentById(student.getId());

    verify(repository, times(1)).searchStudentById(student.getId());
    assertThat(actual).isEqualTo(excepted);
  }

  @Test
  void 受講生の名前に対応する受講生IDの検索が動作すること() {
    Student student = createStudent();
    List<Student> studentList = List.of(student);
    when(repository.searchStudentByName(student.getName())).thenReturn(studentList);

    List<Integer> excepted = List.of(student.getId());

    List<Integer> actual = sut.searchStudentByName(student.getName());

    verify(repository, times(1)).searchStudentByName(student.getName());
    assertThat(actual).isEqualTo(excepted);
  }

  @Test
  void 受講生の居住地に対応する受講生情報の検索が動作すること() {
    Student student = createStudent();
    List<Student> studentList = List.of(student);
    when(repository.searchStudentByArea(student.getArea())).thenReturn(studentList);

    List<Integer> excepted = List.of(student.getId());

    List<Integer> actual = sut.searchStudentByArea(student.getArea());

    verify(repository, times(1)).searchStudentByArea(student.getArea());
    assertThat(actual).isEqualTo(excepted);
  }

  @Test
  void 受講コースに対応する受講生情報の検索が動作すること() {
    StudentCourse studentCourse = createStudentCourse();
    List<StudentCourse> studentCourseList = List.of(studentCourse);
    when(repository.searchCourseListByCourseName(studentCourse.getCourseName())).thenReturn(
        studentCourseList);

    List<Integer> excepted = List.of(studentCourse.getStudentId());

    List<Integer> actual = sut.searchStudentByCourseName(studentCourse.getCourseName());

    verify(repository, times(1)).searchCourseListByCourseName(studentCourse.getCourseName());
    assertThat(actual).isEqualTo(excepted);
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
    Student student = createStudent();
    StudentCourse studentCourse = new StudentCourse();

    sut.initStudentsCourse(studentCourse, student.getId());

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

  private static Student createStudent() {
    Student student = new Student();
    student.setId(999);
    student.setName("名前");
    student.setFurigana("なまえ");
    student.setArea("居住地");
    return student;
  }

  private static StudentCourse createStudentCourse() {
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setId(9999);
    studentCourse.setStudentId(999);
    studentCourse.setCourseName("コース");
    return studentCourse;
  }

}
