-- Drop all tables in the public schema
DO $$ DECLARE
    tbl RECORD;
BEGIN
    -- Loop through each table in the public schema
    FOR tbl IN
        SELECT tablename
        FROM pg_tables
        WHERE schemaname = 'public'
        LOOP
            EXECUTE 'DROP TABLE IF EXISTS public.' || quote_ident(tbl.tablename) || ' CASCADE';
        END LOOP;
END $$;

-- Recreate the schema with updated table names
-- Users table remains unchanged
CREATE TABLE public.users (
                              user_id SERIAL PRIMARY KEY,
                              username VARCHAR(50) UNIQUE NOT NULL,
                              password VARCHAR(50) NOT NULL,
                              role VARCHAR(20) DEFAULT 'USER' NOT NULL
);

-- Insert initial users
INSERT INTO public.users (user_id, username, password, role) VALUES
                                                                 (1, 'jon', '1234', 'user'),
                                                                 (2, 'bingo', '1234', 'admin'),
                                                                 (3, 'ole', '1234', 'postgres'),
                                                                 (5, 'dennis', '1234', 'postgres');

-- Additional user details table
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