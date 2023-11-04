-- Exemple de données pour la table "Serie"
INSERT INTO Serie (nom, lien_image, vo, premise) VALUES('Breaking Bad', 'breaking_bad.jpg', 'anglais', 'Un professeur de chimie devient un baron de la drogue.'),('Game of Thrones', 'game_of_thrones.jpg', 'anglais', 'Une lutte pour le trône de fer dans un monde fantastique.'),('Stranger Things', 'stranger_things.jpg', 'anglais', 'Des événements étranges se produisent dans une petite ville.'),('Friends', 'friends.jpg', 'anglais', 'Les aventures de six amis à New York.'),('The Crown', 'the_crown.jpg', 'VO', 'La vie de la reine d Angleterre.');

INSERT INTO Genre (nom ) VALUES('Drame'),('Crime'),('Fantaisie'),('Science-Fiction'),('Horreur'),('Comédie'),('Biographie'),('Histoire');

INSERT INTO genre_series (genre_id, series_id) VALUES (1, 1),(2, 1),(3, 2), (1, 2), (4, 3), (5, 3), (6, 4), (1, 4), (7, 5), (8, 5);

INSERT INTO Saison (num, nom, annee, nombre_episode, serie_id) VALUES (1, 'Saison 1', 2008, 7, 1),(2, 'Saison 2', 2009, 13, 1),(1, 'Saison 1', 2011, 10, 2),(2, 'Saison 2', 2012, 10, 2),(1, 'Saison 1', 2016, 8, 3),(2, 'Saison 2', 2017, 9, 3),(1, 'Saison 1', 1994, 24, 4),(2, 'Saison 2', 1995, 24, 4),(1, 'Saison 1', 2016, 10, 5),(2, 'Saison 2', 2017, 10, 5);