CREATE TABLE games_list (
    id VARCHAR(100) PRIMARY KEY,
    user_id VARCHAR REFERENCES users(id),
    game_id VARCHAR REFERENCES games(id),
    list_id VARCHAR REFERENCES lists(id),
    console_played_id VARCHAR REFERENCES console(id),
    rate INT,
    note TEXT
);