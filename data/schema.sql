create database eazybank;

use eazybank;

drop table user;
drop table accounts;

create table user(
	id integer primary key,
	user_id text,
	first_name text,
	last_name text,
	mobile_number text,
	email text,
	role text default 'USER',
	password varchar(255),
	created_at datetime,
	updated_at datetime
);

CREATE UNIQUE INDEX idx_user_user_id
ON user (user_id);
CREATE UNIQUE INDEX idx_user_email 
ON user (email);

create table accounts(
	account_number integer primary key,
	user_id text,
	account_type text,
	branch_address text,
	created_at datetime,
	updated_at datetime,
	foreign key(user_id) references user(user_id)
);

CREATE INDEX idx_accounts_user_id
ON accounts (user_id;

create table account_transactions(
	transaction_id text primary key,
	account_number integer,
	user_id text,
	transaction_date datetime,
	transaction_summary text,
	transaction_type text,
	amount real,
	closing_balance real,
	created_at datetime,
	foreign key(account_number) references accounts(account_number),
	foreign key(user_id) references user(user_id)
);

CREATE UNIQUE INDEX idx_account_transaction_id
ON account_transactions (transaction_id);
CREATE INDEX idx_account_transaction_user_id
ON account_transactions (user_id);

create table loans(
	loan_number integer primary key,
	user_id text,
	start_date datetime,
	loan_type text,
	total_loan real,
	amount_paid real,
	outstanding_amount real,
	created_at datetime,
	updated_at datetime,
	foreign key(user_id) references user(user_id)
);

CREATE INDEX idx_loans_user_id
ON loans (user_id);

create table cards(
	card_id integer primary key,
	user_id text,
	card_number text,
	card_type text,
	total_limit real,
	amount_used real,
	available_amount real,
	created_at datetime,
	updated_at datetime,
	foreign key(user_id) references user(user_id)
);

CREATE INDEX idx_cards_user_id
ON cards (user_id);

create table notice_details(
	id integer primary key,
	notice_summary text,
	notice_details text,
	notice_begin_date datetime,
	notice_end_date datetime,
	created_at datetime,
	updated_at datetime
);

CREATE INDEX idx_notice_begin_date
ON notice_details (notice_begin_date);

create table contact_messages(
  contact_id text primary key,
  contact_name text,
  contact_email text,
  subject text,
  message text,
  created_at datetime
);

CREATE UNIQUE INDEX idx_contact_messages_id
ON contact_messages (contact_id);