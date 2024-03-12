CREATE TABLE games_list (
    id UUID PRIMARY KEY,
    user_id UUID REFERENCES users(id),
    game_id UUID REFERENCES games(id),
    list_id UUID REFERENCES lists(id),
    console_played_id UUID REFERENCES console(id),
    rate INT,
    note TEXT
);