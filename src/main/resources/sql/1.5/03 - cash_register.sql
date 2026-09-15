create table cash_register_session(
	id serial primary key,
	client_id int not null,
	opening_date timestamp not null default now(),
	closing_date timestamp,
	initial_amount float8 not null,
	counted_amount float8,
	status varchar not null default 'OPEN',
	opened_by varchar not null,
	closed_by varchar,
	constraint cash_register_session_client_fk foreign key (client_id) references client(id),
	constraint cash_register_session_status_ck check (status in ('OPEN', 'CLOSED'))
);

create unique index cash_register_session_open_idx on cash_register_session(client_id) where status = 'OPEN';
