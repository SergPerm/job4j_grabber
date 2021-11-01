create table if not exists post (
    id serial primary key,
    title text,
    description text,
    link character varying (300) not null unique,
    created timestamp
);