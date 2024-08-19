CREATE TABLE attraction_service (
    attraction_id   INT     NOT NULL,
    service_id      INT     NOT NULL,
    PRIMARY KEY (attraction_id, service_id),
    CONSTRAINT fk_attraction_service_attraction
        FOREIGN KEY (attraction_id)
            REFERENCES attraction(attraction_id),
    CONSTRAINT fk_attraction_service_service
        FOREIGN KEY (service_id)
            REFERENCES service (service_id)
);