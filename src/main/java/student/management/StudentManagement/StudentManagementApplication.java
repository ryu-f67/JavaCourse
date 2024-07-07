package student.management.StudentManagement;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class StudentManagementApplication {

	private String name = "Ryuya";
	private String age = "30";

	private Map<String, String> favorite = new HashMap<>();

	public static void main(String[] args) {
		SpringApplication.run(StudentManagementApplication.class, args);
	}

	@GetMapping("/studentInfo")
	public String getStudentInfo() {
		StringBuilder sb = new StringBuilder();
		sb.append("I am "+ name +"," + age + " years old.");
		sb.append(System.lineSeparator());
		if (!favorite.isEmpty()) {
			favorite.forEach((key, value) -> sb.append("My favorite " + key + " is " + value + "." + System.lineSeparator()));
		}
		return sb.toString();
	}

	@PostMapping("/studentInfo")
	public void postStudentInfo(String name, String age) {
		this.name = name;
		this.age = age;
	}

	@PostMapping("/age")
	public void updateAge(String age) {
		this.age = age;
	}

	@PostMapping("/name")
	public void updateName(String name) {
		this.name = name;
	}

	@PostMapping("/favorite")
	public void addFavorite(String category, String things) {
		this.favorite.put(category, things);
	}
}
