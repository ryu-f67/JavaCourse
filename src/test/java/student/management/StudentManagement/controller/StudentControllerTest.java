package student.management.StudentManagement.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import student.management.StudentManagement.data.Student;
import student.management.StudentManagement.service.StudentService;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private StudentService service;

  private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @Test
  void 全受講生の検索ができて空のリストがかえってくること_論理削除を表示() throws Exception {
    mockMvc.perform(get("/allStudentsList"))
        .andExpect(status().isOk())
        .andExpect(content().json("[]"));

    verify(service, times(1)).searchAllStudentList();
  }

  @Test
  void 全受講生の検索ができて空のリストがかえってくること_論理削除を非表示() throws Exception {
    mockMvc.perform(get("/allStudentsList"))
        .andExpect(status().isOk())
        .andExpect(content().json("[]"));

    verify(service, times(1)).searchAllStudentList();
  }

  @Test
  void 受講生の検索ができること() throws Exception {
    int id = 999;
    mockMvc.perform(get("/student/{id}", id))
        .andExpect(status().isOk());

    verify(service, times(1)).searchStudent(id);
  }

  @Test
  void 受講生情報の登録が実行できて空で返ってくること() throws Exception {
    mockMvc.perform(post("/registerStudent").contentType(MediaType.APPLICATION_JSON).content(
            """
                {
                    "student":{
                        "id":8,
                        "name":"佐藤　寿也",
                        "furigana":"さとう　としや",
                        "nickname":"トシ",
                        "mail":"toshiya.sato@example.com",
                        "area":"神奈川県",
                        "age":"30",
                        "gender":"男性",
                        "remark":""
                    },
                    "studentCourseList":[
                        {
                            "courseName":"AWS"
                        }
                    ]
                }
                """
        ))
        .andExpect(status().isOk());

    verify(service, times(1)).registerStudent(any());
  }

  @Test
  void 受講生情報の更新が実行できて空で返ってくること() throws Exception {
    mockMvc.perform(put("/updateStudent").contentType(MediaType.APPLICATION_JSON).content(
            """
                {
                    "student":{
                        "id":5,
                        "name":"高橋 翔太",
                        "furigana":"たかはし しょうた",
                        "nickname":"ショウタ",
                        "mail":"takahashi.shota@example.com",
                        "area":"石川県",
                        "age":"30",
                        "gender":"男性",
                        "remark":"",
                        "deleted": false
                    },
                    "studentCourseList": [
                        {
                            "id": 6,
                            "studentId": 5,
                            "courseName": "Java",
                            "startAt": "2024-04-15T11:00:00",
                            "endAt": "2024-06-15T19:00:00"
                        }
                    ]
                }
                """
        ))
        .andExpect(status().isOk());

    verify(service, times(1)).updateStudent(any());
  }

  @Test
  void 受講生情報の例外APIが実行できてステータスが400で返ってくること() throws Exception {
    mockMvc.perform(get("/testException"))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string("errorが発生しました。"));
  }

  @Test
  void 受講生詳細の受講生で適切な値を入力したときに入力チェックに異常が発生しないこと() {
    Student student = new Student();
    student.setId(1);
    student.setName("中田 翔");
    student.setFurigana("なかた しょう");
    student.setMail("test@example.com");
    student.setAge(35);

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(0);

  }

  @Test
  void 受講生詳細の受講生でメールアドレスに異なる形式を入力したときに入力チェックに掛かること() {
    Student student = new Student();
    student.setId(1);
    student.setName("中田 翔");
    student.setFurigana("なかた しょう");
    student.setMail("testAddress");
    student.setAge(35);

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(1);

  }


}