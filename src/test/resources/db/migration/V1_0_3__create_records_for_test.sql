INSERT INTO `skill`
(id, name, parent_id)
VALUES
  (1, 'Java', NULL),
  (2, 'Spring', 1),
  (3, 'JPA', 1),
  (4, 'JSF', 1),
  (5, 'Camel', 1);

INSERT INTO `user_skill`
  (skill_id, user_id, level, note)
VALUES
  (3, 2, 10, 'Beginner'),
  (4, 2, 50, 'Experienced');