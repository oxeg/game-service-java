CREATE TABLE IF NOT EXISTS user_friend (
    user_from UUID NOT NULL REFERENCES user_list(id),
    user_to   UUID NOT NULL,
    UNIQUE (user_from, user_to)
);

CREATE INDEX IF NOT EXISTS user_friend_from_idx ON user_friend(user_from);
