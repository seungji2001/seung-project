CREATE TABLE url_mapping (
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    short_code  VARCHAR(10)  NOT NULL,
    original_url VARCHAR(2048) NOT NULL,
    click_count BIGINT       NOT NULL DEFAULT 0,
    created_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uq_short_code (short_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;