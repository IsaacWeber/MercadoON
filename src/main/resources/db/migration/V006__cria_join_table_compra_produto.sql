use mercadoon;

create table compra_produto(
	compra_id bigint not null,
    produto_id bigint not null,
	primary key(compra_id, produto_id),
    foreign key (compra_id) references compra(id),
    foreign key (produto_id) references produto(id)
);