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
    avatar_uri varchar(600),
	UNIQUE (email)
);

--languages

CREATE TABLE language_grades(
    id BIGSERIAL PRIMARY KEY,
    ref_language varchar(25) NOT NULL,
    translated_language varchar(25) NOT NULL,
    user_id uuid NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    emitter_user_id uuid NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    grade float(53),
    UNIQUE(user_id, emitter_user_id, ref_language, translated_language)
);

--requests

CREATE TABLE request(
    id BIGSERIAL PRIMARY KEY,
    source_language varchar(25) NOT NULL,
    translated_language varchar(25) NOT NULL,
    status varchar(10) NOT NULL,
    user_id uuid NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    name varchar(30),
    file_path varchar(100),
    created_at timestamp(6),
    modified_at timestamp(6),
    estimated_time float(53),
    nb_words integer,
    description varchar(4000),
    due_date timestamp(6),
    price float(53)
);

--contract

CREATE TABLE contract(
    id BIGSERIAL PRIMARY KEY,
    status varchar(10) NOT NULL,
    contracted_status varchar(10),
    contracted_user_id uuid NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    file_path varchar(100),
    created_at timestamp(6),
    modified_at timestamp(6),
    completed_at timestamp(6),
    request_id BIGSERIAL NOT NULL REFERENCES request(id) ON DELETE CASCADE
);

CREATE TABLE contract_request(
    id BIGSERIAL PRIMARY KEY,
    status varchar(10) NOT NULL,
    request_id BIGSERIAL NOT NULL REFERENCES request(id) ON DELETE CASCADE,
    user_id uuid NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    created_at timestamp(6),
    modified_at timestamp(6)
);

CREATE TABLE extension_request(
    id BIGSERIAL PRIMARY KEY,
    created_at timestamp(6),
    modified_at timestamp(6),
    proposed_date timestamp(6) NOT NULL,
    status varchar(10) NOT NULL,
    user_id uuid NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    contract_id BIGSERIAL NOT NULL REFERENCES contract(id) ON DELETE CASCADE
)