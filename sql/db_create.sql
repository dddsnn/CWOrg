CREATE SCHEMA duende;
SET SCHEMA duende;


-- Tables will be dropped, if they exist
drop table  duende.friendship;
drop table  duende.images;
drop table  duende.comments;
drop table  duende.users;


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



