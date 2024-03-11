CREATE TABLE game_genre (
    id VARCHAR(100) PRIMARY KEY,
    game_id VARCHAR REFERENCES games(id),
    genre_id VARCHAR REFERENCES genres(id)
);