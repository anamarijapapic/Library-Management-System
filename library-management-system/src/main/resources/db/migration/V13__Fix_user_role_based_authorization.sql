DROP TABLE "user" CASCADE;

CREATE TABLE IF NOT EXISTS "user" (
    id SERIAL NOT NULL PRIMARY KEY,
    first_name VARCHAR NOT NULL,
    last_name VARCHAR NOT NULL,
    "password" VARCHAR NOT NULL,
    email VARCHAR UNIQUE NOT NULL,
    date_of_birth TIMESTAMP NOT NULL,
    contact_number VARCHAR NOT NULL,
    "enabled" BOOLEAN NULL
);

DROP TYPE role CASCADE;

CREATE TABLE IF NOT EXISTS role (
    id SERIAL NOT NULL PRIMARY KEY,
    "name" VARCHAR NOT NULL
);

CREATE TABLE user_role (
    user_id SERIAL NOT NULL,
    role_id SERIAL NOT NULL,
    CONSTRAINT fk_user
       FOREIGN KEY(user_id)
           REFERENCES "user"(id),
    CONSTRAINT fk_role
       FOREIGN KEY(role_id)
           REFERENCES role(id)
);

CREATE INDEX fk_user_idx ON user_role (user_id);
CREATE INDEX fk_role_idx ON user_role (role_id);

INSERT INTO role ("name") VALUES ('MEMBER');
INSERT INTO role ("name") VALUES ('LIBRARIAN');
INSERT INTO role ("name") VALUES ('ADMIN');


INSERT INTO "user" (
    first_name,
    last_name,
    "password",
    email,
    date_of_birth,
    contact_number,
    "enabled"
) VALUES (
    'admin',
    'admin',
    '$2a$12$3tZr1OLzCU1mojVnwAtBlOOy9WkJNM2Yifbr3iAxVQBFXlqIAlw22',
    'admin@admin.oss',
    '1980-01-10 00:00:00',
    '+1111111111',
    TRUE
);

INSERT INTO "user" (
    first_name,
    last_name,
    "password",
    email,
    date_of_birth,
    contact_number,
    "enabled"
) VALUES (
    'librarian',
    'librarian',
    '$2a$12$3tZr1OLzCU1mojVnwAtBlOOy9WkJNM2Yifbr3iAxVQBFXlqIAlw22',
    'librarian@librarian.oss',
    '1990-02-20 00:00:00',
    '+2222222222',
    TRUE
);

INSERT INTO "user" (
    first_name,
    last_name,
    "password",
    email,
    date_of_birth,
    contact_number,
    "enabled"
) VALUES (
    'member',
    'member',
    '$2a$12$3tZr1OLzCU1mojVnwAtBlOOy9WkJNM2Yifbr3iAxVQBFXlqIAlw22',
    'member@member.oss',
    '2000-03-30 00:00:00',
    '+3333333333',
    TRUE
);

INSERT INTO user_role (user_id, role_id) VALUES (1, 1); -- user admin has role ADMIN
INSERT INTO user_role (user_id, role_id) VALUES (2, 2); -- user librarian has role LIBRARIAN
INSERT INTO user_role (user_id, role_id) VALUES (3, 3); -- user member has role MEMBER