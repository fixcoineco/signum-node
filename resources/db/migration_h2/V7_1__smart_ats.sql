CREATE TABLE IF NOT EXISTS at_map (db_id IDENTITY, 
    at_id BIGINT NOT NULL, key1 BIGINT NOT NULL, key2 BIGINT, value BIGINT,
    height INT NOT NULL, latest BOOLEAN NOT NULL DEFAULT TRUE);
