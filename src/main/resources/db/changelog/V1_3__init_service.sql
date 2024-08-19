CREATE TABLE service (
    service_id      SERIAL       NOT NULL,
    service_name    VARCHAR(32)  NOT NULL,
    description     VARCHAR(128) NOT NULL,
    PRIMARY KEY (service_id),
    UNIQUE (service_name)
);
