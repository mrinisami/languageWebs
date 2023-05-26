DELETE FROM language_grades;
DELETE from users;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

INSERT INTO users(id, email, first_name, last_name, user_password, user_role)
VALUES('22a161e2-c34e-45af-be8c-27ac5d86b1b3','sami@123.com', 'sami', 'mrini', 'admin123', 'USER');

INSERT INTO users(id, email, first_name, last_name, user_password, user_role)
VALUES('943050df-bb81-488d-9420-11ef7ebef3fa', 'sami@1234.com', 'sami', 'mrini', 'admin123', 'USER');

INSERT INTO users(id, email, first_name, last_name, user_password, user_role)
VALUES('f675c9f4-2af5-4834-862e-2e8aab437392', 'sami@1235.com', 'sami', 'mrini', 'admin123', 'EVALUATOR');

INSERT INTO language_grades(id, user_id, emitter_user_id, ref_language, translated_language, grade)
VALUES(1, '22a161e2-c34e-45af-be8c-27ac5d86b1b3', '22a161e2-c34e-45af-be8c-27ac5d86b1b3', 'ENGLISH', 'FRENCH', 95);

INSERT INTO language_grades(id, user_id, emitter_user_id, ref_language, translated_language, grade)
VALUES(2, '22a161e2-c34e-45af-be8c-27ac5d86b1b3', '943050df-bb81-488d-9420-11ef7ebef3fa', 'ENGLISH', 'FRENCH', 89);


INSERT INTO language_grades(id, user_id, emitter_user_id, ref_language, translated_language, grade)
VALUES(3, '943050df-bb81-488d-9420-11ef7ebef3fa', '943050df-bb81-488d-9420-11ef7ebef3fa', 'ARABIC', 'FRENCH', 55);

INSERT INTO language_grades(id, user_id, emitter_user_id, ref_language, translated_language, grade)
VALUES(4, '943050df-bb81-488d-9420-11ef7ebef3fa', '22a161e2-c34e-45af-be8c-27ac5d86b1b3', 'ARABIC', 'FRENCH', 89);