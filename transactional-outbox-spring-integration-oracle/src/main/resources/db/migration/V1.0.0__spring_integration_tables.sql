-- https://raw.githubusercontent.com/spring-projects/spring-integration/main/spring-integration-jdbc/src/main/resources/org/springframework/integration/jdbc/schema-oracle.sql

CREATE TABLE INT_MESSAGE  (
                              MESSAGE_ID CHAR(36) NOT NULL,
                              REGION VARCHAR2(100) NOT NULL,
                              CREATED_DATE TIMESTAMP NOT NULL,
                              MESSAGE_BYTES BLOB,
                              constraint INT_MESSAGE_PK primary key (MESSAGE_ID, REGION)
);

CREATE INDEX INT_MESSAGE_IX1 ON INT_MESSAGE (CREATED_DATE);

CREATE TABLE INT_GROUP_TO_MESSAGE  (
                                       GROUP_KEY VARCHAR2(36) NOT NULL,
                                       MESSAGE_ID CHAR(36) NOT NULL,
                                       REGION VARCHAR2(100),
                                       constraint INT_GROUP_TO_MESSAGE_PK primary key (GROUP_KEY, MESSAGE_ID, REGION)
);

CREATE TABLE INT_MESSAGE_GROUP  (
                                    GROUP_KEY VARCHAR2(36) NOT NULL,
                                    REGION VARCHAR2(100) NOT NULL,
                                    GROUP_CONDITION VARCHAR(255),
                                    COMPLETE NUMBER(19,0),
                                    LAST_RELEASED_SEQUENCE NUMBER(19,0),
                                    CREATED_DATE TIMESTAMP NOT NULL,
                                    UPDATED_DATE TIMESTAMP DEFAULT NULL,
                                    constraint INT_MESSAGE_GROUP_PK primary key (GROUP_KEY, REGION)
);

CREATE TABLE INT_LOCK  (
                           LOCK_KEY VARCHAR2(36) NOT NULL,
                           REGION VARCHAR2(100) NOT NULL,
                           CLIENT_ID VARCHAR2(36),
                           CREATED_DATE TIMESTAMP NOT NULL,
                           constraint INT_LOCK_PK primary key (LOCK_KEY, REGION)
);

CREATE SEQUENCE INT_MESSAGE_SEQ START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE TABLE INT_CHANNEL_MESSAGE (
                                     MESSAGE_ID CHAR(36) NOT NULL,
                                     GROUP_KEY VARCHAR2(36) NOT NULL,
                                     CREATED_DATE NUMBER(19,0) NOT NULL,
                                     MESSAGE_PRIORITY NUMBER(19,0),
                                     MESSAGE_SEQUENCE NUMBER(19,0) NOT NULL ,
                                     MESSAGE_BYTES BLOB,
                                     REGION VARCHAR2(100) NOT NULL,
                                     constraint INT_CHANNEL_MESSAGE_PK primary key (REGION, GROUP_KEY, CREATED_DATE, MESSAGE_SEQUENCE)
);

CREATE INDEX INT_CHANNEL_MSG_DELETE_IDX ON INT_CHANNEL_MESSAGE (REGION, GROUP_KEY, MESSAGE_ID);
-- This is only needed if the message group store property 'priorityEnabled' is true
-- CREATE UNIQUE INDEX INT_CHANNEL_MSG_PRIORITY_IDX ON INT_CHANNEL_MESSAGE (REGION, GROUP_KEY, MESSAGE_PRIORITY DESC, CREATED_DATE, MESSAGE_SEQUENCE);

CREATE TABLE INT_METADATA_STORE  (
                                     METADATA_KEY VARCHAR2(255) NOT NULL,
                                     METADATA_VALUE VARCHAR2(4000),
                                     REGION VARCHAR2(100) NOT NULL,
                                     constraint INT_METADATA_STORE_PK primary key (METADATA_KEY, REGION)
);