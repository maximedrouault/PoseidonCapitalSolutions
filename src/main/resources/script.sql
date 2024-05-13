create table bid_list
(
    id             int auto_increment
        primary key,
    account        varchar(30)  null,
    ask            double       null,
    ask_quantity   double       null,
    benchmark      varchar(125) null,
    bid            double       null,
    bid_list_date  datetime(6)  null,
    bid_quantity   double       not null,
    book           varchar(125) null,
    commentary     varchar(125) null,
    creation_date  datetime(6)  null,
    creation_name  varchar(125) null,
    deal_name      varchar(125) null,
    deal_type      varchar(125) null,
    revision_date  datetime(6)  null,
    revision_name  varchar(125) null,
    security       varchar(125) null,
    side           varchar(125) null,
    source_list_id varchar(125) null,
    status         varchar(10)  null,
    trader         varchar(125) null,
    type           varchar(30)  null
);

create table curve_point
(
    id            int auto_increment
        primary key,
    as_of_date    datetime(6) null,
    creation_date datetime(6) null,
    curve_id      int         not null,
    term          double      not null,
    value         double      not null,
    check (`curve_id` <= 2147483647)
);

create table rating
(
    id            int auto_increment
        primary key,
    fitch_rating  varchar(125) null,
    moodys_rating varchar(125) null,
    order_number  int          not null,
    sandprating   varchar(125) null,
    check (`order_number` <= 2147483647)
);

create table rule_name
(
    id          int auto_increment
        primary key,
    description varchar(125) null,
    json        varchar(125) null,
    name        varchar(125) null,
    sql_part    varchar(125) null,
    sql_str     varchar(125) null,
    template    varchar(512) null
);

create table trade
(
    id             int auto_increment
        primary key,
    account        varchar(30)  null,
    benchmark      varchar(125) null,
    book           varchar(125) null,
    buy_price      double       null,
    buy_quantity   double       not null,
    creation_date  datetime(6)  null,
    creation_name  varchar(125) null,
    deal_name      varchar(125) null,
    deal_type      varchar(125) null,
    revision_date  datetime(6)  null,
    revision_name  varchar(125) null,
    security       varchar(125) null,
    sell_price     double       null,
    sell_quantity  double       null,
    side           varchar(125) null,
    source_list_id varchar(125) null,
    status         varchar(125) null,
    trade_date     datetime(6)  null,
    trader         varchar(125) null,
    type           varchar(30)  null
);

create table user
(
    id       int auto_increment
        primary key,
    fullname varchar(125) null,
    password varchar(125) null,
    role     varchar(125) null,
    username varchar(125) null,
    constraint UK_sb8bbouer5wak8vyiiy4pf2bx
        unique (username)
);


INSERT INTO user (id, fullname, username, password, role)
VALUES (1, 'Administrator Test', 'admin', '$2a$12$gWV20kW52IOdUhJLarNLPOl3gZvpedJtDYrREAhX5wZMMAfcRpYiy', 'ADMIN'),
       (2, 'User Test', 'user', '$2a$12$LC0fhIPzTcOZv9157LJZs.qExQprcBk9Pe.e5XJLULfw9QQaQx8NG', 'USER');

INSERT INTO rating (id ,moodys_rating, sandprating, fitch_rating, order_number)
VALUES (1, 'Moodys Rating test', 'Sand PRating test', 'Fitch Rating test', 5);

INSERT INTO  bid_list(id, account, type, bid_quantity)
VALUES (1, 'Account test', 'Type test', 50);

INSERT INTO curve_point(id, curve_id, term, value)
VALUES (1, 10, 50, 30);

INSERT INTO rule_name(id, name, description, json, template, sql_str, sql_part)
VALUES (1,'Name test', 'Description test', 'Json test', 'Template test', 'SQL Str test', 'SQL Part test');

INSERT INTO trade(id, account, type, buy_quantity)
VALUES (1, 'Account test', 'Type test', 30)