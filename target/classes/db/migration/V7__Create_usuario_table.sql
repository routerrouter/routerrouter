CREATE TABLE usuario
(
    codigo serial NOT NULL,
    account_non_expired boolean,
    account_non_locked boolean,
    credentials_non_expired boolean,
    enabled boolean,
    full_name character varying(255) COLLATE pg_catalog."default",
    password character varying(255) COLLATE pg_catalog."default",
    user_name character varying(255) COLLATE pg_catalog."default",
    armazem_id bigint NOT NULL,
    CONSTRAINT usuario_pkey PRIMARY KEY (codigo),
    CONSTRAINT uk_k8d0f2n7n88w1a16yhua64onx UNIQUE (user_name),
    CONSTRAINT fk52lc2hcsv0efos4pq5j2f5f4h FOREIGN KEY (armazem_id)
        REFERENCES armazem (codigo) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);