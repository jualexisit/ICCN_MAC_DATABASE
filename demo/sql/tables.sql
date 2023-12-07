SHOW mac_db;

CREATE SCHEMA IF NOT EXISTS mac_db;

CREATE TABLE mac_db.macs
(
    mac_id BIGINT UNSIGNED NOT NULL PRIMARY KEY,
    vendor VARCHAR(32) NOT NULL,
    time_removed TIMESTAMP NOT NULL
);

CREATE TABLE mac_db.mac_ptr
(
    mac_name VARCHAR(32) NOT NULL,
    mac_id BIGINT UNSIGNED NOT NULL PRIMARY KEY
);

INSERT INTO mac_db.macs (mac_id, vendor, time_removed)
VALUES (0xf8f7d3000001, "ICCN_Justin", TIMESTAMP("2017-07-23"))

INSERT INTO mac_db.mac_ptr (mac_name, mac_id)
VALUES ('current_mac' , 0xf8f7d3000005)

SELECT mac_id FROM mac_ptr
WHERE mac_name = 'current_mac';

SHOW SCHEMA mac_db;