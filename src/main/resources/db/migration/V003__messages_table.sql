create table messages
(
	id serial not null
		constraint messages_pk
			primary key,
	text varchar not null,
	"from" integer not null
		constraint from_fk
			references users,
	"to" integer not null
		constraint to_fk
			references users,
	time varchar not null
);

alter table messages owner to postgres;

create unique index messages_id_uindex
	on messages (id);