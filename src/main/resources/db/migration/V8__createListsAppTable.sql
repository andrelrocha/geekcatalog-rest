CREATE TABLE lists_app (
    id UUID PRIMARY KEY,
    name VARCHAR(200),
    description VARCHAR(300),
    user_id UUID REFERENCES users(id)
);