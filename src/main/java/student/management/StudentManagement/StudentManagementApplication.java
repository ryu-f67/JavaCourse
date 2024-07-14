package student.management.StudentManagement;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class StudentManagementApplication {

	@Autowired
	private StudentRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(StudentManagementApplication.class, args);
	}

	// studentsテーブル内すべてを取得
	@GetMapping("/studentsList")
	public List<Student> getStudentList() {
		return repository.findAllStudents();
	}

	// students_coursesテーブル内すべてを取得
	@GetMapping("/studentsCoursesList")
	public List<StudentCourse> getStudentCourseList() {
		return repository.findAllStudentCourses();
	}
}
