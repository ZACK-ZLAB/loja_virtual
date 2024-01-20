CREATE TABLE token (
    id BIGINT PRIMARY KEY,
    token VARCHAR(255) UNIQUE,
    tokenType VARCHAR(50),
    revoked BOOLEAN,
    expired BOOLEAN,
    usuario_id BIGINT,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

CREATE SEQUENCE IF NOT EXISTS public.seq_token
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.seq_token
    OWNER TO postgres;