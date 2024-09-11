CREATE TABLE profile_pic (
    id UUID PRIMARY KEY,
    image BYTEA,
    user_id UUID REFERENCES users(id)
);