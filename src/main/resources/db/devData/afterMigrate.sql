DELETE FROM users;
DELETE FROM language_grades;

INSERT INTO users(email, user_password)
VALUES('sami@123.com', 'admin123');

INSERT INTO users(email, user_password, user_role)
VALUES('admin@123.com', 'admin123', 'EVALUATOR');

INSERT INTO users(email, user_password)
VALUES('user2@123.com', 'admin123');


