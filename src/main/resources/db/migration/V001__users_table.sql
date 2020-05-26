create table users
(
	id serial not null
		constraint users_pk
			primary key,
	"e-mail" varchar not null,
	"workInfo" varchar,
	"lastLogin" varchar not null,
	"fullName" varchar not null,
	pass varchar not null,
	prof_photo varchar
);

alter table relations owner to postgres;

create unique index users_id_uindex
	on users (id);