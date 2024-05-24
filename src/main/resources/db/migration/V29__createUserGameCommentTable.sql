CREATE TABLE user_game_comment (
    id UUID,
    comment VARCHAR(300),
    user_id UUID REFERENCES users(id),
    game_id UUID REFERENCES games(id),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
)