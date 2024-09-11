CREATE TABLE game_list (
    id UUID PRIMARY KEY,
    user_id UUID REFERENCES users(id),
    game_id UUID REFERENCES games(id),
    list_id UUID REFERENCES lists_app(id),
    console_played_id UUID REFERENCES consoles(id),
    note TEXT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);