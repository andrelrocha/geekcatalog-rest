-- Primeiro, adicione a coluna
ALTER TABLE users
ADD COLUMN country_id UUID;

-- Em seguida, adicione a restrição de chave estrangeira
ALTER TABLE users
ADD CONSTRAINT fk_country_id FOREIGN KEY (country_id) REFERENCES countries(id);
