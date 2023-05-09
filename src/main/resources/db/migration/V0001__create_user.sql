CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

--users

CREATE TABLE users (
    id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    first_name varchar(50),
    last_name varchar(50),
    email varchar(100) NOT NULL,
    user_password varchar(252) NOT NULL,
    created_at timestamp(6),
    user_role varchar(10) DEFAULT ('USER'),
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

CREATE TABLE languages(
    id BIGSERIAL PRIMARY KEY,
    name varchar(25) NOT NULL,
    short_name varchar(3) NOT NULL
);

CREATE TABLE languages_spoken(
    id BIGSERIAL PRIMARY KEY,
    language_id BIGSERIAL NOT NULL REFERENCES languages(id),
    user_id uuid NOT NULL REFERENCES users(id),
    own_grade numeric(2, 1),
    admin_grade numeric(2, 1),
    user_grade numeric(2, 1)
)