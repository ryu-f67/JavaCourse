package student.management.StudentManagement;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM student WHERE name = #{name}")
  Student searchByName(String name);

  @Select("SELECT * FROM student")
  List<Student> showStudentTable();

  @Insert("INSERT student values(#{name},#{age},#{bloodType})")
  void registerStudent(String name, int age, String bloodType);

  @Update("UPDATE student SET age = #{age}, blood_type = #{bloodType} WHERE name = #{name}")
  void updateStudent(String name, int age, String bloodType);

  @Delete("DELETE FROM student WHERE name = #{name}")
  void deleteStudent(String name);
}
