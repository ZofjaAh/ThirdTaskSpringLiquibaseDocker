CREATE TABLE locality (
    locality_id         SERIAL          NOT NULL,
    locality_name       VARCHAR(64)     NOT NULL,
    population          INT ,
    metro_availability  BOOLEAN,
    PRIMARY KEY (locality_id),
    UNIQUE (locality_name)
);