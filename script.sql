--
-- PostgreSQL database dump
--

-- Dumped from database version 17.4
-- Dumped by pg_dump version 17.4

-- Started on 2025-04-03 04:40:47

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
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
-- TOC entry 222 (class 1259 OID 475717)
-- Name: category; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.category (
    categoryid integer NOT NULL,
    categoryname character varying(255) NOT NULL
);


ALTER TABLE public.category OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 475716)
-- Name: category_categoryid_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.category_categoryid_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.category_categoryid_seq OWNER TO postgres;

--
-- TOC entry 4906 (class 0 OID 0)
-- Dependencies: 221
-- Name: category_categoryid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.category_categoryid_seq OWNED BY public.category.categoryid;


--
-- TOC entry 226 (class 1259 OID 475736)
-- Name: expense; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.expense (
    expenseid integer NOT NULL,
    expensedate date NOT NULL,
    memberid integer NOT NULL,
    productid integer NOT NULL,
    quantity integer NOT NULL
);


ALTER TABLE public.expense OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 475735)
-- Name: expense_expenseid_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.expense_expenseid_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.expense_expenseid_seq OWNER TO postgres;

--
-- TOC entry 4907 (class 0 OID 0)
-- Dependencies: 225
-- Name: expense_expenseid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.expense_expenseid_seq OWNED BY public.expense.expenseid;


--
-- TOC entry 218 (class 1259 OID 475697)
-- Name: family; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.family (
    familyid integer NOT NULL,
    familyname character varying(255) DEFAULT 'Default Family'::character varying NOT NULL
);


ALTER TABLE public.family OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 475696)
-- Name: family_familyid_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.family_familyid_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.family_familyid_seq OWNER TO postgres;

--
-- TOC entry 4908 (class 0 OID 0)
-- Dependencies: 217
-- Name: family_familyid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.family_familyid_seq OWNED BY public.family.familyid;


--
-- TOC entry 220 (class 1259 OID 475705)
-- Name: familymember; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.familymember (
    memberid integer NOT NULL,
    familyid integer NOT NULL,
    fullname character varying(255) NOT NULL,
    birthdate date
);


ALTER TABLE public.familymember OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 475704)
-- Name: familymember_memberid_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.familymember_memberid_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.familymember_memberid_seq OWNER TO postgres;

--
-- TOC entry 4909 (class 0 OID 0)
-- Dependencies: 219
-- Name: familymember_memberid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.familymember_memberid_seq OWNED BY public.familymember.memberid;


--
-- TOC entry 228 (class 1259 OID 475753)
-- Name: job; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.job (
    jobid integer NOT NULL,
    memberid integer,
    "position" character varying(255),
    organization character varying(255),
    salary numeric(10,2),
    startdate date
);


ALTER TABLE public.job OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 475752)
-- Name: job_jobid_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.job_jobid_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.job_jobid_seq OWNER TO postgres;

--
-- TOC entry 4910 (class 0 OID 0)
-- Dependencies: 227
-- Name: job_jobid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.job_jobid_seq OWNED BY public.job.jobid;


--
-- TOC entry 224 (class 1259 OID 475724)
-- Name: product; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.product (
    productid integer NOT NULL,
    productname character varying(255) NOT NULL,
    categoryid integer NOT NULL,
    unitprice numeric(10,2) NOT NULL
);


ALTER TABLE public.product OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 475723)
-- Name: product_productid_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.product_productid_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.product_productid_seq OWNER TO postgres;

--
-- TOC entry 4911 (class 0 OID 0)
-- Dependencies: 223
-- Name: product_productid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.product_productid_seq OWNED BY public.product.productid;


--
-- TOC entry 4723 (class 2604 OID 475720)
-- Name: category categoryid; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.category ALTER COLUMN categoryid SET DEFAULT nextval('public.category_categoryid_seq'::regclass);


--
-- TOC entry 4725 (class 2604 OID 475739)
-- Name: expense expenseid; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.expense ALTER COLUMN expenseid SET DEFAULT nextval('public.expense_expenseid_seq'::regclass);


--
-- TOC entry 4720 (class 2604 OID 475700)
-- Name: family familyid; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.family ALTER COLUMN familyid SET DEFAULT nextval('public.family_familyid_seq'::regclass);


--
-- TOC entry 4722 (class 2604 OID 475708)
-- Name: familymember memberid; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.familymember ALTER COLUMN memberid SET DEFAULT nextval('public.familymember_memberid_seq'::regclass);


--
-- TOC entry 4726 (class 2604 OID 475756)
-- Name: job jobid; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.job ALTER COLUMN jobid SET DEFAULT nextval('public.job_jobid_seq'::regclass);


--
-- TOC entry 4724 (class 2604 OID 475727)
-- Name: product productid; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product ALTER COLUMN productid SET DEFAULT nextval('public.product_productid_seq'::regclass);


--
-- TOC entry 4894 (class 0 OID 475717)
-- Dependencies: 222
-- Data for Name: category; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.category (categoryid, categoryname) FROM stdin;
1	Продукты
2	Транспорт
3	Развлечения
4	Одежда
5	Образование
6	Здоровье
7	Кредиты
8	Подарки
\.


--
-- TOC entry 4898 (class 0 OID 475736)
-- Dependencies: 226
-- Data for Name: expense; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.expense (expenseid, expensedate, memberid, productid, quantity) FROM stdin;
1	2025-02-01	1	1	2
2	2025-02-01	1	2	3
3	2025-02-01	1	2	1
4	2025-02-06	2	5	1
5	2025-02-09	5	3	30
6	2025-02-12	4	7	5
7	2025-02-18	1	8	1
8	2025-02-22	2	9	1
9	2025-02-22	3	4	1
\.


--
-- TOC entry 4890 (class 0 OID 475697)
-- Dependencies: 218
-- Data for Name: family; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.family (familyid, familyname) FROM stdin;
1	Default Family
\.


--
-- TOC entry 4892 (class 0 OID 475705)
-- Dependencies: 220
-- Data for Name: familymember; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.familymember (memberid, familyid, fullname, birthdate) FROM stdin;
1	1	Иванов Иван Петрович	1985-06-15
4	1	Иванова Елена Викторовна	1959-03-10
5	1	Петров Сергей Александрович	1982-12-05
6	1	nikita	2003-12-12
3	1	Иванов Алексей Иванович	2010-02-05
2	1	Иванова Мария Сергеевна	1987-09-20
\.


--
-- TOC entry 4900 (class 0 OID 475753)
-- Dependencies: 228
-- Data for Name: job; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.job (jobid, memberid, "position", organization, salary, startdate) FROM stdin;
1	1	Инженер	ООО "ТехноСервис"	85000.00	2015-01-03
4	5	Таксист	Индивидуальная работа	30000.00	2020-09-01
3	3	Репетитор	Частная практика	15000.00	2020-01-10
2	2	Бухгалтер/	АО "ФинансГрупп"	75000.00	2012-04-15
\.


--
-- TOC entry 4896 (class 0 OID 475724)
-- Dependencies: 224
-- Data for Name: product; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.product (productid, productname, categoryid, unitprice) FROM stdin;
1	Хлеб	1	50.00
2	Молоко	1	80.00
3	Бензин	2	80.00
4	Билет в кино	3	300.00
5	Зимнее пальто	4	8500.00
6	Учебники	5	400.00
7	Лекарства	6	25000.00
8	Погашение кредита	7	89000.00
9	Смартфон в подарок	8	99900.00
\.


--
-- TOC entry 4912 (class 0 OID 0)
-- Dependencies: 221
-- Name: category_categoryid_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.category_categoryid_seq', 8, true);


--
-- TOC entry 4913 (class 0 OID 0)
-- Dependencies: 225
-- Name: expense_expenseid_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.expense_expenseid_seq', 9, true);


--
-- TOC entry 4914 (class 0 OID 0)
-- Dependencies: 217
-- Name: family_familyid_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.family_familyid_seq', 1, true);


--
-- TOC entry 4915 (class 0 OID 0)
-- Dependencies: 219
-- Name: familymember_memberid_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.familymember_memberid_seq', 6, true);


--
-- TOC entry 4916 (class 0 OID 0)
-- Dependencies: 227
-- Name: job_jobid_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.job_jobid_seq', 4, true);


--
-- TOC entry 4917 (class 0 OID 0)
-- Dependencies: 223
-- Name: product_productid_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.product_productid_seq', 9, true);


--
-- TOC entry 4732 (class 2606 OID 475722)
-- Name: category category_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.category
    ADD CONSTRAINT category_pkey PRIMARY KEY (categoryid);


--
-- TOC entry 4736 (class 2606 OID 475741)
-- Name: expense expense_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.expense
    ADD CONSTRAINT expense_pkey PRIMARY KEY (expenseid);


--
-- TOC entry 4728 (class 2606 OID 475703)
-- Name: family family_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.family
    ADD CONSTRAINT family_pkey PRIMARY KEY (familyid);


--
-- TOC entry 4730 (class 2606 OID 475710)
-- Name: familymember familymember_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.familymember
    ADD CONSTRAINT familymember_pkey PRIMARY KEY (memberid);


--
-- TOC entry 4738 (class 2606 OID 475760)
-- Name: job job_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.job
    ADD CONSTRAINT job_pkey PRIMARY KEY (jobid);


--
-- TOC entry 4734 (class 2606 OID 475729)
-- Name: product product_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT product_pkey PRIMARY KEY (productid);


--
-- TOC entry 4741 (class 2606 OID 475742)
-- Name: expense expense_memberid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.expense
    ADD CONSTRAINT expense_memberid_fkey FOREIGN KEY (memberid) REFERENCES public.familymember(memberid) ON DELETE CASCADE;


--
-- TOC entry 4742 (class 2606 OID 475747)
-- Name: expense expense_productid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.expense
    ADD CONSTRAINT expense_productid_fkey FOREIGN KEY (productid) REFERENCES public.product(productid) ON DELETE CASCADE;


--
-- TOC entry 4739 (class 2606 OID 475711)
-- Name: familymember familymember_familyid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.familymember
    ADD CONSTRAINT familymember_familyid_fkey FOREIGN KEY (familyid) REFERENCES public.family(familyid) ON DELETE CASCADE;


--
-- TOC entry 4743 (class 2606 OID 475761)
-- Name: job job_memberid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.job
    ADD CONSTRAINT job_memberid_fkey FOREIGN KEY (memberid) REFERENCES public.familymember(memberid);


--
-- TOC entry 4740 (class 2606 OID 475730)
-- Name: product product_categoryid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT product_categoryid_fkey FOREIGN KEY (categoryid) REFERENCES public.category(categoryid) ON DELETE CASCADE;


-- Completed on 2025-04-03 04:40:47

--
-- PostgreSQL database dump complete
--

