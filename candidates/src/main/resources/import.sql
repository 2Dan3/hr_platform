INSERT INTO skill (id, name) VALUES (1, 'Java');

INSERT INTO skill (id, name) VALUES (2, 'Python');

INSERT INTO skill (id, name) VALUES (3, 'Angular');

INSERT INTO skill (id, name) VALUES (4, 'Elasticsearch');

INSERT INTO skill (id, name) VALUES (5, 'Cassandra');

INSERT INTO skill (id, name) VALUES (6, 'T-SQL');

INSERT INTO skill (id, name) VALUES (7, 'Russian');

INSERT INTO skill (id, name) VALUES (8, 'English');

INSERT INTO skill (id, name) VALUES (9, 'Serbian');

INSERT INTO skill (id, name) VALUES (10, 'German');

INSERT INTO skill (id, name) VALUES (11, 'French');

INSERT INTO skill (id, name) VALUES (12, 'Spanish');


INSERT INTO candidate (id, name_full, date_of_birth, contact_number, email) VALUES (1, 'Danilo Ugrin', '2001-12-23', '0640638062', 'dan23.ftn@gmail.com');

INSERT INTO candidate (id, name_full, date_of_birth, contact_number, email) VALUES (2, 'Ivana Ivanovic', '1995-10-28', '061111111', 'test2@gmail.com');

INSERT INTO candidate (id, name_full, date_of_birth, contact_number, email) VALUES (3, 'Marko Markovic', '1999-11-13', '064444444', 'test3@gmail.com');


INSERT INTO candidate_skill (candidate_id, skill_id) VALUES (1, 1);

INSERT INTO candidate_skill (candidate_id, skill_id) VALUES (1, 3);

INSERT INTO candidate_skill (candidate_id, skill_id) VALUES (1, 4);

INSERT INTO candidate_skill (candidate_id, skill_id) VALUES (1, 6);

INSERT INTO candidate_skill (candidate_id, skill_id) VALUES (1, 7);

INSERT INTO candidate_skill (candidate_id, skill_id) VALUES (1, 8);

INSERT INTO candidate_skill (candidate_id, skill_id) VALUES (1, 9);

INSERT INTO candidate_skill (candidate_id, skill_id) VALUES (1, 10);

INSERT INTO candidate_skill (candidate_id, skill_id) VALUES (1, 11);

INSERT INTO candidate_skill (candidate_id, skill_id) VALUES (2, 2);

INSERT INTO candidate_skill (candidate_id, skill_id) VALUES (2, 5);

INSERT INTO candidate_skill (candidate_id, skill_id) VALUES (2, 6);

INSERT INTO candidate_skill (candidate_id, skill_id) VALUES (3, 12);

INSERT INTO candidate_skill (candidate_id, skill_id) VALUES (3, 3);
