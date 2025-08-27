CREATE TABLE files_info (
    file_id BIGSERIAL PRIMARY KEY,
    file_name VARCHAR(100) NOT NULL,
    file_owner_id VARCHAR NOT NULL,
    file_creation_date VARCHAR(20) NOT NULL,
    file_last_modified_date VARCHAR(20) NOT NULL,
    file_access_level VARCHAR(10) NOT NULL,
    file_tags VARCHAR
)