DROP TABLE IF EXISTS public.parmweight;


CREATE SEQUENCE public.parmweight_weight_id_seq;


CREATE TABLE IF NOT EXISTS public.parmweight
(
    weight_id integer NOT NULL DEFAULT nextval('parmweight_weight_id_seq'),
    user_id integer NOT NULL,
    weight real NOT NULL,
    date timestamp without time zone NOT NULL DEFAULT CURRENT_DATE,
    CONSTRAINT parmweight_pkey PRIMARY KEY (weight_id),
    CONSTRAINT fk_userid FOREIGN KEY (user_id)
    REFERENCES public.users (user_id)
                   ON UPDATE NO ACTION
                   ON DELETE NO ACTION
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.parmweight
    OWNER TO postgres;