CREATE TABLE game_studio (
    id VARCHAR(100) PRIMARY KEY,
    game_id VARCHAR REFERENCES games(id),
    studio_id VARCHAR REFERENCES studios(id)
);