-- Taken from https://raw.githubusercontent.com/spring-projects/spring-integration/main/spring-integration-jdbc/src/main/resources/org/springframework/integration/jdbc/schema-postgresql.sql

CREATE TABLE _spring_integration_MESSAGE
(
    MESSAGE_ID    CHAR(36)     NOT NULL,
    REGION        VARCHAR(100) NOT NULL,
    CREATED_DATE  TIMESTAMP    NOT NULL,
    MESSAGE_BYTES BYTEA,
    CONSTRAINT _spring_integration_MESSAGE_PK PRIMARY KEY (MESSAGE_ID, REGION)
);

CREATE INDEX _spring_integration_MESSAGE_IX1 ON _spring_integration_MESSAGE (CREATED_DATE);

CREATE TABLE _spring_integration_GROUP_TO_MESSAGE
(
    GROUP_KEY  CHAR(36) NOT NULL,
    MESSAGE_ID CHAR(36) NOT NULL,
    REGION     VARCHAR(100),
    CONSTRAINT _spring_integration_GROUP_TO_MESSAGE_PK PRIMARY KEY (GROUP_KEY, MESSAGE_ID, REGION)
);

CREATE TABLE _spring_integration_MESSAGE_GROUP
(
    GROUP_KEY              CHAR(36)     NOT NULL,
    REGION                 VARCHAR(100) NOT NULL,
    GROUP_CONDITION        VARCHAR(255),
    COMPLETE               BIGINT,
    LAST_RELEASED_SEQUENCE BIGINT,
    CREATED_DATE           TIMESTAMP    NOT NULL,
    UPDATED_DATE           TIMESTAMP DEFAULT NULL,
    CONSTRAINT _spring_integration_MESSAGE_GROUP_PK PRIMARY KEY (GROUP_KEY, REGION)
);

CREATE TABLE _spring_integration_LOCK
(
    LOCK_KEY     CHAR(36)     NOT NULL,
    REGION       VARCHAR(100) NOT NULL,
    CLIENT_ID    CHAR(36),
    CREATED_DATE TIMESTAMP    NOT NULL,
    CONSTRAINT _spring_integration_LOCK_PK PRIMARY KEY (LOCK_KEY, REGION)
);

CREATE SEQUENCE _spring_integration_MESSAGE_SEQ START WITH 1 INCREMENT BY 1 NO CYCLE;

CREATE TABLE _spring_integration_CHANNEL_MESSAGE
(
    MESSAGE_ID           CHAR(36)     NOT NULL,
    GROUP_KEY            CHAR(36)     NOT NULL,
    CREATED_DATE         BIGINT       NOT NULL,
    MESSAGE_PRIORITY     BIGINT,
    MESSAGE_SEQUENCE     BIGINT       NOT NULL DEFAULT NEXTVAL('_spring_integration_MESSAGE_SEQ'),
    MESSAGE_BYTES        JSON,
    REGION               VARCHAR(100) NOT NULL,
    CONSTRAINT _spring_integration_CHANNEL_MESSAGE_PK PRIMARY KEY (REGION, GROUP_KEY, CREATED_DATE, MESSAGE_SEQUENCE)
);

CREATE INDEX _spring_integration_CHANNEL_MSG_DELETE_IDX ON _spring_integration_CHANNEL_MESSAGE (REGION, GROUP_KEY, MESSAGE_ID);
-- This is only needed if the message group store property 'priorityEnabled' is true
-- CREATE UNIQUE INDEX _spring_integration_CHANNEL_MSG_PRIORITY_IDX ON _spring_integration_CHANNEL_MESSAGE (REGION, GROUP_KEY, MESSAGE_PRIORITY DESC NULLS LAST, CREATED_DATE, MESSAGE_SEQUENCE);


CREATE TABLE _spring_integration_METADATA_STORE
(
    METADATA_KEY   VARCHAR(255) NOT NULL,
    METADATA_VALUE VARCHAR(4000),
    REGION         VARCHAR(100) NOT NULL,
    CONSTRAINT _spring_integration_METADATA_STORE_PK PRIMARY KEY (METADATA_KEY, REGION)
);

-- This is only needed if using PostgresChannelMessageSubscriber

/*CREATE FUNCTION _spring_integration_CHANNEL_MESSAGE_NOTIFY_FCT()
RETURNS TRIGGER AS
 $BODY$
 BEGIN
     PERFORM pg_notify('_spring_integration_channel_message_notify', NEW.REGION || ' ' || NEW.GROUP_KEY);
     RETURN NEW;
 END;
 $BODY$
 LANGUAGE PLPGSQL;

 CREATE TRIGGER _spring_integration_CHANNEL_MESSAGE_NOTIFY_TRG
 AFTER INSERT ON _spring_integration_CHANNEL_MESSAGE
 FOR EACH ROW
 EXECUTE PROCEDURE _spring_integration_CHANNEL_MESSAGE_NOTIFY_FCT();*/