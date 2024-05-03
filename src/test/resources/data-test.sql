INSERT INTO "user" (fullname, username, password, role)
VALUES ('Administrator', 'admin', '$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa', 'ADMIN'),
       ('User', 'user', '$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa', 'USER');

INSERT INTO rating (id ,moodys_rating, sandprating, fitch_rating, order_number)
VALUES (5, 'Moodys Rating', 'Sand PRating', 'Fitch Rating', 5);