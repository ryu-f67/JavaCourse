package student.management.StudentManagement;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class StudentManagementApplication {

	@Autowired
	private StudentRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(StudentManagementApplication.class, args);
	}

	// テーブル内すべてを取得
	@GetMapping("/studentTable")
	public List<Student> showStudentTable() {
		return repository.showStudentTable();
	}

	@GetMapping("/student")
	public String getStudent(@RequestParam String name) {
		Student student = repository.searchByName(name);
		return student.getName() + " " + student.getAge() + "歳 " + student.getBloodType() + "型";
	}

	@PostMapping("/student")
	public void registerStudent(String name, int age, String bloodType) {
		repository.registerStudent(name, age, bloodType);
	}

	@PatchMapping("/student")
	public void updateStudent(@RequestParam String name, int age, String bloodType) {
		repository.updateStudent(name, age, bloodType);
	}

	@DeleteMapping("/student")
	public void deleteStudent(String name) {
		repository.deleteStudent(name);
	}
}
