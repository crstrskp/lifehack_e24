--
-- PostgreSQL database dump
--

-- Dumped from database version 16.2 (Debian 16.2-1.pgdg120+2)
-- Dumped by pg_dump version 16.1

-- Started on 2024-03-16 08:31:29 UTC

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
-- TOC entry 4 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: pg_database_owner
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO pg_database_owner;

--
-- TOC entry 3359 (class 0 OID 0)
-- Dependencies: 4
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: pg_database_owner
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 215 (class 1259 OID 24578)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
                              user_id integer NOT NULL,
                              username character varying(50) NOT NULL,
                              password character varying(50) NOT NULL,
                              role character varying(20) DEFAULT USER NOT NULL
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 24582)
-- Name: users_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_user_id_seq OWNER TO postgres;

--
-- TOC entry 3360 (class 0 OID 0)
-- Dependencies: 216
-- Name: users_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_user_id_seq OWNED BY public.users.user_id;


--
-- TOC entry 3203 (class 2604 OID 24583)
-- Name: users user_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN user_id SET DEFAULT nextval('public.users_user_id_seq'::regclass);


--
-- TOC entry 3352 (class 0 OID 24578)
-- Dependencies: 215
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.users VALUES (1, 'jon', '1234', 'user');
INSERT INTO public.users VALUES (2, 'bingo', '1234', 'admin');
INSERT INTO public.users VALUES (3, 'ole', '1234', 'postgres');
INSERT INTO public.users VALUES (5, 'dennis', '1234', 'postgres');


--
-- TOC entry 3361 (class 0 OID 0)
-- Dependencies: 216
-- Name: users_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_user_id_seq', 5, true);


--
-- TOC entry 3206 (class 2606 OID 24585)
-- Name: users unique_username; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT unique_username UNIQUE (username);


--
-- TOC entry 3208 (class 2606 OID 24587)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (user_id);


-- Completed on 2024-03-16 08:31:29 UTC

--
-- PostgreSQL database dump complete
--

-- ScoopScores database
CREATE TABLE public.ss_user_detail (
                                       detail_id SERIAL PRIMARY KEY,
                                       user_id INTEGER NOT NULL,
                                       email VARCHAR(255) UNIQUE,
                                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                       CONSTRAINT fk_user
                                           FOREIGN KEY(user_id)
                                               REFERENCES public.users(user_id)
);

-- Brands table
CREATE TABLE public.ss_brand (
                                 id SERIAL PRIMARY KEY,
                                 name VARCHAR(255) UNIQUE NOT NULL
);

-- Categories table
CREATE TABLE public.ss_category (
                                    id SERIAL PRIMARY KEY,
                                    name VARCHAR(255) UNIQUE NOT NULL
);

-- Ice cream table
CREATE TABLE public.ss_ice_cream (
                                     id SERIAL PRIMARY KEY,
                                     name VARCHAR(255) UNIQUE NOT NULL,
                                     brand_id INT NOT NULL,
                                     description TEXT,
                                     category_id INT,
                                     image_url TEXT,
                                     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                     CONSTRAINT fk_brand
                                         FOREIGN KEY(brand_id)
                                             REFERENCES public.ss_brand(id),
                                     CONSTRAINT fk_category
                                         FOREIGN KEY(category_id)
                                             REFERENCES public.ss_category(id)
);

-- Rank list table
CREATE TABLE public.ss_rank_list (
                                     id SERIAL PRIMARY KEY,
                                     user_id INT,
                                     title VARCHAR(255) NOT NULL,
                                     description TEXT,
                                     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                     is_public BOOLEAN DEFAULT TRUE,
                                     CONSTRAINT fk_user
                                         FOREIGN KEY(user_id)
                                             REFERENCES public.users(user_id)
);

-- Rank list item table
CREATE TABLE public.ss_rank_list_item (
                                          id SERIAL PRIMARY KEY,
                                          rank_list_id INT NOT NULL,
                                          ice_cream_id INT NOT NULL,
                                          tier VARCHAR(1) CHECK (tier IN ('S', 'A', 'B', 'C', 'D', 'E', 'F')),
                                          position INT,
                                          CONSTRAINT fk_rank_list
                                              FOREIGN KEY(rank_list_id)
                                                  REFERENCES public.ss_rank_list(id) ON DELETE CASCADE,
                                          CONSTRAINT fk_ice_cream
                                              FOREIGN KEY(ice_cream_id)
                                                  REFERENCES public.ss_ice_cream(id) ON DELETE CASCADE
);

-- Votes table
CREATE TABLE public.ss_vote (
                                id SERIAL PRIMARY KEY,
                                rank_list_id INT NOT NULL,
                                voter_id INT NOT NULL,
                                vote_value INT CHECK (vote_value IN (1, -1)),
                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                CONSTRAINT fk_rank_list_vote
                                    FOREIGN KEY(rank_list_id)
                                        REFERENCES public.ss_rank_list(id) ON DELETE CASCADE,
                                CONSTRAINT fk_voter
                                    FOREIGN KEY(voter_id)
                                        REFERENCES public.users(user_id) ON DELETE CASCADE
);
INSERT INTO public.ss_brand (name)
VALUES ('Ben & Jerrys'),
       ('Premier'),
       ('Frisko'),
       ('Gudhjemis'),
       ('Naturli'),
       ('Ismageriet'),
       ('Polar is');

INSERT INTO public.ss_ice_cream (name,brand_id,description)
VALUES ('Caramel Brownie Party',1,'The best icecream on the market - it is a caramel ice cream with caramel sauce swirls and gooey brownies chunks. It is addictive! - Absolutely S-tier'),
       ('Cookie Dough Swich Up',1,'Vanilla icecream with cocoacookies, pieces of cookie dough with chocolate pieces, and swirl of cocoacookies'),
       ('Skildpaddeis', 2,'Rum flavoured icecream with 16% caramelsauce and 10% chokolatepieces. Absolutely S-tier. Try it with bananas!'),
       ('Lemon sorbet',4,'Lemon flavoured sorbet icecream - Recommended by Christoffer'),
       ('Cookie dough deluxe',5,'Vegan vanilla icecream with chocolate chip cookie dough chunks and chocolate pieces. - Good vegan alternative to Rema1000s or Ben & Jerrys'),
       ('Tricolore is',7,'Stabil regnbue is - isen som alle kender fra deres barndom'),
       ('Oreo', 3,'Vanilla icecream with crushed oreocookies'),
       ('Guldhorn',2,'Crunchy waffel filled with vannila'),
       ('Magnum Double White Chocolate & Cookies',4,'Vanilla icecream with white chocolate chunks, chocolate cookie-crumblesauce and a thick layer of white chocolate on top'),
       ('Zapp',3,'Popsicle with caramel flavour and pretty colours'),
       ('Mango-sorbet',6,'Amazing sorbet, refreshing sorbet with the freshness from the mango'),
       ('Kokos-sorbet',6,'Coconut sorbet, refreshing sorbet with the delicious taste of coconuts'),
       ('Lakrids is',3,'Liquorice flavoured ice cream'),
       ('VegaNice Choco/Mint', 2, 'Vegan chocolate and mint icecream in a black waffel cone');

INSERT INTO public.ss_rank_list(user_id, title)
VALUES (1, 'Daniels liste'),
       (2, 'Rikkes liste');
