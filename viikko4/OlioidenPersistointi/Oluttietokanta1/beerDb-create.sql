create table beer (
  id                        integer not null,
  name                      varchar(255),
  average                   double,
  brewery_id                integer,
  constraint pk_beer primary key (id))
;

create table brewery (
  id                        integer not null,
  name                      varchar(255),
  constraint pk_brewery primary key (id))
;

create table rating (
  id                        integer not null,
  beer_id                   integer,
  user_id                   integer,
  value                     integer,
  constraint pk_rating primary key (id))
;

create table user (
  id                        integer not null,
  name                      varchar(255),
  constraint pk_user primary key (id))
;

create sequence beer_seq;

create sequence brewery_seq;

create sequence rating_seq;

create sequence user_seq;

alter table beer add constraint fk_beer_brewery_1 foreign key (brewery_id) references brewery (id) on delete restrict on update restrict;
create index ix_beer_brewery_1 on beer (brewery_id);
alter table rating add constraint fk_rating_beer_2 foreign key (beer_id) references beer (id) on delete restrict on update restrict;
create index ix_rating_beer_2 on rating (beer_id);
alter table rating add constraint fk_rating_user_3 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_rating_user_3 on rating (user_id);


