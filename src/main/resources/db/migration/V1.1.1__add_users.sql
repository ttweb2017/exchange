insert into users(id, activation_code, active, avatar, phone, email, password, username, first_name, last_name, last_visit) values
  (1, '', 1, 'profile.svg', '+99361616161', 'admin@hazaryorite.com', '$2a$08$NmpoVMf9JPe7hjmecgPUl.JYrrV5fskHpsP0PiWFo9C5KKjQdPNFi', 'admin', 'Admin', 'Karaoke', '2019-09-28 12:06:53'),
  (2, '', 0, 'profile.svg', '+99361616161', 'moderator@hazaryorite.com', '$2a$08$3kNg2IE5XGSjMY7xGCyiN.5TP0O3WwldgDUv7jUpsZUmZwIzSHozG', 'moderator', 'Moderator', 'Karaoke', '2019-09-28 12:06:53'),
  (3, '', 0, 'profile.svg', '+99361616161', 'user@hazaryorite.com', '$2a$08$MHJpU1MSV/U/zJl7Ww3zY.sD7wivlpLL2tfV6OOx2CMrWJF05DvVG', 'user', 'User', 'Karaoke', '2019-09-28 12:06:53');

insert into user_role(user_id, roles) values
  (1, 'USER'),
  (1, 'MODERATOR'),
  (1, 'ADMIN'),
  (2, 'USER'),
  (2, 'MODERATOR'),
  (3, 'USER');

update hibernate_sequence set next_val = 4;