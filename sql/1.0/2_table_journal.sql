CREATE TABLE journal (
    id bigserial,
    user_id bigint,
    action_id bigint,
    record_time bigint,
    access_from_host text,

    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE RESTRICT,
    FOREIGN KEY (action_id) REFERENCES actions (id) ON DELETE RESTRICT
);