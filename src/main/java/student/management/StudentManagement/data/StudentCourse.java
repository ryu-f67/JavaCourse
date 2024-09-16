package student.management.StudentManagement.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentCourse {

  @NotNull
  private int id;

  @NotNull
  private int studentId;

  @NotBlank
  private String courseName;

  private LocalDateTime startAt;

  private LocalDateTime endAt;
}
