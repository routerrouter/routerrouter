CREATE TABLE IF NOT EXISTS "armazem" (
	"codigo" serial NOT NULL,
	"capacidade" INTEGER NOT NULL,
	"designacao" VARCHAR(180) NOT NULL,
	"enabled" BOOLEAN NOT NULL,
	"createdBy" VARCHAR(180),
	"createdDate" TIMESTAMP,
	"lastModifiedBy" VARCHAR(100),
	"lastModifiedDate" TIMESTAMP,
	 CONSTRAINT armazem_pkey PRIMARY KEY (codigo)
);