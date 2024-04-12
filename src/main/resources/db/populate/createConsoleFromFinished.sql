DO $$
DECLARE
    game_info RECORD;
    console_exists BOOLEAN;
BEGIN
    -- Loop através dos registros da tabela legacyapp.finished
    FOR game_info IN SELECT console FROM legacyapp.finished LOOP
        -- Verifica se o jogo já existe na tabela games
        SELECT EXISTS(SELECT 1 FROM socialapp.console WHERE name = game_info.console) INTO console_exists;

        -- Se o console não existir, insere na tabela console
        IF NOT console_exists THEN
            INSERT INTO socialapp.console (id, name) VALUES (uuid_generate_v4(), game_info.console);
        END IF;
    END LOOP;
END $$;