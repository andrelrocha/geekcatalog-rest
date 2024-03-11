CREATE TABLE users (
    id VARCHAR(100) PRIMARY KEY,
    login VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(100),
    token_expiration TIMESTAMP,
    token_mail VARCHAR(255),
    profile_pic_id VARCHAR(100),
    FOREIGN KEY (profile_pic_id) REFERENCES profile_pic(id)
);