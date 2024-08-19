CREATE TABLE attraction (
    attraction_id       SERIAL              NOT NULL,
    attraction_name     VARCHAR(64)         NOT NULL,
    creation_date       DATE                NOT NULL,
    description         VARCHAR(128)        NOT NULL,
    type                attraction_type     NOT NULL ,
    locality_id         INT                 NOT NULL,
    PRIMARY KEY (attraction_id),
    UNIQUE (attraction_name, locality_id),
    CONSTRAINT fk_attraction_locality
        FOREIGN KEY (locality_id)
            REFERENCES locality(locality_id)
);