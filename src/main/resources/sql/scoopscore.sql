-- Roles Table: defines roles like 'guest', 'user', 'admin_basic', 'admin_super'
-- TODO: rewrite users table to match lifehack DB, put new columns into a seperate table
CREATE TABLE roles (
                       id SERIAL PRIMARY KEY,
                       role_name VARCHAR(50) UNIQUE NOT NULL  -- Possible values: 'guest', 'user', 'admin_basic', 'admin_super'
);

-- Users Table: stores user information with a reference to the role
CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(255) UNIQUE NOT NULL,
                       email VARCHAR(255) UNIQUE NOT NULL,
                       password_hash VARCHAR(255) NOT NULL,
                       role_id INT NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       CONSTRAINT fk_role
                           FOREIGN KEY(role_id)
                               REFERENCES roles(id)
);

-- Brands Table: stores information about ice cream brands
CREATE TABLE brands (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(255) UNIQUE NOT NULL
);

-- Categories Table: stores categories of ice creams, like 'Dairy', 'Vegan', etc.
CREATE TABLE categories (
                            id SERIAL PRIMARY KEY,
                            name VARCHAR(255) UNIQUE NOT NULL
);

-- Ice Creams Table: stores information about different ice cream products
CREATE TABLE ice_creams (
                            id SERIAL PRIMARY KEY,
                            name VARCHAR(255) UNIQUE NOT NULL,  -- Stores unique names like "Caramel Brownie Party"
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
CREATE TABLE rank_lists (
                            id SERIAL PRIMARY KEY,
                            user_id INT NULL,  -- NULL for guest users
                            title VARCHAR(255) NOT NULL,
                            description TEXT,
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            is_public BOOLEAN DEFAULT TRUE,  -- Guests' lists will always be private
                            CONSTRAINT fk_user
                                FOREIGN KEY(user_id)
                                    REFERENCES users(id)
);

-- Rank List Items Table: stores each ice cream in a rank list with a tier from S to F
CREATE TABLE rank_list_items (
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
CREATE TABLE votes (
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
                               REFERENCES users(id) ON DELETE CASCADE
);

-- Insert initial roles
INSERT INTO roles (role_name) VALUES
                                  ('guest'),
                                  ('user'),
                                  ('admin_basic'),
                                  ('admin_super');

-- Example brand and category entries (optional for initial setup)
INSERT INTO brands (name) VALUES ('Ben & Jerry''s'), ('Häagen-Dazs');
INSERT INTO categories (name) VALUES ('Dairy'), ('Vegan'), ('Low-Calorie');
