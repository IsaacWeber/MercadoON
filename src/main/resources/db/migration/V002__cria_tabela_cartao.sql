use mercadoon;

create table cartao(
	id bigint auto_increment,
    nome_usuario varchar(100) not null,
    numero varchar(100) not null,
    cvv varchar(10) not null,
    validade varchar(10) not null,
    bandeira int not null,
    funcao int not null,
    cliente_id bigint,
    primary key(id),
    foreign key (cliente_id) references cliente(id)
);