CREATE TABLE friend_request (
      id int(11) NOT NULL AUTO_INCREMENT,
      created_at timestamp(6) NULL,
      created_by varchar(255) NULL,
      updated_at timestamp(6) NULL,
      updated_by varchar(255) NULL,
      message varchar(255) NULL,
      receiver_id int(11) NOT NULL,
      sender_id int(11) NOT NULL,
      status varchar(20) NULL,
      CONSTRAINT friend_request_pkey PRIMARY KEY (id)
  );

  CREATE TABLE friends (
      friend_id int(11) NOT NULL,
      user_id int(11) NOT NULL,
      created_at timestamp(6) NULL,
      created_by varchar(255) NULL,
      updated_at timestamp(6) NULL,
      updated_by varchar(255) NULL,
      blocked_by int(11) NULL,
      is_blocked bool NOT NULL,
      CONSTRAINT friends_pkey PRIMARY KEY (friend_id, user_id)
  );