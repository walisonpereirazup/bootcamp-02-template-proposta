create table cards (
    id uuid not null,
    card_number varchar(255),
    cardholder_name varchar(255),
    proposal_id uuid,
    primary key (id)
);

alter table cards
    add constraint FKb4u6pj0t9gfnx6i0je7p14840
    foreign key (proposal_id)
    references proposals;
