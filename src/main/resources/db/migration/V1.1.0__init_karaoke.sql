create table hibernate_sequence (next_val bigint) engine=MyISAM;

insert into hibernate_sequence values ( 1 );

create table singer (
  id bigint not null,
  active bit not null,
  avatar varchar(255),
  first_name varchar(255),
  last_name varchar(255),
primary key (id)) engine=MyISAM;

create table user_role (user_id bigint not null, roles varchar(255)) engine=MyISAM;

create table users (
  id bigint not null,
  activation_code varchar(255),
  active bit not null,
  avatar varchar(255),
  email varchar(255),
  first_name varchar(255),
  last_name varchar(255),
  last_visit datetime,
  password varchar(255),
  phone varchar(255),
  username varchar(255),
primary key (id)) engine=MyISAM;

create table video (
  id bigint not null,
  active bit not null,
  added_date_time datetime,
  image varchar(255),
  name varchar(255),
  video varchar(255),
  watched_counter integer not null,
  singer_id bigint,
primary key (id)) engine=MyISAM;

create table category (
  id bigint not null,
  active bit not null,
  name varchar(255),
primary key (id)) engine=MyISAM;

create table song (
  id bigint not null,
  active bit not null,
  added_date_time datetime,
  image varchar(255),
  name varchar(255),
  video varchar(255),
  watched_counter integer not null,
  category_id bigint,
primary key (id)) engine=MyISAM;

alter table user_role add constraint FK_user_role_user foreign key (user_id) references users (id);

alter table video add constraint FK_video_singer foreign key (singer_id) references singer (id);

alter table song add constraint FK_song_category foreign key (category_id) references category (id);