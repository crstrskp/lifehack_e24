-- Table: public.parmweight

-- DROP TABLE IF EXISTS public.parmweight;

CREATE TABLE IF NOT EXISTS public.parmweight
(
    weight_id SERIAL PRIMARY KEY,
    user_id integer NOT NULL,
    weight real NOT NULL,
    date timestamp without time zone NOT NULL,
    CONSTRAINT fk_userid FOREIGN KEY (user_id)
    REFERENCES public.users (user_id)
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID
    )
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.parmweight
    OWNER TO postgres;