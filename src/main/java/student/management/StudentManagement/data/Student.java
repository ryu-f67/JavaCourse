package student.management.StudentManagement.data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {

  @NotNull
  private int id;

  @NotBlank
  private String name;

  @NotBlank
  private String furigana;

  private String nickname;

  @NotBlank
  @Email
  private String mail;

  private String area;

  @NotNull
  @Positive
  private int age;

  private String gender;

  private String remark;

  private boolean isDeleted;
}
