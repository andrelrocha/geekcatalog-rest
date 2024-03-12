CREATE TABLE game_studio (
    id UUID PRIMARY KEY,
    game_id UUID REFERENCES games(id),
    studio_id UUID REFERENCES studios(id)
);