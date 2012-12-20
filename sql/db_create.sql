CREATE SCHEMA cworg;
SET SCHEMA cworg;

-- Tables will be dropped, if they exist
drop table  duende.friendship;--TODO
drop table  duende.images;
drop table  duende.comments;
drop table  duende.users;

-- WoT players (not necesserily users of this application)
CREATE TABLE cworg.players (
	id UNSIGNED BIGINT PRIMARY KEY,
	name VARCHAR(30) NOT NULL UNIQUE,
	lastCW DATETIME DEFAULT NULL
);

-- Clans
CREATE TABLE cworg.clans (
	id UNSIGNED BIGINT PRIMARY KEY,
	name VARCHAR(50) NOT NULL,
	tag VARCHAR(5) NOT NULL
);

-- Mapping players-clans
CREATE TABLE cworg.clanMembership (
	membershipId UNSIGNED INTEGER PRIMARY KEY GENERATED ALWAYS,
	playerId UNSIGNED BIGINT NOT NULL,
	clanId UNSIGNED BIGINT NOT NULL,
	FOREIGN KEY (playerId) REFERENCES cworg.players(id),
	FOREIGN KEY (clanId) REFERENCES cworg.clans(id)
);

-- Users of this application
-- Passwords are hashed with BCrypt
CREATE TABLE cworg.users (
	id UNSIGNED BIGINT PRIMARY KEY,
	passwordBCrypt NOT NULL,
	FOREIGN KEY (id) REFERENCES cworg.players(id)
);

-- List of tanks in the game
CREATE TABLE cworg.tanks (
	id UNSIGNED INTEGER PRIMARY KEY GENERATED ALWAYS,
	name VARCHAR(50) NOT NULL,
	shortName VARCHAR(20) DEFAULT NULL,
	tier SMALLINT NOT NULL,
	nation SMALLINT NOT NULL,
	class SMALLINT NOT NULL,
	freezeTime TIME NOT NULL,
);

-- Who has which tank, and is it frozen
CREATE TABLE cworg.tankOwnership (
	ownershipId UNSIGNED INTEGER PRIMARY KEY GENERATED ALWAYS,
	playerId UNSIGNED BIGINT NOT NULL,
	tankId UNSIGEND INTEGER NOT NULL,
	freezingStart DATETIME,
	freezingStop DATETIME,
	FOREIGN KEY (playerId) REFERENCES cworg.players(id),
	FOREIGN KEY (tankId) REFERENCES cworg.tanks(id)
);

-- User table: One entry represents one physical user.
-- Username is used to log in, thus it has to be unique,
-- the userId is used for foreign keys to link to the user table.
create table duende.users
(
   userId INTEGER primary key generated always as identity,
   username varchar( 30 ) not null UNIQUE,
   password varchar( 30 ) not null,
   firstname varchar( 50 ) default null,
   lastname varchar( 50 ) default null,
   birthdate date default null,
   city varchar( 150 ) default null,
   country varchar( 150 ) default null,
   text varchar( 500 ) default null,
   photofilename varchar( 100 ) default null,
   currentLocation varchar ( 250 ) default null
);


-- Friendship table: A friendship entry denotes the connection 
-- between two users. Setting up a friendship is a process
-- between users A (fromUser) and B (toUser), in which A asks B 
-- to be friends. Until B accepts this request, the friendship 
-- is PENDING and NON-symmetrical. (That also means, that not 
-- all information which is revealed to friends is shown.)
-- Once the request has been accepted by user B, the state is
-- changed to ACCEPTED and both users are in a symmetrical 
-- friendship relation. A second entry in the friendship table
-- is NOT necessary and NOT allowed! (Would lead to having to
-- delete the friendship twice to get rid of it.)
create table duende.friendship
(
   friendshipId INTEGER primary key generated always as identity,
   fromUserId INTEGER CONSTRAINT fromUserId_fk REFERENCES duende.users(userId),
   toUserId INTEGER CONSTRAINT toUserId_fk REFERENCES duende.users(userId),
   state INTEGER
);


-- An entry in the image table represents one photo in the gallery. 
-- Filename can be artificial filename, picked by the application.
-- Make sure that several users can upload images like, e.g. "me.jpg".
create table duende.images
(
   imageId INTEGER primary key generated always as identity,
   userId INTEGER CONSTRAINT galleryImageToUserId_fk REFERENCES duende.users(userId),
   filename varchar( 100 ) not null,
   insertDate timestamp DEFAULT CURRENT TIMESTAMP
);


-- Entries in the comment table represent wall comments one user 
-- has written on someone elses wall. 
create table duende.comments
(
   commentId INTEGER primary key generated always as identity,
   fromUserId INTEGER CONSTRAINT commentFromUserId_fk REFERENCES duende.users(userId),
   toUserId INTEGER CONSTRAINT commentToUserId_fk REFERENCES duende.users(userId),
   insertDate timestamp DEFAULT CURRENT TIMESTAMP,
   text varchar( 500 ) default null
);



