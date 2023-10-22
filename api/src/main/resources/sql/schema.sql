DROP TABLE IF EXISTS comment CASCADE;
DROP TABLE IF EXISTS article CASCADE;
DROP TABLE IF EXISTS user_roles CASCADE;
DROP TABLE IF EXISTS "user" CASCADE;

CREATE TABLE "user"
(
    id SERIAL NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    last_name VARCHAR(255),
    first_name VARCHAR(255),
    patronymic VARCHAR(255),
    email VARCHAR(255),
    PRIMARY KEY (id),
    CONSTRAINT username_unique UNIQUE (username)
);

CREATE TABLE user_roles
(
    user_id SERIAL NOT NULL REFERENCES "user"(id),
    roles VARCHAR(255)
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
