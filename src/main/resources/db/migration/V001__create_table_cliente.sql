create table cliente(
	id bigint auto_increment,
    nome varchar(100) not null,
    sobrenome varchar(100) not null,
    cpf varchar(12) not null,
    email varchar(100) not null,
    endereco varchar(100) not null,
    membro_desde date not null,
    primary key(id)
);

