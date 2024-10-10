DROP TABLE IF EXISTS public.motivational_quotes;

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

DROP TABLE IF EXISTS public.motivational_favorites;

CREATE TABLE IF NOT EXISTS public.motivational_favorites
(
    user_id integer NOT NULL,
    quote_id integer NOT NULL,
    CONSTRAINT motivational_favorites_pkey PRIMARY KEY (user_id, quote_id),
    CONSTRAINT f_quote FOREIGN KEY (quote_id)
        REFERENCES public.motivational_quotes (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT f_userid FOREIGN KEY (user_id)
        REFERENCES public.users (user_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

INSERT INTO public.motivational_quotes(title, text, image_url, author_id) VALUES ('Journey', '"The journey of a thousand miles begins with one step." – Lao Tzu', NULL, 1);
INSERT INTO public.motivational_quotes(title, text, image_url, author_id) VALUES ('Chance/change', '"Your life does not get better by chance, it gets better by change." – Jim Rohn', NULL, 1);
INSERT INTO public.motivational_quotes(title, text, image_url, author_id) VALUES ('Lewis: Hardships', '"Hardships often prepare ordinary people for an extraordinary destiny." – C.S. Lewis', NULL, 1);
INSERT INTO public.motivational_quotes(title, text, image_url, author_id) VALUES ('Keep going', '"Don''t watch the clock; do what it does. Keep going." – Sam Levenson', NULL, 1);
INSERT INTO public.motivational_quotes(title, text, image_url, author_id) VALUES ('Limits in doubt', '"The only limit to our realization of tomorrow is our doubts of today." – Franklin D. Roosevelt', NULL, 1);
INSERT INTO public.motivational_quotes(title, text, image_url, author_id) VALUES ('Halfway there', '"Believe you can and you''re halfway there." – Theodore Roosevelt', NULL, 2);
INSERT INTO public.motivational_quotes(title, text, image_url, author_id) VALUES ('Courage to continue', '"Success is not final, failure is not fatal: It is the courage to continue that counts." – Winston Churchill', NULL, 1);
