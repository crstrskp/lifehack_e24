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

-- Create the public schema
CREATE SCHEMA public;

-- Users table
CREATE TABLE public.users
(
    user_id  SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE         NOT NULL,
    password VARCHAR(50)                NOT NULL,
    role     VARCHAR(20) DEFAULT 'USER' NOT NULL
);

-- Additional user details table
CREATE TABLE public.ss_user_detail
(
    detail_id  SERIAL PRIMARY KEY,
    user_id    INTEGER NOT NULL,
    email      VARCHAR(255) UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES public.users (user_id)
);

-- Brands table
CREATE TABLE public.ss_brand
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);

-- Categories table
CREATE TABLE public.ss_category
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);

-- Ice cream table
CREATE TABLE public.ss_ice_cream
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(255) UNIQUE NOT NULL,
    brand_id    INT                 NOT NULL,
    description TEXT,
    category_id INT,
    image_url   TEXT,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_brand FOREIGN KEY (brand_id) REFERENCES public.ss_brand (id),
    CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES public.ss_category (id)
);

-- Rank list table
CREATE TABLE public.ss_rank_list
(
    id          SERIAL PRIMARY KEY,
    user_id     INT,
    title       VARCHAR(255) NOT NULL,
    description TEXT,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_public   BOOLEAN   DEFAULT TRUE,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES public.users (user_id)
);

-- Rank list item table
CREATE TABLE public.ss_rank_list_item
(
    id           SERIAL PRIMARY KEY,
    rank_list_id INT NOT NULL,
    ice_cream_id INT NOT NULL,
    tier         VARCHAR(1) CHECK (tier IN ('S', 'A', 'B', 'C', 'D', 'E', 'F')),
    position     INT,
    CONSTRAINT fk_rank_list FOREIGN KEY (rank_list_id) REFERENCES public.ss_rank_list (id) ON DELETE CASCADE,
    CONSTRAINT fk_ice_cream FOREIGN KEY (ice_cream_id) REFERENCES public.ss_ice_cream (id) ON DELETE CASCADE
);

-- Votes table
CREATE TABLE public.ss_vote
(
    id           SERIAL PRIMARY KEY,
    rank_list_id INT NOT NULL,
    voter_id     INT NOT NULL,
    vote_value   INT CHECK (vote_value IN (1, -1)),
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_rank_list_vote FOREIGN KEY (rank_list_id) REFERENCES public.ss_rank_list (id) ON DELETE CASCADE,
    CONSTRAINT fk_voter FOREIGN KEY (voter_id) REFERENCES public.users (user_id) ON DELETE CASCADE
);

-- Insert sample data into the users table first
INSERT INTO public.users (username, password, role)
VALUES ('jon', '1234', 'user'),
       ('bingo', '1234', 'admin'),
       ('ole', '1234', 'postgres'),
       ('dennis', '1234', 'postgres');

-- Now we can insert into the brands table
INSERT INTO public.ss_brand (name)
VALUES ('Ben & Jerrys'),
       ('Premier'),
       ('Frisko'),
       ('Gudhjemis'),
       ('Naturli'),
       ('Ismageriet'),
       ('Polar is');

-- Insert sample data into the ice cream table
INSERT INTO public.ss_ice_cream (name, brand_id, description)
VALUES ('Caramel Brownie Party', 1,
        'The best ice cream on the market - it is a caramel ice cream with caramel sauce swirls and gooey brownie chunks. It is addictive! - Absolutely S-tier'),
       ('Cookie Dough Swich Up', 1,
        'Vanilla ice cream with cocoa cookies, pieces of cookie dough with chocolate pieces, and swirl of cocoa cookies'),
       ('Skildpaddeis', 2,
        'Rum flavored ice cream with 16% caramel sauce and 10% chocolate pieces. Absolutely S-tier. Try it with bananas!'),
       ('Lemon sorbet', 4, 'Lemon flavored sorbet ice cream - Recommended by Christoffer'),
       ('Cookie dough deluxe', 5,
        'Vegan vanilla ice cream with chocolate chip cookie dough chunks and chocolate pieces. - Good vegan alternative to Rema1000s or Ben & Jerrys'),
       ('Tricolore is', 7, 'Stabil regnbue is - isen som alle kender fra deres barndom'),
       ('Oreo', 3, 'Vanilla ice cream with crushed Oreo cookies'),
       ('Guldhorn', 2, 'Crunchy wafel filled with vanilla'),
       ('Magnum Double White Chocolate & Cookies', 4,
        'Vanilla ice cream with white chocolate chunks, chocolate cookie-crumblesauce and a thick layer of white chocolate on top'),
       ('Zapp', 3, 'Popsicle with caramel flavor and pretty colors'),
       ('Mango-sorbet', 6, 'Amazing sorbet, refreshing sorbet with the freshness from the mango'),
       ('Kokos-sorbet', 6, 'Coconut sorbet, refreshing sorbet with the delicious taste of coconuts'),
       ('Lakrids is', 3, 'Liquorice flavored ice cream'),
       ('VegaNice Choco/Mint', 2, 'Vegan chocolate and mint ice cream in a black waffle cone');

-- Now we can insert into the rank list table, which references the users table
INSERT INTO public.ss_rank_list (user_id, title)
VALUES (1, 'Daniels liste'),
       (2, 'Rikkes liste');

INSERT INTO public.ss_rank_list_item (id, rank_list_id, ice_cream_id, tier, position)
VALUES (1, 1, 1, 'S', 1);

-- Completed the data insertion.