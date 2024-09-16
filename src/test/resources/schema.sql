CREATE TABLE IF NOT EXISTS students
(
    id int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    furigana VARCHAR(255) NOT NULL,
    nickname VARCHAR(255),
    mail VARCHAR(255) NOT NULL,
    area VARCHAR(255),
    age INT NOT NULL,
    gender VARCHAR(255),
    remark VARCHAR(255),
    is_deleted boolean NOT NULL
);

CREATE TABLE IF NOT EXISTS students_courses
(
    id int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    student_id int NOT NULL,
    course_name VARCHAR(36) NOT NULL,
    start_at TIMESTAMP,
    end_at TIMESTAMP
);