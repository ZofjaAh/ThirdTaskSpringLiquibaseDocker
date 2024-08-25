CREATE TABLE locality (
    locality_id         SERIAL          NOT NULL,
    locality_name       VARCHAR(64)     NOT NULL,
    population          INT             NOT NULL,
    metro_availability  BOOLEAN         NOT NULL,
    PRIMARY KEY (locality_id),
    UNIQUE (locality_name)
);