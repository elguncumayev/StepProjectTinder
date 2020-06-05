create table users
(
	id serial not null
		constraint users_pk
			primary key,
	username varchar not null,
	"e-mail" varchar not null,
	"workInfo" varchar,
	"lastLogin" varchar not null,
	"fullName" varchar not null,
	pass varchar not null,
	prof_photo varchar

);

create unique index users_id_uindex
	on users (id);

create unique index "users_e-mail_uindex"
	on users ("e-mail");

create unique index users_username_uindex
	on users (username);