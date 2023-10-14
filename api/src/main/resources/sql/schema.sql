DROP TABLE IF EXISTS comment CASCADE;
DROP TABLE IF EXISTS article CASCADE;
DROP TABLE IF EXISTS user_role CASCADE;
DROP TABLE IF EXISTS role CASCADE;
DROP TABLE IF EXISTS "user" CASCADE;

CREATE TABLE "user"
(
    id SERIAL NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    patronymic VARCHAR(255),
    email VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT username_unique UNIQUE (username)
);

CREATE TABLE role
(
    id SERIAL NOT NULL,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT name_unique UNIQUE (name)
);

CREATE TABLE user_role
(
    user_id integer NOT NULL REFERENCES "user"(id),
    role_id integer NOT NULL REFERENCES role(id),
    PRIMARY KEY (user_id, role_id)
);

CREATE TABLE article
(
    id SERIAL NOT NULL,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    date DATE NOT NULL,
    user_id INTEGER NOT NULL REFERENCES "user"(id),
    PRIMARY KEY (id)
);

CREATE TABLE comment
(
    id SERIAL NOT NULL,
    content VARCHAR(255) NOT NULL,
    date DATE NOT NULL,
    user_id INTEGER NOT NULL REFERENCES "user"(id),
    article_id INTEGER NOT NULL REFERENCES article(id),
    PRIMARY KEY (id)
);

INSERT INTO role (id, name) VALUES (1, 'ROLE_USER');
INSERT INTO role (id, name) VALUES (2, 'ROLE_ADMIN');