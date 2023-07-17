DELETE from users;
DELETE from request;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

INSERT INTO users(id, email, first_name, last_name, user_password, user_role, avatar_uri)
VALUES('22a161e2-c34e-45af-be8c-27ac5d86b1b3','sami@123.com', 'sami', 'mrini', 'admin123', 'USER', 'http://localhost:9000/language-web/dev/profile.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=EU6P4TFPUTG9MM50VZGS%2F20230610%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20230610T213306Z&X-Amz-Expires=604800&X-Amz-Security-Token=eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJhY2Nlc3NLZXkiOiJFVTZQNFRGUFVURzlNTTUwVlpHUyIsImV4cCI6MTY4NjQ3NTY4NSwicGFyZW50IjoiZGV2In0.uPbDQcpSh_NM6tT1tC1pnqbQY9vIdDUbSKlavA__kitPToOf7HWOsqaQClqQ3z1mnlOEgCEDuC2st6DFZBi7Xg&X-Amz-SignedHeaders=host&versionId=null&X-Amz-Signature=5fc5d7727c003091314b075ecb345f01e295b5e07d69980b3d226a47517b324f');

INSERT INTO users(id, email, first_name, last_name, user_password, user_role, avatar_uri)
VALUES('943050df-bb81-488d-9420-11ef7ebef3fa', 'sami@1234.com', 'boris', 'djeki', 'admin123', 'USER', 'http://localhost:9000/language-web/dev/profile.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=EU6P4TFPUTG9MM50VZGS%2F20230610%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20230610T213306Z&X-Amz-Expires=604800&X-Amz-Security-Token=eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJhY2Nlc3NLZXkiOiJFVTZQNFRGUFVURzlNTTUwVlpHUyIsImV4cCI6MTY4NjQ3NTY4NSwicGFyZW50IjoiZGV2In0.uPbDQcpSh_NM6tT1tC1pnqbQY9vIdDUbSKlavA__kitPToOf7HWOsqaQClqQ3z1mnlOEgCEDuC2st6DFZBi7Xg&X-Amz-SignedHeaders=host&versionId=null&X-Amz-Signature=5fc5d7727c003091314b075ecb345f01e295b5e07d69980b3d226a47517b324f');

INSERT INTO users(id, email, first_name, last_name, user_password, user_role, avatar_uri)
VALUES('f675c9f4-2af5-4834-862e-2e8aab437392', 'sami@1235.com', 'Pablo', 'Escobar', 'admin123', 'EVALUATOR', 'http://localhost:9000/language-web/dev/profile.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=EU6P4TFPUTG9MM50VZGS%2F20230610%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20230610T213306Z&X-Amz-Expires=604800&X-Amz-Security-Token=eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJhY2Nlc3NLZXkiOiJFVTZQNFRGUFVURzlNTTUwVlpHUyIsImV4cCI6MTY4NjQ3NTY4NSwicGFyZW50IjoiZGV2In0.uPbDQcpSh_NM6tT1tC1pnqbQY9vIdDUbSKlavA__kitPToOf7HWOsqaQClqQ3z1mnlOEgCEDuC2st6DFZBi7Xg&X-Amz-SignedHeaders=host&versionId=null&X-Amz-Signature=5fc5d7727c003091314b075ecb345f01e295b5e07d69980b3d226a47517b324f');

INSERT INTO language_grades(id, user_id, emitter_user_id, ref_language, translated_language, grade)
VALUES(28888, '22a161e2-c34e-45af-be8c-27ac5d86b1b3', '22a161e2-c34e-45af-be8c-27ac5d86b1b3', 'ENGLISH', 'FRENCH', 95);

INSERT INTO language_grades(id, user_id, emitter_user_id, ref_language, translated_language, grade)
VALUES(38888, '22a161e2-c34e-45af-be8c-27ac5d86b1b3', '943050df-bb81-488d-9420-11ef7ebef3fa', 'ENGLISH', 'FRENCH', 89);


INSERT INTO language_grades(id, user_id, emitter_user_id, ref_language, translated_language, grade)
VALUES(333333, '943050df-bb81-488d-9420-11ef7ebef3fa', '943050df-bb81-488d-9420-11ef7ebef3fa', 'ARABIC', 'FRENCH', 55);

INSERT INTO language_grades(id, user_id, emitter_user_id, ref_language, translated_language, grade)
VALUES(333339, '943050df-bb81-488d-9420-11ef7ebef3fa', '943050df-bb81-488d-9420-11ef7ebef3fa', 'ENGLISH', 'FRENCH', 75);

INSERT INTO language_grades(id, user_id, emitter_user_id, ref_language, translated_language, grade)
VALUES(3838383, '943050df-bb81-488d-9420-11ef7ebef3fa', '22a161e2-c34e-45af-be8c-27ac5d86b1b3', 'ARABIC', 'FRENCH', 89);

INSERT INTO request(id, translated_language, source_language, status, user_id, price, file_path, due_date, name)
VALUES(2343,  'ARABIC', 'ENGLISH', 'PENDING', '943050df-bb81-488d-9420-11ef7ebef3fa', 0.5, 'dev/history.pdf', current_timestamp(0), 'history');

INSERT INTO request(id, translated_language, source_language, status, user_id, price, file_path, name)
VALUES(2373,  'FRENCH', 'ENGLISH', 'PENDING', '943050df-bb81-488d-9420-11ef7ebef3fa', 0.33, 'dev/history.pdf', 'history');

INSERT INTO request(id, translated_language, source_language, status, user_id, price, file_path, name)
VALUES(23721, 'FRENCH', 'ENGLISH', 'PENDING', '22a161e2-c34e-45af-be8c-27ac5d86b1b3', 0.75, 'dev/pote.txt', 'pote');

INSERT INTO contract_request(id, status, request_id, user_id)
VALUES(133, 'PENDING', 2343, '22a161e2-c34e-45af-be8c-27ac5d86b1b3');

INSERT INTO contract_request(id, status, request_id, user_id)
VALUES(244, 'PENDING', 2373, '22a161e2-c34e-45af-be8c-27ac5d86b1b3');

INSERT INTO contract_request(id, status, request_id, user_id)
VALUES(333, 'ACCEPTED', 23721, '943050df-bb81-488d-9420-11ef7ebef3fa');

INSERT INTO contract(status, contracted_user_id, request_id)
VALUES('PENDING', '943050df-bb81-488d-9420-11ef7ebef3fa', 23721);