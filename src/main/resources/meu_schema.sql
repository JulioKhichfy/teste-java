create table cliente (
    id integer not null,
    codigo integer,
    nome varchar(100),
    primary key (id)
);

create table produto (
    id integer not null,
    preco_unitario decimal(38,2),
    descricao varchar(255),
    primary key (id)
);

create table pedido (
    cliente_id integer,
    data_pedido date,
    id integer not null,
    total decimal(20,2),
    primary key (id)
);

create table item_pedido (
    id integer not null,
    pedido_id integer,
    produto_id integer,
    quantidade integer,
    primary key (id)
);

alter table cliente
   add constraint UK_jhug2gvm17hj5sqykqqf3ks01 unique (codigo)

alter table item_pedido
   add constraint FK60ym08cfoysa17wrn1swyiuda
   foreign key (pedido_id)
   references pedido (id)

alter table item_pedido
   add constraint FKtk55mn6d6bvl5h0no5uagi3sf
   foreign key (produto_id)
   references produto (id)

alter table pedido
   add constraint FK30s8j2ktpay6of18lbyqn3632
   foreign key (cliente_id)
   references cliente (id)