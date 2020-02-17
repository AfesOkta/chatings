--name:save-message!
-- creates a new message
INSERT INTO chatings
(name, message, timestamp)
VALUES (:name, :message, :timestamp)

--name:get-messages
-- selects all available messages
SELECT * from chatings