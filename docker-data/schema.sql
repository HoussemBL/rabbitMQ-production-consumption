create database wiki;

USE wiki;

CREATE TABLE IF NOT EXISTS stats (
    id INT AUTO_INCREMENT PRIMARY KEY,
    timestamp_val VARCHAR(255),
    num_edits BIGINT ,
    num_edits_german BIGINT
) ;
