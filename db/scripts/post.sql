create table if not exists post (
    id serial primary key,
    title text,
    description text,
    link text unique,
    created timestamp
);