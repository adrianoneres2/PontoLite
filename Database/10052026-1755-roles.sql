--
-- PostgreSQL database cluster dump
--

SET default_transaction_read_only = off;

SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;

--
-- Roles
--

CREATE ROLE postgres;
ALTER ROLE postgres WITH SUPERUSER INHERIT CREATEROLE CREATEDB LOGIN REPLICATION BYPASSRLS PASSWORD 'SCRAM-SHA-256$4096:pDolyFhNf3Jq3DeLfqgYpw==$8Xbgai4xaqNiQcVzHmHSfO29cnEROttGBd6WV7C/ph0=:zsrazN2WaWyiW8BbaapPOi+AVnD9Akdc8r796ojoQbc=';
CREATE ROLE usr_happytraffic;
ALTER ROLE usr_happytraffic WITH NOSUPERUSER INHERIT NOCREATEROLE NOCREATEDB LOGIN NOREPLICATION NOBYPASSRLS;
CREATE ROLE usr_pontolite;
ALTER ROLE usr_pontolite WITH NOSUPERUSER INHERIT NOCREATEROLE NOCREATEDB LOGIN NOREPLICATION NOBYPASSRLS PASSWORD 'SCRAM-SHA-256$4096:BjnpK92bfxALTL31owT6/Q==$0KGwOZDM4A0QQnXoxpKpVbcVFlcpV+f6lDXa0T81Bv0=:yHhinCP14xjhCUNvQqeP7k46U/eNS43XdPqQ0/CFUg8=';

--
-- User Configurations
--








--
-- PostgreSQL database cluster dump complete
--

