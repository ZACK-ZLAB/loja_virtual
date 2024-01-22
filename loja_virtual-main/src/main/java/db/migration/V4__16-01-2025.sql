CREATE TABLE IF NOT EXISTS public.token
(
    id bigint NOT NULL,
    token character varying(255) COLLATE pg_catalog."default",
    tokentype character varying(50) COLLATE pg_catalog."default",
    revoked boolean,
    expired boolean,
    usuario_id bigint,
    token_type character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT token_pkey PRIMARY KEY (id),
    CONSTRAINT token_token_key UNIQUE (token),
    CONSTRAINT token_usuario_id_fkey FOREIGN KEY (usuario_id)
        REFERENCES public.usuario (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT token_token_type_check CHECK (token_type::text = 'BEARER'::text)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.token
    OWNER to postgres;

CREATE SEQUENCE IF NOT EXISTS public.seq_token
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.seq_token
    OWNER TO postgres;