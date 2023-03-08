DROP TABLE IF EXISTS MPA CASCADE;
CREATE TABLE IF NOT EXISTS MPA (
    id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name varchar(100)
);

DROP TABLE IF EXISTS films CASCADE;
CREATE TABLE IF NOT EXISTS films (
    id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name varchar(100),
    description varchar(200),
    release_date DATE,
    duration INTEGER,
    rate INTEGER,
    mpa_id INTEGER,
    FOREIGN KEY (mpa_id) REFERENCES MPA (id)
);

DROP TABLE IF EXISTS genre CASCADE;
CREATE TABLE IF NOT EXISTS genre (
    id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name varchar (200)
);

DROP TABLE IF EXISTS film_genre CASCADE;
CREATE TABLE IF NOT EXISTS film_genre (
    genre_id INTEGER,
    film_id INTEGER,
    FOREIGN KEY (film_id) REFERENCES films (id),
    FOREIGN KEY (genre_id) REFERENCES genre (id)
);

DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE IF NOT EXISTS users (
    id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    email varchar (100),
    login varchar (100),
    name varchar (100),
    birthday DATE
);

DROP TABLE IF EXISTS likes CASCADE;
CREATE TABLE IF NOT EXISTS likes (
    user_id INTEGER,
    film_id INTEGER,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (film_id) REFERENCES films (id)
);

DROP TABLE IF EXISTS  friends CASCADE;
CREATE TABLE IF NOT EXISTS friends (
    user_id INTEGER,
    friend_id INTEGER,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (friend_id) REFERENCES users (id)
)