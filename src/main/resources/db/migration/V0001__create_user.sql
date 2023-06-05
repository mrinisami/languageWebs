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

--stored-content

CREATE TABLE stored_content(
	id BIGSERIAL PRIMARY KEY,
	source_language varchar(25) NOT NULL,
	name varchar(50) NOT NULL,
	created_at timestamp(6),
	modified_at timestamp(6),
	user_id uuid NOT NULL REFERENCES users(id),
	UNIQUE(name, user_id)
);

--languages

CREATE TABLE language_grades(
    id BIGSERIAL PRIMARY KEY,
    ref_language varchar(25) NOT NULL,
    translated_language varchar(25) NOT NULL,
    user_id uuid NOT NULL REFERENCES users(id),
    emitter_user_id uuid NOT NULL REFERENCES users(id),
    grade float(53),
    UNIQUE(user_id, emitter_user_id, ref_language, translated_language)
);

--requests

CREATE TABLE request(
    id BIGSERIAL PRIMARY KEY,
    source_language varchar(25) NOT NULL,
    translated_language varchar(25) NOT NULL,
    status varchar(10) NOT NULL,
    content_id BIGSERIAL NOT NULL REFERENCES stored_content(id),
    created_at timestamp(6),
    modified_at timestamp(6),
    price float(53)
)