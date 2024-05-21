INSERT INTO cliente (id, codigo, nome) VALUES (1, 1, 'Julio Cesar');
INSERT INTO cliente (id, codigo, nome) VALUES (2, 2, 'Luciano');
INSERT INTO cliente (id, codigo, nome) VALUES (3, 3, 'Raquel');
INSERT INTO cliente (id, codigo, nome) VALUES (4, 4, 'Silvia');
INSERT INTO cliente (id, codigo, nome) VALUES (5, 5, 'Maria Jose');
INSERT INTO cliente (id, codigo, nome) VALUES (6, 6, 'Ricardo');
INSERT INTO cliente (id, codigo, nome) VALUES (7, 7, 'Angela');
INSERT INTO cliente (id, codigo, nome) VALUES (8, 8, 'Selma');
INSERT INTO cliente (id, codigo, nome) VALUES (9, 9, 'Fernanda');
INSERT INTO cliente (id, codigo, nome) VALUES (10, 10, 'Maria Clara');

INSERT INTO produto (id, preco_unitario, descricao) VALUES (1, 10.0, 'mouse');
INSERT INTO produto (id, preco_unitario, descricao) VALUES (2, 15.0, 'teclado');
INSERT INTO produto (id, preco_unitario, descricao) VALUES (3, 700.0, 'monitor');
INSERT INTO produto (id, preco_unitario, descricao) VALUES (4, 120.0, 'fonte');
INSERT INTO produto (id, preco_unitario, descricao) VALUES (5, 100.0, 'webcam');
INSERT INTO produto (id, preco_unitario, descricao) VALUES (6, 500.0, 'cadeira');
INSERT INTO produto (id, preco_unitario, descricao) VALUES (7, 1500.0, 'celular');
INSERT INTO produto (id, preco_unitario, descricao) VALUES (8, 150.0, 'HD 120gb');
INSERT INTO produto (id, preco_unitario, descricao) VALUES (9, 40.0, 'camisa estampada');
INSERT INTO produto (id, preco_unitario, descricao) VALUES (10, 35.0, 'bermuda de praia');
INSERT INTO produto (id, preco_unitario, descricao) VALUES (11, 90.0, 'óculos de sol');
INSERT INTO produto (id, preco_unitario, descricao) VALUES (12, 170.0, 'tenis nike');
INSERT INTO produto (id, preco_unitario, descricao) VALUES (13, 60.0, 'caneca');
INSERT INTO produto (id, preco_unitario, descricao) VALUES (14, 80.0, 'estabilizador');
INSERT INTO produto (id, preco_unitario, descricao) VALUES (15, 190.0, 'raquete de tenis');
INSERT INTO produto (id, preco_unitario, descricao) VALUES (16, 1900.0, 'iphone');
INSERT INTO produto (id, preco_unitario, descricao) VALUES (17, 3000.0, 'televisão 50');
INSERT INTO produto (id, preco_unitario, descricao) VALUES (18, 7000.0, 'piano');
INSERT INTO produto (id, preco_unitario, descricao) VALUES (19, 890.0, 'violão');
INSERT INTO produto (id, preco_unitario, descricao) VALUES (20, 140.0, 'pandeiro');

INSERT INTO pedido (id, cliente_id, data_pedido, total) VALUES (1, 1, '2024-05-21', 52.25);

INSERT INTO item_pedido (id, pedido_id, produto_id, quantidade) VALUES (1, 1, 1, 4);
INSERT INTO item_pedido (id, pedido_id, produto_id, quantidade) VALUES (2, 1, 2, 1 );



