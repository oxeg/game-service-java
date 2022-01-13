CREATE TABLE IF NOT EXISTS player (
    id   UUID NOT NULL,
    name VARCHAR(64),
    CONSTRAINT player_id_pk PRIMARY KEY (id)
);

