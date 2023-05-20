CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

--users

CREATE TABLE users (
    id uuid PRIMARY KEY,
    first_name varchar(50),
    last_name varchar(50),
    email varchar(100) NOT NULL,
    user_password varchar(252) NOT NULL,
    created_at timestamp(6),
    user_role varchar(10),
	UNIQUE (email)
);

--texts

CREATE TABLE texts(
	id BIGSERIAL PRIMARY KEY,
	text_content varchar(5120),
	text_link varchar(400),
	text_language varchar(15) NOT NULL,
	created_at timestamp(6),
	user_id uuid NOT NULL REFERENCES users(id),
	UNIQUE(user_id)
);

--languages

CREATE TABLE language_grades(
    id BIGSERIAL PRIMARY KEY,
    ref_language varchar(25) NOT NULL,
    user_id uuid NOT NULL REFERENCES users(id),
    emitter_user_id uuid NOT NULL REFERENCES users(id),
    grade float(53),
    UNIQUE(user_id, emitter_user_id, ref_language)
)