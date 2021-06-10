CREATE TABLE usuario_permission (
	codigo_usuario BIGINT NOT NULL,
	codigo_permission BIGINT NOT NULL,
	PRIMARY KEY (codigo_usuario, codigo_permission),
	FOREIGN KEY (codigo_usuario) REFERENCES usuario(codigo),
	FOREIGN KEY (codigo_permission) REFERENCES permission(codigo)
);