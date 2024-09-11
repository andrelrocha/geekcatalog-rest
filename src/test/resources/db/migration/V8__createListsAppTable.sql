CREATE TABLE lists_app (
    id UUID PRIMARY KEY,
    name VARCHAR(100),
    description VARCHAR(300),
    user_id UUID REFERENCES users(id),
    visibility BOOLEAN,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);