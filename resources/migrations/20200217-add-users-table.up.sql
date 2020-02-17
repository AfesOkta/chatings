-- START:guestbook-table
CREATE TABLE public.chatings
(id serial PRIMARY KEY ,
  name VARCHAR(30),
  message VARCHAR(200),
  timestamp TIMESTAMP);
-- END:guestbook-table