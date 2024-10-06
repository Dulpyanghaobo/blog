create table tarot_reading_categories
(
    tarot_reading_id bigint not null,
    category_id      bigint not null,
    primary key (tarot_reading_id, category_id),
    constraint fk_tarot_reading_categories_category
        foreign key (category_id) references tarot_categories (id),
    constraint fk_tarot_reading_categories_reading
        foreign key (tarot_reading_id) references tarot_readings (id)
);

