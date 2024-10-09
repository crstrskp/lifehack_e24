
CREATE TABLE IF NOT EXISTS public.motivational_quotes
(
    id serial NOT NULL,
    title character varying(64) NOT NULL,
    text character varying,
    image_url character varying(150),
    author_id integer NOT NULL,
    PRIMARY KEY (id)
    );

ALTER TABLE IF EXISTS public.motivational_quotes
    ADD CONSTRAINT fk_author_id FOREIGN KEY (author_id)
    REFERENCES users (user_id) MATCH SIMPLE
    ON UPDATE NO ACTION
       ON DELETE NO ACTION
    NOT VALID;
