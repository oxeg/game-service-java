CREATE INDEX IF NOT EXISTS user_id_hash_idx ON user_list USING hash(id);
