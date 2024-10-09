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
                                          tier CHAR(1) CHECK (tier IN ('S', 'A', 'B', 'C', 'D', 'E', 'F')),
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
