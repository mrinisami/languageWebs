CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

--users

CREATE TABLE users (
    id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    first_name varchar(50),
    last_name varchar(50),
    email varchar(100) NOT NULL,
    user_password varchar(252) NOT NULL,
    date_joined timestamp(6),
    user_role varchar(10) DEFAULT ('USER'),
	UNIQUE (email)
);

--texts

CREATE TABLE texts(
	id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
	text_content varchar(5120),
	text_link varchar(400),
	text_language varchar(15) NOT NULL,
	date_added timestamp(6),
	user_id uuid NOT NULL REFERENCES users(id),
	UNIQUE(user_id)

);

