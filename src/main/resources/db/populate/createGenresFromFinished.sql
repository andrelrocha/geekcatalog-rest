DO $$
DECLARE
    game_info RECORD;
    genre_exists BOOLEAN;
    genre_left VARCHAR(100);
    genre_right VARCHAR(100);
BEGIN
    FOR game_info IN SELECT genre FROM legacyapp.finished LOOP
        IF POSITION('/' IN game_info.genre) > 0 THEN
            genre_left := SUBSTRING(game_info.genre FROM 1 FOR POSITION('/' IN game_info.genre) - 1);
            genre_right := SUBSTRING(game_info.genre FROM POSITION('/' IN game_info.genre) + 1);

            SELECT EXISTS(SELECT 1 FROM socialapp.genres WHERE name = genre_left) INTO genre_exists;
            IF NOT genre_exists THEN
                INSERT INTO socialapp.genres (id, name) VALUES (uuid_generate_v4(), genre_left);
            END IF;

            SELECT EXISTS(SELECT 1 FROM socialapp.genres WHERE name = genre_right) INTO genre_exists;
            IF NOT genre_exists THEN
                INSERT INTO socialapp.genres (id, name) VALUES (uuid_generate_v4(), genre_right);
            END IF;
        ELSE
            SELECT EXISTS(SELECT 1 FROM socialapp.genres WHERE name = game_info.genre) INTO genre_exists;
            IF NOT genre_exists THEN
                INSERT INTO socialapp.genres (id, name) VALUES (uuid_generate_v4(), game_info.genre);
            END IF;
        END IF;
    END LOOP;
END $$;
