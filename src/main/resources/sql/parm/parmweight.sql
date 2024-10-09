-- Table: public.parmweight

-- DROP TABLE IF EXISTS public.parmweight;

CREATE TABLE IF NOT EXISTS public.parmweight
(
    weight_id integer NOT NULL DEFAULT nextval('parmweight_weight_id_seq'::regclass),
    user_id integer NOT NULL,
    weight real NOT NULL,
    date timestamp without time zone NOT NULL,
    CONSTRAINT parmweight_pkey PRIMARY KEY (weight_id),
    CONSTRAINT fk_userid FOREIGN KEY (user_id)
    REFERENCES public.users (user_id) MATCH SIMPLE
                   ON UPDATE NO ACTION
                   ON DELETE NO ACTION
    NOT VALID
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.parmweight
    OWNER to postgres;