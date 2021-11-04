CREATE TABLE if not exists company 
(
    id integer NOT NULL,
    name character varying,
    CONSTRAINT company_pkey PRIMARY KEY (id)
);

CREATE TABLE if not exists person
(
    id integer NOT NULL,
    name character varying,
    company_id integer references company(id),
    CONSTRAINT person_pkey PRIMARY KEY (id)
);

select p.name, c.name
from person as p
join company as c
on p.company_id = c.id
where c.id != 5

select c.name as name, count(*) as maxcount
from person as p
join company as c
on p.company_id = c.id
group by p.company_id, c.name
order by maxcount desc
limit 1

insert into company (id, name) 
values 
(1, 'bmv'),
(2, 'mersedes'),
(3, 'toyota'),
(4, 'suzuki'),
(5, 'honda'),
(6, 'fiat');

insert into person (id, name, company_id)
values
(1, 'serg', 1),
(11, 'serg2', 2),
(12, 'serg3', 3),
(13, 'serg4', 4),
(14, 'serg5', 5),
(15, 'serg6', 6),
(2, 'petr', 1),
(21, 'petr2', 2),
(22, 'petr3', 3),
(23, 'petr4', 4),
(24, 'petr5', 5),
(3, 'lana', 1),
(31, 'lana2', 2),
(32, 'lana3', 3),
(33, 'lana4', 4),
(4, 'rail', 1),
(41, 'rail2', 2),
(42, 'rail3', 3),
(43, 'rail4', 4),
(5, 'stas', 1),
(51, 'stas2', 2),
(52, 'stas3', 3),
(6, 'gleb', 1),
(7, 'denis', 2),
(8, 'victor', 2);