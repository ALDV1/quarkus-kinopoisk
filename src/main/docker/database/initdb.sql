CREATE DATABASE IF NOT EXISTS movie_db;

create table if not exists movie_db.movie(
    id int primary key auto_increment,
    name varchar(255) not null,
    rating double not null check ( rating > 0.0 and rating <= 10.0),
    release_year year
);

create table if not exists movie_db.actor(
    id int primary key auto_increment,
    name varchar(255) not null,
    age int check ( age > 0 ),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp on update current_timestamp
);

create table if not exists movie_db.movie_actor(
    movie_id int references movie(id),
    actor_id int references actor(id),
    PRIMARY KEY (movie_id, actor_id)
)