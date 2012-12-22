-- Sets up the database for CWOrg. Drops possibly existing database
DROP SCHEMA IF EXISTS cworg;
CREATE SCHEMA cworg;
USE cworg;

-- Tables will be dropped, if they exist
DROP TABLE IF EXISTS cworg.players;
DROP TABLE IF EXISTS cworg.clans;
DROP TABLE IF EXISTS cworg.clanMembership;
DROP TABLE IF EXISTS cworg.users;
DROP TABLE IF EXISTS cworg.tanks;
DROP TABLE IF EXISTS cworg.tankOwnership;
DROP TABLE IF EXISTS cworg.nation;
DROP TABLE IF EXISTS cworg.tankClass;
-- TODO

-- WoT players (not necesserily users of this application)
CREATE TABLE cworg.players (
	id BIGINT PRIMARY KEY,
	name VARCHAR(30) NOT NULL UNIQUE,
	lastCW DATETIME DEFAULT NULL
);

-- Clans
CREATE TABLE cworg.clans (
	id BIGINT PRIMARY KEY,
	name VARCHAR(50) NOT NULL,
	tag VARCHAR(5) NOT NULL
);

-- Mapping players-clans
CREATE TABLE cworg.clanMembership (
	membershipId INT PRIMARY KEY AUTO_INCREMENT,
	playerId BIGINT NOT NULL,
	clanId BIGINT NOT NULL,
	FOREIGN KEY (playerId) REFERENCES cworg.players(id),
	FOREIGN KEY (clanId) REFERENCES cworg.clans(id)
);

-- Users of this application
-- Passwords are hashed with BCrypt
CREATE TABLE cworg.users (
	id BIGINT PRIMARY KEY,
	passwordBCrypt CHAR(60) NOT NULL,
	FOREIGN KEY (id) REFERENCES cworg.players(id)
);

-- Nations in the game, instead of an enum
CREATE TABLE cworg.nation (
	id INT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(30) NOT NULL
);

-- Tank classes in the game
CREATE TABLE cworg.tankClass (
	id INT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(30) NOT NULL
);

-- List of tanks in the game
CREATE TABLE cworg.tanks (
	id INT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL,
	shortName VARCHAR(20) DEFAULT NULL,
	tier SMALLINT UNSIGNED NOT NULL,
	nationId INT NOT NULL,
	classId INT NOT NULL,
	freezeTime TIME NOT NULL,
	FOREIGN KEY (nationId) REFERENCES cworg.nation(id),
	FOREIGN KEY (classId) REFERENCES cworg.tankClass(id)
);

-- Who has which tank, and is it frozen
CREATE TABLE cworg.tankOwnership (
	ownershipId INT PRIMARY KEY AUTO_INCREMENT,
	playerId BIGINT NOT NULL,
	tankId INT NOT NULL,
	freezingStart DATETIME,
	freezingStop DATETIME,
	FOREIGN KEY (playerId) REFERENCES cworg.players(id),
	FOREIGN KEY (tankId) REFERENCES cworg.tanks(id)
);
