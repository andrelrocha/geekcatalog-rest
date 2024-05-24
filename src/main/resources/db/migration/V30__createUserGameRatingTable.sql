CREATE TABLE users_game_rating (
    id UUID,
    rating INT,
    user_id UUID REFERENCES users(id),
    game_id UUID REFERENCES games(id),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
)