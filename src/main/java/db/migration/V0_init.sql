--
-- PostgreSQL database dump
--

-- Dumped from database version 16.0
-- Dumped by pg_dump version 16.0

-- Started on 2023-11-09 17:14:25

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 5005 (class 1262 OID 57511)
-- Name: loja_virtual; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE loja_virtual WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Portuguese_Brazil.1252';


ALTER DATABASE loja_virtual OWNER TO postgres;

\connect loja_virtual

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 255 (class 1255 OID 65561)
-- Name: validachavepessoa(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.validachavepessoa() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE existe integer;
BEGIN
  SELECT COUNT(1) INTO existe FROM pessoa_fisica WHERE id = NEW.pessoa_id;
  
  IF existe <= 0 THEN
    SELECT COUNT(1) INTO existe FROM pessoa_juridica WHERE id = NEW.pessoa_id;
    
    IF existe <= 0 THEN
      RAISE EXCEPTION 'Não foi encontrado o ID e PK da pessoa para realizar a associação do cadastro';
    END IF;
  END IF;
  
  RETURN NEW;
END;
$$;


ALTER FUNCTION public.validachavepessoa() OWNER TO postgres;

--
-- TOC entry 256 (class 1255 OID 65566)
-- Name: validachavepessoa2(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.validachavepessoa2() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE existe integer;
BEGIN
  SELECT COUNT(1) INTO existe FROM pessoa_fisica WHERE id = NEW.pessoa_forn_id;
  
  IF existe <= 0 THEN
    SELECT COUNT(1) INTO existe FROM pessoa_juridica WHERE id = NEW.pessoa_forn_id;
    
    IF existe <= 0 THEN
      RAISE EXCEPTION 'Não foi encontrado o ID e PK da pessoa para realizar a associação do cadastro';
    END IF;
  END IF;
  
  RETURN NEW;
END;
$$;


ALTER FUNCTION public.validachavepessoa2() OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 234 (class 1259 OID 57740)
-- Name: acesso; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.acesso (
    id bigint NOT NULL,
    descricao character varying(255) NOT NULL
);


ALTER TABLE public.acesso OWNER TO postgres;

--
-- TOC entry 235 (class 1259 OID 57745)
-- Name: avaliacao_produto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.avaliacao_produto (
    nota integer NOT NULL,
    id bigint NOT NULL,
    pessoa_id bigint NOT NULL,
    produto_id bigint NOT NULL,
    descricao character varying(255) NOT NULL
);


ALTER TABLE public.avaliacao_produto OWNER TO postgres;

--
-- TOC entry 236 (class 1259 OID 57750)
-- Name: categoria_produto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.categoria_produto (
    id bigint NOT NULL,
    nome_desc character varying(255) NOT NULL
);


ALTER TABLE public.categoria_produto OWNER TO postgres;

--
-- TOC entry 237 (class 1259 OID 57755)
-- Name: conta_pagar; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.conta_pagar (
    dt_pagamento date,
    dt_vencimento date NOT NULL,
    valor_desconto numeric(38,2),
    valor_total numeric(38,2) NOT NULL,
    id bigint NOT NULL,
    pessoa_forn_id bigint NOT NULL,
    pessoa_id bigint NOT NULL,
    descricao character varying(255) NOT NULL,
    status character varying(255) NOT NULL,
    CONSTRAINT conta_pagar_status_check CHECK (((status)::text = ANY ((ARRAY['COBRANCA'::character varying, 'VENCIDA'::character varying, 'ABERTA'::character varying, 'QUITADA'::character varying, 'ALUGUEL'::character varying, 'FUNCIONARIO'::character varying, 'NEGOCIADA'::character varying])::text[])))
);


ALTER TABLE public.conta_pagar OWNER TO postgres;

--
-- TOC entry 238 (class 1259 OID 57763)
-- Name: conta_receber; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.conta_receber (
    dt_pagamento date,
    dt_vencimento date NOT NULL,
    valor_desconto numeric(38,2),
    valor_total numeric(38,2) NOT NULL,
    id bigint NOT NULL,
    pessoa_id bigint NOT NULL,
    descricao character varying(255) NOT NULL,
    status character varying(255) NOT NULL,
    CONSTRAINT conta_receber_status_check CHECK (((status)::text = ANY ((ARRAY['COBRANCA'::character varying, 'VENCIDA'::character varying, 'ABERTA'::character varying, 'QUITADA'::character varying])::text[])))
);


ALTER TABLE public.conta_receber OWNER TO postgres;

--
-- TOC entry 239 (class 1259 OID 57771)
-- Name: cup_desc; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cup_desc (
    data_validade_cupom date NOT NULL,
    valor_porcent_desc numeric(38,2),
    valor_real_desc numeric(38,2),
    id bigint NOT NULL,
    cod_desc character varying(255) NOT NULL
);


ALTER TABLE public.cup_desc OWNER TO postgres;

--
-- TOC entry 240 (class 1259 OID 57776)
-- Name: endereco; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.endereco (
    id bigint NOT NULL,
    pessoa_id bigint NOT NULL,
    bairro character varying(255) NOT NULL,
    cep character varying(255) NOT NULL,
    cidade character varying(255) NOT NULL,
    complemento character varying(255),
    numero character varying(255) NOT NULL,
    rua_logra character varying(255) NOT NULL,
    tipo_endereco character varying(255) NOT NULL,
    uf character varying(255) NOT NULL,
    CONSTRAINT endereco_tipo_endereco_check CHECK (((tipo_endereco)::text = ANY ((ARRAY['COBRANCA'::character varying, 'ENTREGA'::character varying])::text[])))
);


ALTER TABLE public.endereco OWNER TO postgres;

--
-- TOC entry 241 (class 1259 OID 57784)
-- Name: forma_pagamento; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.forma_pagamento (
    id bigint NOT NULL,
    descricao character varying(255) NOT NULL
);


ALTER TABLE public.forma_pagamento OWNER TO postgres;

--
-- TOC entry 242 (class 1259 OID 57789)
-- Name: imagem_produto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.imagem_produto (
    id bigint NOT NULL,
    produto_id bigint NOT NULL,
    imagem_miniatura text NOT NULL,
    imagem_original text NOT NULL
);


ALTER TABLE public.imagem_produto OWNER TO postgres;

--
-- TOC entry 243 (class 1259 OID 57796)
-- Name: item_venda_loja; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.item_venda_loja (
    quantidade double precision NOT NULL,
    id bigint NOT NULL,
    produto_id bigint NOT NULL,
    venda_compra_loja_virtu_id bigint NOT NULL
);


ALTER TABLE public.item_venda_loja OWNER TO postgres;

--
-- TOC entry 244 (class 1259 OID 57801)
-- Name: marca_produto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.marca_produto (
    id bigint NOT NULL,
    nome_desc character varying(255) NOT NULL
);


ALTER TABLE public.marca_produto OWNER TO postgres;

--
-- TOC entry 245 (class 1259 OID 57806)
-- Name: nota_fiscal_compra; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.nota_fiscal_compra (
    data_compra date NOT NULL,
    valor_desconto numeric(38,2),
    valor_icms numeric(38,2) NOT NULL,
    valor_total numeric(38,2) NOT NULL,
    conta_pagar_id bigint NOT NULL,
    id bigint NOT NULL,
    pessoa_id bigint NOT NULL,
    descricao_obs character varying(255),
    numero_nota character varying(255) NOT NULL,
    serie_nota character varying(255) NOT NULL
);


ALTER TABLE public.nota_fiscal_compra OWNER TO postgres;

--
-- TOC entry 246 (class 1259 OID 57813)
-- Name: nota_fiscal_venda; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.nota_fiscal_venda (
    id bigint NOT NULL,
    venda_compra_loja_virt_id bigint NOT NULL,
    numero character varying(255) NOT NULL,
    pdf text NOT NULL,
    serie character varying(255) NOT NULL,
    tipo character varying(255) NOT NULL,
    xml text NOT NULL
);


ALTER TABLE public.nota_fiscal_venda OWNER TO postgres;

--
-- TOC entry 247 (class 1259 OID 57822)
-- Name: nota_item_produto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.nota_item_produto (
    quantidade double precision NOT NULL,
    id bigint NOT NULL,
    nota_fiscal_compra_id bigint NOT NULL,
    produto_id bigint NOT NULL
);


ALTER TABLE public.nota_item_produto OWNER TO postgres;

--
-- TOC entry 248 (class 1259 OID 57827)
-- Name: pessoa_fisica; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pessoa_fisica (
    data_nascimento date,
    id bigint NOT NULL,
    cpf character varying(255) NOT NULL,
    email character varying(255) NOT NULL,
    nome character varying(255) NOT NULL,
    telefone character varying(255) NOT NULL
);


ALTER TABLE public.pessoa_fisica OWNER TO postgres;

--
-- TOC entry 249 (class 1259 OID 57834)
-- Name: pessoa_juridica; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pessoa_juridica (
    id bigint NOT NULL,
    categoria character varying(255),
    cnpj character varying(255) NOT NULL,
    email character varying(255) NOT NULL,
    insc_estadual character varying(255) NOT NULL,
    insc_municipal character varying(255),
    nome character varying(255) NOT NULL,
    nome_fantasia character varying(255) NOT NULL,
    razao_social character varying(255) NOT NULL,
    telefone character varying(255) NOT NULL
);


ALTER TABLE public.pessoa_juridica OWNER TO postgres;

--
-- TOC entry 250 (class 1259 OID 57841)
-- Name: produto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.produto (
    alerta_qtde_estoque boolean,
    altura double precision NOT NULL,
    ativo boolean NOT NULL,
    largura double precision NOT NULL,
    peso double precision NOT NULL,
    profundidade double precision NOT NULL,
    qtd_estoque integer NOT NULL,
    qtde_alerta_estoque integer,
    qtde_clique integer,
    valor_venda numeric(38,2) NOT NULL,
    id bigint NOT NULL,
    descricao text NOT NULL,
    link_youtube character varying(255),
    nome character varying(255) NOT NULL,
    tipo_unidade character varying(255) NOT NULL
);


ALTER TABLE public.produto OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 57721)
-- Name: seq_acesso; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_acesso
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_acesso OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 57722)
-- Name: seq_avaliacao_produto; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_avaliacao_produto
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_avaliacao_produto OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 57723)
-- Name: seq_categoria_produto; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_categoria_produto
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_categoria_produto OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 57724)
-- Name: seq_conta_pagar; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_conta_pagar
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_conta_pagar OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 57725)
-- Name: seq_conta_receber; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_conta_receber
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_conta_receber OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 57726)
-- Name: seq_cup_desc; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_cup_desc
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_cup_desc OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 57727)
-- Name: seq_endereco; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_endereco
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_endereco OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 57728)
-- Name: seq_forma_pagamento; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_forma_pagamento
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_forma_pagamento OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 57729)
-- Name: seq_imagem_produto; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_imagem_produto
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_imagem_produto OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 57730)
-- Name: seq_item_venda_loja; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_item_venda_loja
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_item_venda_loja OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 57731)
-- Name: seq_marca_produto; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_marca_produto
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_marca_produto OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 57732)
-- Name: seq_nota_fiscal_compra; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_nota_fiscal_compra
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_nota_fiscal_compra OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 57733)
-- Name: seq_nota_fiscal_venda; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_nota_fiscal_venda
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_nota_fiscal_venda OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 57734)
-- Name: seq_nota_item_produto; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_nota_item_produto
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_nota_item_produto OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 57735)
-- Name: seq_pessoa; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_pessoa
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_pessoa OWNER TO postgres;

--
-- TOC entry 230 (class 1259 OID 57736)
-- Name: seq_produto; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_produto
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_produto OWNER TO postgres;

--
-- TOC entry 231 (class 1259 OID 57737)
-- Name: seq_status_rastreio; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_status_rastreio
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_status_rastreio OWNER TO postgres;

--
-- TOC entry 232 (class 1259 OID 57738)
-- Name: seq_usuario; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_usuario
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_usuario OWNER TO postgres;

--
-- TOC entry 233 (class 1259 OID 57739)
-- Name: seq_vd_cp_loja_virt; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_vd_cp_loja_virt
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_vd_cp_loja_virt OWNER TO postgres;

--
-- TOC entry 251 (class 1259 OID 57848)
-- Name: status_rastreio; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.status_rastreio (
    id bigint NOT NULL,
    venda_compra_loja_virt_id bigint NOT NULL,
    centro_distribuicao character varying(255),
    cidade character varying(255),
    estado character varying(255),
    status character varying(255)
);


ALTER TABLE public.status_rastreio OWNER TO postgres;

--
-- TOC entry 252 (class 1259 OID 57855)
-- Name: usuario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.usuario (
    id bigint NOT NULL,
    pessoa_id bigint NOT NULL,
    data_atual_senha date,
    login character varying(255) NOT NULL,
    senha character varying(255) NOT NULL
);


ALTER TABLE public.usuario OWNER TO postgres;

--
-- TOC entry 253 (class 1259 OID 57862)
-- Name: usuarios_acesso; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.usuarios_acesso (
    acesso_id bigint NOT NULL,
    usuario_id bigint NOT NULL
);


ALTER TABLE public.usuarios_acesso OWNER TO postgres;

--
-- TOC entry 254 (class 1259 OID 57957)
-- Name: vd_cp_loja_virt; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.vd_cp_loja_virt (
    id bigint NOT NULL,
    data_entrega date NOT NULL,
    data_venda date NOT NULL,
    dia_entrega integer NOT NULL,
    valor_desconto numeric(38,2),
    valor_fret numeric(38,2) NOT NULL,
    valor_total numeric(38,2) NOT NULL,
    cumpo_desc_id bigint,
    endereco_cobranca_id bigint NOT NULL,
    endereco_entrega_id bigint NOT NULL,
    forma_pagamente_id bigint NOT NULL,
    nota_fiscal_venda_id bigint NOT NULL,
    pessoa_id bigint NOT NULL
);


ALTER TABLE public.vd_cp_loja_virt OWNER TO postgres;

--
-- TOC entry 4979 (class 0 OID 57740)
-- Dependencies: 234
-- Data for Name: acesso; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4980 (class 0 OID 57745)
-- Dependencies: 235
-- Data for Name: avaliacao_produto; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.avaliacao_produto (nota, id, pessoa_id, produto_id, descricao) VALUES (10, 2, 1, 1, 'teste avaliacao produto trigger');


--
-- TOC entry 4981 (class 0 OID 57750)
-- Dependencies: 236
-- Data for Name: categoria_produto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4982 (class 0 OID 57755)
-- Dependencies: 237
-- Data for Name: conta_pagar; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4983 (class 0 OID 57763)
-- Dependencies: 238
-- Data for Name: conta_receber; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4984 (class 0 OID 57771)
-- Dependencies: 239
-- Data for Name: cup_desc; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4985 (class 0 OID 57776)
-- Dependencies: 240
-- Data for Name: endereco; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4986 (class 0 OID 57784)
-- Dependencies: 241
-- Data for Name: forma_pagamento; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4987 (class 0 OID 57789)
-- Dependencies: 242
-- Data for Name: imagem_produto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4988 (class 0 OID 57796)
-- Dependencies: 243
-- Data for Name: item_venda_loja; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4989 (class 0 OID 57801)
-- Dependencies: 244
-- Data for Name: marca_produto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4990 (class 0 OID 57806)
-- Dependencies: 245
-- Data for Name: nota_fiscal_compra; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4991 (class 0 OID 57813)
-- Dependencies: 246
-- Data for Name: nota_fiscal_venda; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4992 (class 0 OID 57822)
-- Dependencies: 247
-- Data for Name: nota_item_produto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4993 (class 0 OID 57827)
-- Dependencies: 248
-- Data for Name: pessoa_fisica; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.pessoa_fisica (data_nascimento, id, cpf, email, nome, telefone) VALUES ('1992-12-19', 1, '03620659346', 'zacariasjunior@gmail', 'zack', '562452155');


--
-- TOC entry 4994 (class 0 OID 57834)
-- Dependencies: 249
-- Data for Name: pessoa_juridica; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4995 (class 0 OID 57841)
-- Dependencies: 250
-- Data for Name: produto; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.produto (alerta_qtde_estoque, altura, ativo, largura, peso, profundidade, qtd_estoque, qtde_alerta_estoque, qtde_clique, valor_venda, id, descricao, link_youtube, nome, tipo_unidade) VALUES (true, 10, true, 50.2, 50, 80.8, 1, 1, 50, 50.00, 1, 'produto teste', 'sssssffs', 'nome produto teste', 'UN');


--
-- TOC entry 4996 (class 0 OID 57848)
-- Dependencies: 251
-- Data for Name: status_rastreio; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4997 (class 0 OID 57855)
-- Dependencies: 252
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4998 (class 0 OID 57862)
-- Dependencies: 253
-- Data for Name: usuarios_acesso; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4999 (class 0 OID 57957)
-- Dependencies: 254
-- Data for Name: vd_cp_loja_virt; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5006 (class 0 OID 0)
-- Dependencies: 215
-- Name: seq_acesso; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_acesso', 1, false);


--
-- TOC entry 5007 (class 0 OID 0)
-- Dependencies: 216
-- Name: seq_avaliacao_produto; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_avaliacao_produto', 1, false);


--
-- TOC entry 5008 (class 0 OID 0)
-- Dependencies: 217
-- Name: seq_categoria_produto; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_categoria_produto', 1, false);


--
-- TOC entry 5009 (class 0 OID 0)
-- Dependencies: 218
-- Name: seq_conta_pagar; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_conta_pagar', 1, false);


--
-- TOC entry 5010 (class 0 OID 0)
-- Dependencies: 219
-- Name: seq_conta_receber; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_conta_receber', 1, false);


--
-- TOC entry 5011 (class 0 OID 0)
-- Dependencies: 220
-- Name: seq_cup_desc; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_cup_desc', 1, false);


--
-- TOC entry 5012 (class 0 OID 0)
-- Dependencies: 221
-- Name: seq_endereco; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_endereco', 1, false);


--
-- TOC entry 5013 (class 0 OID 0)
-- Dependencies: 222
-- Name: seq_forma_pagamento; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_forma_pagamento', 1, false);


--
-- TOC entry 5014 (class 0 OID 0)
-- Dependencies: 223
-- Name: seq_imagem_produto; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_imagem_produto', 1, false);


--
-- TOC entry 5015 (class 0 OID 0)
-- Dependencies: 224
-- Name: seq_item_venda_loja; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_item_venda_loja', 1, false);


--
-- TOC entry 5016 (class 0 OID 0)
-- Dependencies: 225
-- Name: seq_marca_produto; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_marca_produto', 1, false);


--
-- TOC entry 5017 (class 0 OID 0)
-- Dependencies: 226
-- Name: seq_nota_fiscal_compra; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_nota_fiscal_compra', 1, false);


--
-- TOC entry 5018 (class 0 OID 0)
-- Dependencies: 227
-- Name: seq_nota_fiscal_venda; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_nota_fiscal_venda', 1, false);


--
-- TOC entry 5019 (class 0 OID 0)
-- Dependencies: 228
-- Name: seq_nota_item_produto; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_nota_item_produto', 1, false);


--
-- TOC entry 5020 (class 0 OID 0)
-- Dependencies: 229
-- Name: seq_pessoa; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_pessoa', 1, false);


--
-- TOC entry 5021 (class 0 OID 0)
-- Dependencies: 230
-- Name: seq_produto; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_produto', 1, false);


--
-- TOC entry 5022 (class 0 OID 0)
-- Dependencies: 231
-- Name: seq_status_rastreio; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_status_rastreio', 1, false);


--
-- TOC entry 5023 (class 0 OID 0)
-- Dependencies: 232
-- Name: seq_usuario; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_usuario', 1, false);


--
-- TOC entry 5024 (class 0 OID 0)
-- Dependencies: 233
-- Name: seq_vd_cp_loja_virt; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_vd_cp_loja_virt', 1, false);


--
-- TOC entry 4738 (class 2606 OID 57744)
-- Name: acesso acesso_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.acesso
    ADD CONSTRAINT acesso_pkey PRIMARY KEY (id);


--
-- TOC entry 4740 (class 2606 OID 57749)
-- Name: avaliacao_produto avaliacao_produto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.avaliacao_produto
    ADD CONSTRAINT avaliacao_produto_pkey PRIMARY KEY (id);


--
-- TOC entry 4742 (class 2606 OID 57754)
-- Name: categoria_produto categoria_produto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoria_produto
    ADD CONSTRAINT categoria_produto_pkey PRIMARY KEY (id);


--
-- TOC entry 4744 (class 2606 OID 57762)
-- Name: conta_pagar conta_pagar_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.conta_pagar
    ADD CONSTRAINT conta_pagar_pkey PRIMARY KEY (id);


--
-- TOC entry 4746 (class 2606 OID 57770)
-- Name: conta_receber conta_receber_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.conta_receber
    ADD CONSTRAINT conta_receber_pkey PRIMARY KEY (id);


--
-- TOC entry 4748 (class 2606 OID 57775)
-- Name: cup_desc cup_desc_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cup_desc
    ADD CONSTRAINT cup_desc_pkey PRIMARY KEY (id);


--
-- TOC entry 4750 (class 2606 OID 57783)
-- Name: endereco endereco_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.endereco
    ADD CONSTRAINT endereco_pkey PRIMARY KEY (id);


--
-- TOC entry 4752 (class 2606 OID 57788)
-- Name: forma_pagamento forma_pagamento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.forma_pagamento
    ADD CONSTRAINT forma_pagamento_pkey PRIMARY KEY (id);


--
-- TOC entry 4754 (class 2606 OID 57795)
-- Name: imagem_produto imagem_produto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.imagem_produto
    ADD CONSTRAINT imagem_produto_pkey PRIMARY KEY (id);


--
-- TOC entry 4756 (class 2606 OID 57800)
-- Name: item_venda_loja item_venda_loja_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.item_venda_loja
    ADD CONSTRAINT item_venda_loja_pkey PRIMARY KEY (id);


--
-- TOC entry 4758 (class 2606 OID 57805)
-- Name: marca_produto marca_produto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.marca_produto
    ADD CONSTRAINT marca_produto_pkey PRIMARY KEY (id);


--
-- TOC entry 4760 (class 2606 OID 57812)
-- Name: nota_fiscal_compra nota_fiscal_compra_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nota_fiscal_compra
    ADD CONSTRAINT nota_fiscal_compra_pkey PRIMARY KEY (id);


--
-- TOC entry 4762 (class 2606 OID 57819)
-- Name: nota_fiscal_venda nota_fiscal_venda_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nota_fiscal_venda
    ADD CONSTRAINT nota_fiscal_venda_pkey PRIMARY KEY (id);


--
-- TOC entry 4764 (class 2606 OID 57821)
-- Name: nota_fiscal_venda nota_fiscal_venda_venda_compra_loja_virt_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nota_fiscal_venda
    ADD CONSTRAINT nota_fiscal_venda_venda_compra_loja_virt_id_key UNIQUE (venda_compra_loja_virt_id);


--
-- TOC entry 4766 (class 2606 OID 57826)
-- Name: nota_item_produto nota_item_produto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nota_item_produto
    ADD CONSTRAINT nota_item_produto_pkey PRIMARY KEY (id);


--
-- TOC entry 4768 (class 2606 OID 57833)
-- Name: pessoa_fisica pessoa_fisica_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pessoa_fisica
    ADD CONSTRAINT pessoa_fisica_pkey PRIMARY KEY (id);


--
-- TOC entry 4770 (class 2606 OID 57840)
-- Name: pessoa_juridica pessoa_juridica_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pessoa_juridica
    ADD CONSTRAINT pessoa_juridica_pkey PRIMARY KEY (id);


--
-- TOC entry 4772 (class 2606 OID 57847)
-- Name: produto produto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.produto
    ADD CONSTRAINT produto_pkey PRIMARY KEY (id);


--
-- TOC entry 4774 (class 2606 OID 57854)
-- Name: status_rastreio status_rastreio_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.status_rastreio
    ADD CONSTRAINT status_rastreio_pkey PRIMARY KEY (id);


--
-- TOC entry 4782 (class 2606 OID 57963)
-- Name: vd_cp_loja_virt uk_hkxjejv08kldx994j4serhrbu; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vd_cp_loja_virt
    ADD CONSTRAINT uk_hkxjejv08kldx994j4serhrbu UNIQUE (nota_fiscal_venda_id);


--
-- TOC entry 4778 (class 2606 OID 57868)
-- Name: usuarios_acesso unique_acesso_user; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuarios_acesso
    ADD CONSTRAINT unique_acesso_user UNIQUE (usuario_id, acesso_id);


--
-- TOC entry 4776 (class 2606 OID 57861)
-- Name: usuario usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);


--
-- TOC entry 4780 (class 2606 OID 57866)
-- Name: usuarios_acesso usuarios_acesso_acesso_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuarios_acesso
    ADD CONSTRAINT usuarios_acesso_acesso_id_key UNIQUE (acesso_id);


--
-- TOC entry 4784 (class 2606 OID 57961)
-- Name: vd_cp_loja_virt vd_cp_loja_virt_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vd_cp_loja_virt
    ADD CONSTRAINT vd_cp_loja_virt_pkey PRIMARY KEY (id);


--
-- TOC entry 4801 (class 2620 OID 65562)
-- Name: avaliacao_produto validachavepessoaavaliacaoproduto; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoaavaliacaoproduto BEFORE UPDATE ON public.avaliacao_produto FOR EACH ROW EXECUTE FUNCTION public.validachavepessoa();


--
-- TOC entry 4802 (class 2620 OID 65563)
-- Name: avaliacao_produto validachavepessoaavaliacaoproduto2; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoaavaliacaoproduto2 BEFORE INSERT ON public.avaliacao_produto FOR EACH ROW EXECUTE FUNCTION public.validachavepessoa();


--
-- TOC entry 4807 (class 2620 OID 65569)
-- Name: conta_receber validachavepessoaconta; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoaconta BEFORE UPDATE ON public.conta_receber FOR EACH ROW EXECUTE FUNCTION public.validachavepessoa();


--
-- TOC entry 4809 (class 2620 OID 65571)
-- Name: endereco validachavepessoaconta; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoaconta BEFORE UPDATE ON public.endereco FOR EACH ROW EXECUTE FUNCTION public.validachavepessoa();


--
-- TOC entry 4811 (class 2620 OID 65573)
-- Name: nota_fiscal_compra validachavepessoaconta; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoaconta BEFORE UPDATE ON public.nota_fiscal_compra FOR EACH ROW EXECUTE FUNCTION public.validachavepessoa();


--
-- TOC entry 4813 (class 2620 OID 65575)
-- Name: usuario validachavepessoaconta; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoaconta BEFORE UPDATE ON public.usuario FOR EACH ROW EXECUTE FUNCTION public.validachavepessoa();


--
-- TOC entry 4815 (class 2620 OID 65577)
-- Name: vd_cp_loja_virt validachavepessoaconta; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoaconta BEFORE UPDATE ON public.vd_cp_loja_virt FOR EACH ROW EXECUTE FUNCTION public.validachavepessoa();


--
-- TOC entry 4803 (class 2620 OID 65564)
-- Name: conta_pagar validachavepessoacontapagar; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoacontapagar BEFORE UPDATE ON public.conta_pagar FOR EACH ROW EXECUTE FUNCTION public.validachavepessoa();


--
-- TOC entry 4804 (class 2620 OID 65565)
-- Name: conta_pagar validachavepessoacontapagar2; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoacontapagar2 BEFORE INSERT ON public.conta_pagar FOR EACH ROW EXECUTE FUNCTION public.validachavepessoa();


--
-- TOC entry 4808 (class 2620 OID 65570)
-- Name: conta_receber validachavepessoacontapagar2; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoacontapagar2 BEFORE INSERT ON public.conta_receber FOR EACH ROW EXECUTE FUNCTION public.validachavepessoa();


--
-- TOC entry 4810 (class 2620 OID 65572)
-- Name: endereco validachavepessoacontapagar2; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoacontapagar2 BEFORE INSERT ON public.endereco FOR EACH ROW EXECUTE FUNCTION public.validachavepessoa();


--
-- TOC entry 4812 (class 2620 OID 65574)
-- Name: nota_fiscal_compra validachavepessoacontapagar2; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoacontapagar2 BEFORE INSERT ON public.nota_fiscal_compra FOR EACH ROW EXECUTE FUNCTION public.validachavepessoa();


--
-- TOC entry 4814 (class 2620 OID 65576)
-- Name: usuario validachavepessoacontapagar2; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoacontapagar2 BEFORE INSERT ON public.usuario FOR EACH ROW EXECUTE FUNCTION public.validachavepessoa();


--
-- TOC entry 4816 (class 2620 OID 65578)
-- Name: vd_cp_loja_virt validachavepessoacontapagar2; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoacontapagar2 BEFORE INSERT ON public.vd_cp_loja_virt FOR EACH ROW EXECUTE FUNCTION public.validachavepessoa();


--
-- TOC entry 4805 (class 2620 OID 65567)
-- Name: conta_pagar validachavepessoacontapagarpessoa_forn_id; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoacontapagarpessoa_forn_id BEFORE UPDATE ON public.conta_pagar FOR EACH ROW EXECUTE FUNCTION public.validachavepessoa2();


--
-- TOC entry 4806 (class 2620 OID 65568)
-- Name: conta_pagar validachavepessoacontapagarpessoa_forn_id2; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoacontapagarpessoa_forn_id2 BEFORE INSERT ON public.conta_pagar FOR EACH ROW EXECUTE FUNCTION public.validachavepessoa2();


--
-- TOC entry 4794 (class 2606 OID 57921)
-- Name: usuarios_acesso acesso_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuarios_acesso
    ADD CONSTRAINT acesso_fk FOREIGN KEY (acesso_id) REFERENCES public.acesso(id);


--
-- TOC entry 4789 (class 2606 OID 57896)
-- Name: nota_fiscal_compra conta_pagar_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nota_fiscal_compra
    ADD CONSTRAINT conta_pagar_fk FOREIGN KEY (conta_pagar_id) REFERENCES public.conta_pagar(id);


--
-- TOC entry 4796 (class 2606 OID 57979)
-- Name: vd_cp_loja_virt cumpo_desc_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vd_cp_loja_virt
    ADD CONSTRAINT cumpo_desc_fk FOREIGN KEY (cumpo_desc_id) REFERENCES public.cup_desc(id);


--
-- TOC entry 4797 (class 2606 OID 57984)
-- Name: vd_cp_loja_virt endereco_cobranca_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vd_cp_loja_virt
    ADD CONSTRAINT endereco_cobranca_fk FOREIGN KEY (endereco_cobranca_id) REFERENCES public.endereco(id);


--
-- TOC entry 4798 (class 2606 OID 57989)
-- Name: vd_cp_loja_virt endereco_entrega_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vd_cp_loja_virt
    ADD CONSTRAINT endereco_entrega_fk FOREIGN KEY (endereco_entrega_id) REFERENCES public.endereco(id);


--
-- TOC entry 4799 (class 2606 OID 57994)
-- Name: vd_cp_loja_virt forma_pagamente_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vd_cp_loja_virt
    ADD CONSTRAINT forma_pagamente_fk FOREIGN KEY (forma_pagamente_id) REFERENCES public.forma_pagamento(id);


--
-- TOC entry 4791 (class 2606 OID 57906)
-- Name: nota_item_produto nota_fiscal_compra_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nota_item_produto
    ADD CONSTRAINT nota_fiscal_compra_fk FOREIGN KEY (nota_fiscal_compra_id) REFERENCES public.nota_fiscal_compra(id);


--
-- TOC entry 4800 (class 2606 OID 57999)
-- Name: vd_cp_loja_virt nota_fiscal_venda_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vd_cp_loja_virt
    ADD CONSTRAINT nota_fiscal_venda_fk FOREIGN KEY (nota_fiscal_venda_id) REFERENCES public.nota_fiscal_venda(id);


--
-- TOC entry 4785 (class 2606 OID 57876)
-- Name: avaliacao_produto produto_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.avaliacao_produto
    ADD CONSTRAINT produto_fk FOREIGN KEY (produto_id) REFERENCES public.produto(id);


--
-- TOC entry 4786 (class 2606 OID 57881)
-- Name: imagem_produto produto_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.imagem_produto
    ADD CONSTRAINT produto_fk FOREIGN KEY (produto_id) REFERENCES public.produto(id);


--
-- TOC entry 4787 (class 2606 OID 57886)
-- Name: item_venda_loja produto_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.item_venda_loja
    ADD CONSTRAINT produto_fk FOREIGN KEY (produto_id) REFERENCES public.produto(id);


--
-- TOC entry 4792 (class 2606 OID 57911)
-- Name: nota_item_produto produto_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nota_item_produto
    ADD CONSTRAINT produto_fk FOREIGN KEY (produto_id) REFERENCES public.produto(id);


--
-- TOC entry 4795 (class 2606 OID 57926)
-- Name: usuarios_acesso usuario_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuarios_acesso
    ADD CONSTRAINT usuario_fk FOREIGN KEY (usuario_id) REFERENCES public.usuario(id);


--
-- TOC entry 4790 (class 2606 OID 57969)
-- Name: nota_fiscal_venda venda_compra_loja_virt_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nota_fiscal_venda
    ADD CONSTRAINT venda_compra_loja_virt_fk FOREIGN KEY (venda_compra_loja_virt_id) REFERENCES public.vd_cp_loja_virt(id);


--
-- TOC entry 4793 (class 2606 OID 57974)
-- Name: status_rastreio venda_compra_loja_virt_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.status_rastreio
    ADD CONSTRAINT venda_compra_loja_virt_fk FOREIGN KEY (venda_compra_loja_virt_id) REFERENCES public.vd_cp_loja_virt(id);


--
-- TOC entry 4788 (class 2606 OID 57964)
-- Name: item_venda_loja venda_compraloja_virtu_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.item_venda_loja
    ADD CONSTRAINT venda_compraloja_virtu_fk FOREIGN KEY (venda_compra_loja_virtu_id) REFERENCES public.vd_cp_loja_virt(id);


-- Completed on 2023-11-09 17:14:26

--
-- PostgreSQL database dump complete
--

