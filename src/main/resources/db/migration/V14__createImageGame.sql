CREATE TABLE image_game (
    id UUID PRIMARY KEY,
    game_id UUID,
    FOREIGN KEY (game_id) REFERENCES games(id),
    image BYTEA
);