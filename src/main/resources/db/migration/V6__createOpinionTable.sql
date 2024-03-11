CREATE TABLE opinions (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    console VARCHAR(100),
    note INTEGER,
    opinion TEXT,
    game_id INTEGER REFERENCES games(id)
);