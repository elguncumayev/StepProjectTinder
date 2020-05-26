create table relations
(
	"from" integer not null
		constraint from_fk
			references users,
	"to" integer not null
		constraint to_fk
			references users,
	rel boolean not null,
	id serial not null
		constraint relations_pk_2
			primary key,
	constraint relations_pk
		unique ("from", "to")
);

create unique index relations_id_uindex
	on relations (id);