create table proposals (
    id uuid not null,
    document varchar(255),
    email varchar(255),
    name varchar(255),
    address varchar(255),
    salary numeric(19, 2),
    card_number varchar(255),
    status varchar(255),
    primary key (id)
);

alter table proposals
    add constraint uk_proposal_document unique (document);
