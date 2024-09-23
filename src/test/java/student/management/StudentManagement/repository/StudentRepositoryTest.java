package student.management.StudentManagement.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import student.management.StudentManagement.data.Student;
import student.management.StudentManagement.data.StudentCourse;

@MybatisTest
class StudentRepositoryTest {

  @Autowired
  private StudentRepository sut;

  @Test
  void 受講生の全件検索が行えること_論理削除を表示() {
    List<Student> actual = sut.searchAllStudents();
    assertThat(actual.size()).isEqualTo(5);
  }

  @Test
  void 受講生の全件検索が行えること_論理削除を非表示() {
    List<Student> actual = sut.search();
    assertThat(actual.size()).isEqualTo(3);
  }

  @Test
  void IDに紐づいた受講生の検索が行えること() {
    Student actual = sut.searchStudentById(1);
    assertThat(actual.getId()).isEqualTo(1);
    assertThat(actual.getName()).isEqualTo("山田太郎");
    assertThat(actual.getFurigana()).isEqualTo("やまだたろう");
    assertThat(actual.getNickname()).isEqualTo("たろう");
    assertThat(actual.getMail()).isEqualTo("taro@example.com");
    assertThat(actual.getArea()).isEqualTo("東京都");
    assertThat(actual.getAge()).isEqualTo(30);
    assertThat(actual.getGender()).isEqualTo("男性");
    assertThat(actual.getRemark()).isEqualTo("");
    assertThat(actual.isDeleted()).isEqualTo(false);
  }

  @Test
  void 受講生の名前に紐づいた受講生の検索が行えること_名前で検索() {
    List<Student> actual = sut.searchStudentByName("山田");
    assertThat(actual.size()).isEqualTo(3);
  }

  @Test
  void 受講生の名前に紐づいた受講生の検索が行えること_ふりがなで検索() {
    List<Student> actual = sut.searchStudentByName("はなこ");
    assertThat(actual.size()).isEqualTo(2);
  }

  @Test
  void 受講生の居住地に紐づいた受講生の検索が行えること() {
    List<Student> actual = sut.searchStudentByArea("東京都");
    assertThat(actual.size()).isEqualTo(2);
  }

  @Test
  void 受講生コース情報の全件検索が行えること() {
    List<StudentCourse> actual = sut.searchAllStudentCourseList();
    assertThat(actual.size()).isEqualTo(8);
  }

  @Test
  void IDに紐づいた受講生コース情報の検索が行えること() {
    List<Integer> studentId = List.of(1);
    List<StudentCourse> actual = sut.searchStudentCourseList(studentId);
    assertThat(actual.size()).isEqualTo(3);
  }

  @Test
  void 受講コース名に紐づいた受講生コース情報の検索が行えること() {
    String courseName = "JAVA";
    List<StudentCourse> actual = sut.searchCourseListByCourseName(courseName);
    assertThat(actual.size()).isEqualTo(3);
  }

  @Test
  void 受講生の登録が行えること() {
    Student student = new Student();
    student.setName("中田 翔");
    student.setFurigana("なかた しょう");
    student.setNickname("たいしょう");
    student.setMail("test@example.com");
    student.setArea("愛知県");
    student.setAge(35);
    student.setGender("男性");
    student.setRemark("");
    student.setDeleted(false);

    sut.insert(student);

    List<Student> actual = sut.searchAllStudents();

    assertThat(actual.size()).isEqualTo(6);
  }

  @Test
  void 受講生コース情報の登録が行えること() {
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setStudentId(2);
    studentCourse.setCourseName("AWS");
    studentCourse.setStartAt(LocalDateTime.parse("2024-08-01T09:00:00"));
    studentCourse.setEndAt(LocalDateTime.parse("2024-10-01T09:00:00"));

    sut.insertCourse(studentCourse);

    List<StudentCourse> actual = sut.searchAllStudentCourseList();

    assertThat(actual.size()).isEqualTo(9);
  }

  @Test
  void 受講生の更新が行えること() {
    Student student = new Student();
    student.setId(1);
    student.setName("山田太郎");
    student.setFurigana("やまだたろう");
    student.setNickname("たろう");
    student.setMail("taro@example.com");
    student.setArea("神奈川");
    student.setAge(31);
    student.setGender("男性");
    student.setRemark("");
    student.setDeleted(false);

    sut.updateStudent(student);

    Student actual = sut.searchStudentById(1);

    assertThat(actual.getArea()).isEqualTo("神奈川");
    assertThat(actual.getAge()).isEqualTo(31);
  }

  @Test
  void 受講生コース情報の更新が行えること() {
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setId(2);
    studentCourse.setCourseName("AWS");
    studentCourse.setStartAt(LocalDateTime.parse("2024-04-01T09:00:00"));
    studentCourse.setEndAt(LocalDateTime.parse("2024-06-01T09:00:00"));

    sut.updateStudentCourse(studentCourse);

    List<StudentCourse> actual = sut.searchStudentCourseList(List.of(2))
        .stream()
        .filter(s -> Objects.equals(s.getId(), 2))
        .toList();

    assertThat(actual.size()).isEqualTo(1);

    assertThat(actual.getFirst().getCourseName()).isEqualTo("AWS");
  }

}
