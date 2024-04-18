use mercadoon;

create table compra(
	id bigint auto_increment,
	cliente_id bigint not null,
    cartao_id bigint not null,
    realizacao date,
    previsao_entrega date,
    entrega date,
    
    primary key(id),
    
    foreign key (cliente_id) references cliente(id),
    foreign key (cartao_id) references cartao(id)
);