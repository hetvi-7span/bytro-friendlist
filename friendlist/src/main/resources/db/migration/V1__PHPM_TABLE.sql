-- public.friend_request definition

-- Drop table

-- DROP TABLE public.friend_request;

CREATE TABLE friend_request (
    id serial4 NOT NULL,
    created_at timestamp(6) NULL,
    created_by varchar(255) NULL,
    updated_at timestamp(6) NULL,
    updated_by varchar(255) NULL,
    message varchar(255) NULL,
    receiver_id int4 NOT NULL,
    sender_id int4 NOT NULL,
    status varchar(255) NULL,
    CONSTRAINT friend_request_pkey PRIMARY KEY (id)
);
-- public.friends definition

-- Drop table

-- DROP TABLE public.friends;

CREATE TABLE friends (
    friend_id int4 NOT NULL,
    user_id int4 NOT NULL,
    created_at timestamp(6) NULL,
    created_by varchar(255) NULL,
    updated_at timestamp(6) NULL,
    updated_by varchar(255) NULL,
    blocked_by int4 NOT NULL,
    is_blocked bool NOT NULL,
    CONSTRAINT friends_pkey PRIMARY KEY (friend_id, user_id)
);