CREATE TABLE lists (
    id VARCHAR(100) PRIMARY KEY,
    name VARCHAR(200),
    user_id VARCHAR REFERENCES users(id)
);