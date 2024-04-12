--CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DO $$
DECLARE
    game_info RECORD;
    game_exists BOOLEAN;
BEGIN
    -- Loop através dos registros da tabela legacyapp.finished
    FOR game_info IN SELECT name, metacritic FROM legacyapp.finished LOOP
        -- Verifica se o jogo já existe na tabela games
        SELECT EXISTS(SELECT 1 FROM socialapp.games WHERE name = game_info.name) INTO game_exists;

        -- Se o jogo não existir, insere na tabela games
        IF NOT game_exists THEN
            INSERT INTO socialapp.games (id, name, yr_of_release, metacritic) VALUES (uuid_generate_v4(), game_info.name, NULL, game_info.metacritic);
        END IF;
    END LOOP;
END $$;



