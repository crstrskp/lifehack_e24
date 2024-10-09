-- Table: public.eventplanner

-- DROP TABLE IF EXISTS public.eventplanner;

CREATE TABLE IF NOT EXISTS public.eventplanner
(
    event_id integer NOT NULL DEFAULT nextval('eventplanner_event_id_seq'::regclass),
    is_owner boolean NOT NULL DEFAULT false,
    date_and_time character varying(20) COLLATE pg_catalog."default" NOT NULL,
    location character varying(50) COLLATE pg_catalog."default" NOT NULL,
    title character varying(20) COLLATE pg_catalog."default" NOT NULL,
    description character varying COLLATE pg_catalog."default",
    participants integer NOT NULL,
    owner_id integer,
    CONSTRAINT eventplanner_pkey PRIMARY KEY (event_id),
    CONSTRAINT owner_id FOREIGN KEY (owner_id)
        REFERENCES public.users (user_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT participants FOREIGN KEY (participants)
        REFERENCES public.users (user_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.eventplanner
    OWNER to postgres;
-- Index: fki_participants

-- DROP INDEX IF EXISTS public.fki_participants;

CREATE INDEX IF NOT EXISTS fki_participants
    ON public.eventplanner USING btree
        (participants ASC NULLS LAST)
    TABLESPACE pg_default;