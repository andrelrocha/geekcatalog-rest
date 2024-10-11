CREATE TABLE user_authentication_type (
    id UUID PRIMARY KEY,
    user_id UUID REFERENCES users(id),
    oauth_id VARCHAR(100),
    authentication_type UUID REFERENCES authentication_type(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE OR REPLACE FUNCTION update_timestamp()
RETURNS TRIGGER AS $$
BEGIN
   NEW.updated_at = NOW();
   RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER user_authentication_type_timestamp
BEFORE UPDATE ON user_authentication_type
FOR EACH ROW
EXECUTE FUNCTION update_timestamp();