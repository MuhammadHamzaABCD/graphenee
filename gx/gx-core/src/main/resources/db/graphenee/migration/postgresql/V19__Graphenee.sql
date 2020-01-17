create sequence gx_product_type_seq;
create sequence gx_product_seq;
create sequence gx_billing_seq;

create table gx_product_type (
	oid integer not null default nextval('gx_product_type_seq'::regclass), 
    type_name varchar(50) not null,
    type_code varchar(20),
	primary key (oid)
);

create table gx_product (
	oid integer not null default nextval('gx_product_seq'::regclass),
	product_code varchar(50) not null,
	product_name varchar(200) not null,
	description varchar(500),
	retail_price double precision not null default 0,
	price double precision not null default 0,
	oid_product_type integer,
	bar_code varchar(200),
    foreign key (oid_product_type) references gx_product_type(oid) on delete restrict on update cascade,
	primary key (oid)
);




create table gx_billing (
	oid integer not null default nextval('gx_billing_seq'::regclass), 
    bill_number varchar(50) not null,
    bill_date timestamp not null,
    discount double precision not null default 0,
    total_bill double precision not null default 0,
    tax double precision not null default 0,
    total_payable double precision not null default 0,
    total_paid double precision not null default 0,
	primary key (oid)
);

create table gx_billing_product_join(
oid_product integer not null,
oid_billing integer not null,
foreign key (oid_billing) references gx_billing(oid) on delete restrict on update cascade,
foreign key (oid_product) references gx_product(oid) on delete restrict on update cascade
);


