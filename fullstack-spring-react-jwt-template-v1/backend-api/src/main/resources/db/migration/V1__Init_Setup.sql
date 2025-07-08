CREATE TABLE password_reset_tokens
(
    id           INT AUTO_INCREMENT NOT NULL,
    token        VARCHAR(128)       NOT NULL,
    token_expiry datetime           NOT NULL,
    CONSTRAINT pk_password_reset_tokens PRIMARY KEY (id)
);

CREATE TABLE users
(
    id                 BIGINT AUTO_INCREMENT NOT NULL,
    created_at         datetime              NOT NULL,
    created_by         VARCHAR(20)           NOT NULL,
    updated_at         datetime              NULL,
    updated_by         VARCHAR(20)           NULL,
    username           VARCHAR(100)          NOT NULL,
    email              VARCHAR(100)          NOT NULL,
    profile_picture    VARCHAR(100)          NULL,
    password           VARCHAR(100)          NOT NULL,
    roles              VARCHAR(30)           NOT NULL,
    pwd_reset_token_id INT                   NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE password_reset_tokens
    ADD CONSTRAINT uc_password_reset_tokens_token UNIQUE (token);

ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);

ALTER TABLE users
    ADD CONSTRAINT uc_users_pwd_reset_token UNIQUE (pwd_reset_token_id);

ALTER TABLE users
    ADD CONSTRAINT FK_USERS_ON_PWD_RESET_TOKEN FOREIGN KEY (pwd_reset_token_id) REFERENCES password_reset_tokens (id);