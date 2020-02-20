-- START:guestbook-table
CREATE TABLE public.channels
(id serial PRIMARY KEY ,
  name VARCHAR(30),
  list UUID[]);
-- END:guestbook-table
