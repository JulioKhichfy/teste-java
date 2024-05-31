
    create table cliente (
        codigo integer,
        id integer not null auto_increment,
        primary key (id)
    )

    create table item_pedido (
        id integer not null auto_increment,
        pedido_id integer,
        preco_total decimal(20,2),
        preco_unitario decimal(20,2),
        quantidade integer,
        nome varchar(255),
        primary key (id)
    )

    create table pedido (
        cliente_id integer,
        data_pedido date,
        id integer not null auto_increment,
        numero_controle integer,
        total decimal(20,2),
        primary key (id)
    )

    alter table cliente
       add constraint UK_jhug2gvm17hj5sqykqqf3ks01 unique (codigo)

    alter table item_pedido
       add constraint FK60ym08cfoysa17wrn1swyiuda
       foreign key (pedido_id)
       references pedido (id)

    alter table pedido
       add constraint FK30s8j2ktpay6of18lbyqn3632
       foreign key (cliente_id)
       references cliente (id)