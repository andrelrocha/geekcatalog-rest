INSERT INTO socialapp.themes (id, name) VALUES (uuid_generate_v4(), 'default');
INSERT INTO socialapp.themes (id, name) VALUES (uuid_generate_v4(), 'dark');


INSERT INTO socialapp.authentication_type (id, name)
VALUES
    (uuid_generate_v4(), 'Own Login'),
    (uuid_generate_v4(), 'Google'),
    (uuid_generate_v4(), 'Facebook'),
    (uuid_generate_v4(), 'GitHub'),
    (uuid_generate_v4(), 'Apple'),
    (uuid_generate_v4(), 'LinkedIn'),
    (uuid_generate_v4(), 'SAML'),
    (uuid_generate_v4(), 'OAuth');