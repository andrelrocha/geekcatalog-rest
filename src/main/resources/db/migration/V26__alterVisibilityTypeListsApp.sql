ALTER TABLE lists_app
DROP COLUMN visibility;

ALTER TABLE lists_app
ADD COLUMN visibility BOOLEAN;