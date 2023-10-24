create table if not exists users (
    create_date timestamp(6),
    last_modified_date timestamp(6),
    user_id bigint auto_increment,
    profile_image_url varchar(255) not null,
    nickname varchar(255) not null,
    platform_id varchar(255) not null,
    refresh_token varchar(255),
    primary key (user_id));

create table if not exists music (
    create_date timestamp(6),
    last_modified_date timestamp(6),
    music_id bigint auto_increment,
    user_id bigint,
    cover_image_url varchar(255) not null,
    genre varchar(255) not null,
    singer varchar(255) not null,
    title varchar(255) not null,
    youtube_url varchar(255) not null,
    primary key (music_id),
    foreign key (user_id) references users (user_id));