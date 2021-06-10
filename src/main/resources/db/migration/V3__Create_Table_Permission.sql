CREATE TABLE IF NOT EXISTS permission (
	codigo serial NOT NULL,
	description character varying(255),
	CONSTRAINT permission_pkey PRIMARY KEY (codigo)
);