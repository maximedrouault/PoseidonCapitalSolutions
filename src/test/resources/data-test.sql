INSERT INTO "user" (fullname, username, password, role)
VALUES ('Administrator Test', 'admintest', '$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa', 'ADMIN'),
       ('User Test', 'usertest', '$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa', 'USER');

INSERT INTO rating (id ,moodys_rating, sandprating, fitch_rating, order_number)
VALUES (5, 'Moodys Rating test', 'Sand PRating test', 'Fitch Rating test', 5);

INSERT INTO  bid_list(id, account, type, bid_quantity)
VALUES (5, 'Account test', 'Type test', 50);

INSERT INTO curve_point(id, curve_id, term, "value")
VALUES (5, 10, 50, 30);

INSERT INTO rule_name(id, name, description, json, template, sql_str, sql_part)
VALUES (5,'Name test', 'Description test', 'Json test', 'Template test', 'SQL Str test', 'SQL Part test');

INSERT INTO trade(id, account, type, buy_quantity)
VALUES (5, 'Account test', 'Type test', 30)