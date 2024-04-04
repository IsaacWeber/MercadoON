use mercadoon;
create table produto(
	id bigint auto_increment,
    nome varchar(100) not null,
    marca varchar(100) not null,
    categoria int not null,
    modelo varchar(100) not null,
    cor varchar(100) not null,
    descricao varchar(2000) not null,
    descricao_tecnica varchar(2000) not null,
    preco double(9,2) not null,
    primary key(id)
);