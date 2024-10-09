-- Retain the existing 'users' table as defined in the provided dump
CREATE TABLE public.users (
                              user_id SERIAL PRIMARY KEY,
                              username VARCHAR(50) UNIQUE NOT NULL,
                              password VARCHAR(50) NOT NULL,
                              role VARCHAR(20) DEFAULT 'USER' NOT NULL  -- Existing role field kept as is
);

-- Create the user_details table for additional user information
CREATE TABLE public.user_details (
                                     detail_id SERIAL PRIMARY KEY,
                                     user_id INTEGER NOT NULL,                -- Foreign key linking to users table
                                     email VARCHAR(255) UNIQUE,               -- Email field, unique for each user
                                     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Created timestamp for user details
                                     CONSTRAINT fk_user
                                         FOREIGN KEY(user_id)
                                             REFERENCES public.users(user_id)      -- Foreign key constraint referencing users table
);

-- Brands Table: stores ice cream brands
CREATE TABLE public.brands (
                               id SERIAL PRIMARY KEY,
                               name VARCHAR(255) UNIQUE NOT NULL
);

-- Categories Table: stores categories of ice creams, like 'Dairy', 'Vegan', etc.
CREATE TABLE public.categories (
                                   id SERIAL PRIMARY KEY,
                                   name VARCHAR(255) UNIQUE NOT NULL
);

-- Ice Creams Table: stores information about different ice cream products
CREATE TABLE public.ice_creams (
                                   id SERIAL PRIMARY KEY,
                                   name VARCHAR(255) UNIQUE NOT NULL,
                                   brand_id INT NOT NULL,              -- References the brand (e.g., Ben & Jerry's)
                                   description TEXT,                   -- Describes the flavor's ingredients or special details
                                   category_id INT,                    -- References the category (e.g., Vegan)
                                   image_url TEXT,                     -- Optional URL for an image of the flavor
                                   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                   CONSTRAINT fk_brand
                                       FOREIGN KEY(brand_id)
                                           REFERENCES brands(id),
                                   CONSTRAINT fk_category
                                       FOREIGN KEY(category_id)
                                           REFERENCES categories(id)
);

-- Rank Lists Table: stores user-created tier lists for ranking ice creams
CREATE TABLE public.rank_lists (
                                   id SERIAL PRIMARY KEY,
                                   user_id INT,  -- NULL for guest users
                                   title VARCHAR(255) NOT NULL,
                                   description TEXT,
                                   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                   is_public BOOLEAN DEFAULT TRUE,  -- Guests' lists will always be private
                                   CONSTRAINT fk_user
                                       FOREIGN KEY(user_id)
                                           REFERENCES public.users(user_id)
);

-- Rank List Items Table: stores each ice cream in a rank list with a tier from S to F
CREATE TABLE public.rank_list_items (
                                        id SERIAL PRIMARY KEY,
                                        rank_list_id INT NOT NULL,
                                        ice_cream_id INT NOT NULL,
                                        tier CHAR(1) CHECK (tier IN ('S', 'A', 'B', 'C', 'D', 'E', 'F')),  -- Defines the tier of the ice cream
                                        position INT,  -- Optional, to allow custom order within tiers if needed
                                        CONSTRAINT fk_rank_list
                                            FOREIGN KEY(rank_list_id)
                                                REFERENCES rank_lists(id) ON DELETE CASCADE,
                                        CONSTRAINT fk_ice_cream
                                            FOREIGN KEY(ice_cream_id)
                                                REFERENCES ice_creams(id) ON DELETE CASCADE
);

-- Votes Table: stores votes on rank lists to allow users to rate each other's lists
CREATE TABLE public.votes (
                              id SERIAL PRIMARY KEY,
                              rank_list_id INT NOT NULL,
                              voter_id INT NOT NULL,
                              vote_value INT CHECK (vote_value IN (1, -1)),  -- 1 for upvote, -1 for downvote
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              CONSTRAINT fk_rank_list_vote
                                  FOREIGN KEY(rank_list_id)
                                      REFERENCES rank_lists(id) ON DELETE CASCADE,
                              CONSTRAINT fk_voter
                                  FOREIGN KEY(voter_id)
                                      REFERENCES users(user_id) ON DELETE CASCADE
);

INSERT INTO public.users VALUES (1, 'jon', '1234', 'user');
INSERT INTO public.users VALUES (2, 'bingo', '1234', 'admin');
INSERT INTO public.users VALUES (3, 'ole', '1234', 'postgres');
INSERT INTO public.users VALUES (5, 'dennis', '1234', 'postgres');
