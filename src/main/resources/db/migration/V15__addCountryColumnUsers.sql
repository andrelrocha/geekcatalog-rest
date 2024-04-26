ALTER TABLE users
ADD COLUMN country_id UUID,
ADD CONSTRAINT fk_country_id FOREIGN KEY (country_id) REFERENCES countries(id);
