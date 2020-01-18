create sequence gx_bill_item_seq;

create table gx_bill_item(
	oid integer not null default nextval('gx_bill_item_seq'::regclass),
	oid_bill integer not null,
	discount double precision default 0,
	quantity integer not default 0,
	foreign key (oid_product_type) references gx_product_type(oid) on delete restrict on update cascade,
	primary key (oid)
);




