CREATE TABLE user_comment_like (
    id UUID PRIMARY KEY,
    liked BOOLEAN,
    comment_id UUID REFERENCES user_game_comment(id),
    user_id UUID REFERENCES users(id),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
)