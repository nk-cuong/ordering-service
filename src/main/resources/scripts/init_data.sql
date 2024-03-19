INSERT INTO `role` (id, role_name, description) VALUES (1, 'CUSTOMER', 'CUSTOMER');
INSERT INTO `role` (id, role_name, description) VALUES (2, 'ADMIN', 'ADMIN');

INSERT INTO `user` (id, username, password) VALUES (1, 'TOM', '$2a$12$edmf68o.PMxdeZYBFHgh3.Z/wOGmwMw4SnhR6B89gxAcOO5I0l8pG'); #password: TOM
INSERT INTO `user_role` (user_id, role_id) VALUE (1, 1);
INSERT INTO `user_role` (user_id, role_id) VALUE (1, 2);

INSERT INTO `user` (id, username, password) VALUES (2, 'JERRY', '$2a$12$C90ezeBSItBcoSojh7vc9.slgfWguLZ.bjDweme3o8nv/gMO3vbcS'); #password: JERRY
INSERT INTO `user_role` (user_id, role_id) VALUE (2, 1);

INSERT INTO `product` (id, service_name, price) VALUES (1, 'PHOTO_EDITING', 1000);
INSERT INTO `product` (id, service_name, price) VALUES (2, 'VIDEO_EDITING', 2000);