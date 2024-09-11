CREATE TABLE game_console (
    id UUID PRIMARY KEY,
    game_id UUID REFERENCES games(id),
    console_id UUID REFERENCES consoles(id)
);