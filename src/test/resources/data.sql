INSERT INTO students (name, furigana, nickname, mail, area, age, gender, remark, is_deleted)
VALUES ('山田太郎', 'やまだたろう', 'たろう', 'taro@example.com', '東京', 30, '男性', '', false),
       ('田中花子', 'たなかはなこ', 'はな', 'hanako@example.com', '大阪', 25, '女性', '', true);

INSERT INTO students_courses (student_id, course_name, start_at, end_at)
VALUES (1, 'AWS', '2024-04-01T09:00:00', '2024-06-01T09:00:00'),
       (2, 'JAVA', '2024-08-01T09:00:00', '2024-10-01T09:00:00'),
       (1, 'JAVA', '2024-06-01T09:00:00', '2024-08-01T09:00:00');
