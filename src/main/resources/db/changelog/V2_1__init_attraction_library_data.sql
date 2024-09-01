insert into LOCALITY (locality_id, locality_name, population, metro_availability)
values (1, 'Warsaw', 1764615, true),
       (2, 'Krakow', 757685, false),
       (3, 'Wroclaw', 634192, false),
       (4, 'Poznan', 540365, false),
       (5, 'Gdansk', 470907, true);

SELECT SETVAL('locality_locality_id_seq', 5);
insert into SERVICE (service_id, service_name, description)
values (1, 'tour guide', 'walking tour with guide'),
       (2, 'car rental', 'rent a car for your trip'),
       (3, 'catering', 'food and beverage services'),
       (4, 'bike rental', 'rent a bike for city tours'),
       (5, 'boat tour', 'guided boat tours on the river');

SELECT SETVAL('service_service_id_seq', 5);

insert into ATTRACTION (attraction_id, attraction_name, creation_date, description, type, locality_id)
values (1, 'Old Town', '1339-01-01', 'Warsaw central Old Town neighborhood', 'PALACES', 1),
       (2, 'Wawel Castle', '1364-01-01', 'Historic castle in Krakow', 'PALACES', 2),
       (3, 'Wroclaw Zoo', '1865-07-10', 'One of the oldest zoos in Poland', 'PARKS', 3),
       (4, 'Poznan Cathedral', '968-01-01', 'Oldest cathedral in Poland', 'MUSEUMS', 4),
       (5, 'Gdansk Shipyard', '1945-01-01', 'Historic shipyard in Gdansk', 'ARCHAEOLOGICAL_SITES', 5);

SELECT SETVAL('attraction_attraction_id_seq', 5);

insert into ATTRACTION_SERVICE (attraction_id, service_id)
values (1, 1),
       (1, 2),
       (2, 1),
       (2, 3),
       (3, 4),
       (4, 1),
       (5, 5);

