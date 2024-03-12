CREATE TABLE game_genre (
    id UUID PRIMARY KEY,
    game_id UUID REFERENCES games(id),
    genre_id UUID REFERENCES genres(id)
);