CREATE TABLE statistic (
    id bigserial,
    user_id bigint,
    login_count bigint,

    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
