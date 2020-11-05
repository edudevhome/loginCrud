CREATE DATABASE 'usuario_crud';
USE demo;

create table users (
	id  int(3) NOT NULL AUTO_INCREMENT,
	nome varchar(100) NOT NULL,
	email varchar(100) NOT NULL,
	senha varchar(100) Not Null,
	PRIMARY KEY (id)
);

