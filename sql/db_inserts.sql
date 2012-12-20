-- ------------------------------------------------------
-- --                                                  --
-- --          Use on empty database only!             --
-- --                                                  --
-- ------------------------------------------------------
SET SCHEMA duende;

insert into duende.users (username, password, firstname, lastname, birthdate, city, country, text, photofilename, currentLocation) values ('bernd','bernd','Bernd','Hauck', '1980-01-01', 'Hamburg', 'GERMANY', 'lalala!', 'hauck.jpg', 'Germany, Hamburg, Schwarzenbergstrasse 95');
insert into duende.users (username, password, firstname, lastname, birthdate, city, country, text, photofilename, currentLocation) values ('helge','helge','Helge','Klimek', '1980-01-01', 'Hamburg', 'GERMANY', 'lalala!', 'klimek.jpg', 'Germany, Hamburg, Schwarzenbergstrasse 93');
insert into duende.users (username, password, firstname, lastname, birthdate, city, country, text, photofilename, currentLocation) values ('volker','volker','Volker','Turau', '1980-01-01', 'Hamburg', 'GERMANY', 'lalala!', 'turau.jpg', 'Germany, Hamburg, Schwarzenbergstrasse 91');
insert into duende.users (username, password, firstname, lastname, birthdate, city, country, text, photofilename, currentLocation) values ('marcus','marcus','Marcus','Venzke', '1980-01-01', 'Hamburg', 'GERMANY', 'lalala!', 'venzke.jpg', 'Germany, Hamburg, Schwarzenbergstrasse 89');
insert into duende.users (username, password, firstname, lastname, birthdate, city, country, text, photofilename, currentLocation) values ('arne','arne','Arne','Bosien', '1980-01-01', 'Hamburg', 'GERMANY', 'lalala!', 'bosien.jpg', 'Germany, Hamburg, Schwarzenbergstrasse 87');
insert into duende.users (username, password, firstname, lastname, birthdate, city, country, text, photofilename, currentLocation) values ('gisela','gisela','Gisela','Winterstein', '1980-01-01', 'Hamburg', 'GERMANY', 'lalala!', 'winterstein.jpg', 'Germany, Hamburg, Schwarzenbergstrasse 85');


insert into duende.friendship (fromUserId, toUserId, state) values ((select max(userId) from duende.users where username = 'bernd'), (select max(userId) from duende.users where username = 'helge'), 1);
insert into duende.friendship (fromUserId, toUserId, state) values ((select max(userId) from duende.users where username = 'bernd'), (select max(userId) from duende.users where username = 'volker'), 1);
insert into duende.friendship (fromUserId, toUserId, state) values ((select max(userId) from duende.users where username = 'bernd'), (select max(userId) from duende.users where username = 'marcus'), 1);
insert into duende.friendship (fromUserId, toUserId, state) values ((select max(userId) from duende.users where username = 'bernd'), (select max(userId) from duende.users where username = 'arne'), 1);
insert into duende.friendship (fromUserId, toUserId, state) values ((select max(userId) from duende.users where username = 'bernd'), (select max(userId) from duende.users where username = 'gisela'), 1);
insert into duende.friendship (fromUserId, toUserId, state) values ((select max(userId) from duende.users where username = 'helge'), (select max(userId) from duende.users where username = 'arne'), 1);
insert into duende.friendship (fromUserId, toUserId, state) values ((select max(userId) from duende.users where username = 'volker'), (select max(userId) from duende.users where username = 'gisela'), 1);


insert into duende.comments (fromUserId, toUserId, text) values ((select max(userId) from duende.users where username = 'bernd'), (select max(userId) from duende.users where username = 'helge'), 'First!');
insert into duende.comments (fromUserId, toUserId, text) values ((select max(userId) from duende.users where username = 'bernd'), (select max(userId) from duende.users where username = 'helge'), 'Spam! Spam! Spam! Spam! Lovely Spam!');
insert into duende.comments (fromUserId, toUserId, text) values ((select max(userId) from duende.users where username = 'bernd'), (select max(userId) from duende.users where username = 'volker'), 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus in fermentum elit. Nam nisl nibh, tincidunt ut ornare at, aliquet sagittis tortor. Vivamus commodo, lorem nec facilisis pharetra, diam risus iaculis enim, eu luctus augue elit quis nisi. Duis quis bibendum elit. Suspendisse a mattis erat. Nulla vitae mauris ac mi gravida lacinia.!');
insert into duende.comments (fromUserId, toUserId, text) values ((select max(userId) from duende.users where username = 'helge'), (select max(userId) from duende.users where username = 'volker'), 'Yet another comment!');
insert into duende.comments (fromUserId, toUserId, text) values ((select max(userId) from duende.users where username = 'helge'), (select max(userId) from duende.users where username = 'bernd'), 'O Hai');
insert into duende.comments (fromUserId, toUserId, text) values ((select max(userId) from duende.users where username = 'bernd'), (select max(userId) from duende.users where username = 'volker'), 'Jambo, habari?');


insert into duende.images(userId, filename) values ((select max(userId) from duende.users where username = 'bernd'),'hauck.jpg');
insert into duende.images(userId, filename) values ((select max(userId) from duende.users where username = 'helge'),'klimek.jpg');
insert into duende.images(userId, filename) values ((select max(userId) from duende.users where username = 'volker'),'turau.jpg');
insert into duende.images(userId, filename) values ((select max(userId) from duende.users where username = 'marcus'),'venzke.jpg');
insert into duende.images(userId, filename) values ((select max(userId) from duende.users where username = 'arne'),'bosien.jpg');
insert into duende.images(userId, filename) values ((select max(userId) from duende.users where username = 'gisela'),'winterstein.jpg');

