--
-- PostgreSQL database dump
--

-- Dumped from database version 16.2 (Debian 16.2-1.pgdg120+2)
-- Dumped by pg_dump version 16.1

-- Started on 2024-10-10 10:10:34 UTC

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 219 (class 1259 OID 16511)
-- Name: event_participants; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.event_participants (
                                           user_id integer NOT NULL,
                                           event_id integer NOT NULL
);


ALTER TABLE public.event_participants OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 16497)
-- Name: eventplanner; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.eventplanner (
                                     event_id integer NOT NULL,
                                     title character varying(20) NOT NULL,
                                     description text,
                                     dateandtime character varying(20),
                                     location character varying(40),
                                     is_owner boolean DEFAULT false,
                                     owner_id integer NOT NULL
);


ALTER TABLE public.eventplanner OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 16496)
-- Name: eventplanner_event_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.eventplanner_event_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.eventplanner_event_id_seq OWNER TO postgres;

--
-- TOC entry 3370 (class 0 OID 0)
-- Dependencies: 217
-- Name: eventplanner_event_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.eventplanner_event_id_seq OWNED BY public.eventplanner.event_id;


--
-- TOC entry 216 (class 1259 OID 16457)
-- Name: users_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_user_id_seq OWNER TO postgres;

--
-- TOC entry 3209 (class 2604 OID 16500)
-- Name: eventplanner event_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.eventplanner ALTER COLUMN event_id SET DEFAULT nextval('public.eventplanner_event_id_seq'::regclass);


--
-- TOC entry 3364 (class 0 OID 16511)
-- Dependencies: 219
-- Data for Name: event_participants; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3363 (class 0 OID 16497)
-- Dependencies: 218
-- Data for Name: eventplanner; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3371 (class 0 OID 0)
-- Dependencies: 217
-- Name: eventplanner_event_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.eventplanner_event_id_seq', 1, false);


--
-- TOC entry 3372 (class 0 OID 0)
-- Dependencies: 216
-- Name: users_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_user_id_seq', 5, true);


--
-- TOC entry 3214 (class 2606 OID 16515)
-- Name: event_participants event_participants_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.event_participants
    ADD CONSTRAINT event_participants_pkey PRIMARY KEY (user_id, event_id);


--
-- TOC entry 3212 (class 2606 OID 16505)
-- Name: eventplanner eventplanner_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.eventplanner
    ADD CONSTRAINT eventplanner_pkey PRIMARY KEY (event_id);


--
-- TOC entry 3216 (class 2606 OID 16521)
-- Name: event_participants event_participants_event_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.event_participants
    ADD CONSTRAINT event_participants_event_id_fkey FOREIGN KEY (event_id) REFERENCES public.eventplanner(event_id);


--
-- TOC entry 3217 (class 2606 OID 16516)
-- Name: event_participants event_participants_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.event_participants
    ADD CONSTRAINT event_participants_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(user_id);


--
-- TOC entry 3215 (class 2606 OID 16506)
-- Name: eventplanner eventplanner_owner_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.eventplanner
    ADD CONSTRAINT eventplanner_owner_id_fkey FOREIGN KEY (owner_id) REFERENCES public.users(user_id);


-- Completed on 2024-10-10 10:10:34 UTC

--
-- PostgreSQL database dump complete
--

