CREATE TABLE games (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    yr_of_release INT,
    metacritic INT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);