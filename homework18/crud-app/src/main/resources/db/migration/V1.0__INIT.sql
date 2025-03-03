CREATE TABLE topics (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    title TEXT NOT NULL,
    text TEXT NOT NULL,
    author TEXT NOT NULL,
    CONSTRAINT pk_topics PRIMARY KEY (id)
);

CREATE TABLE replies (
   id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   text TEXT NOT NULL,
   author TEXT NOT NULL,
   topic_id BIGINT NOT NULL,
   CONSTRAINT pk_replies PRIMARY KEY (id)
);

ALTER TABLE replies ADD CONSTRAINT FK_REPLIES_ON_TOPIC FOREIGN KEY (topic_id) REFERENCES topics (id);