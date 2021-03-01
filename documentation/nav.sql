CREATE TYPE "user_status" AS ENUM (
  'ACTIVE',
  'PENDING',
  'DELETED'
);

CREATE TYPE "article_status" AS ENUM (
  'ACTIVE',
  'DELETED'
);

CREATE TYPE "role" AS ENUM (
  'READER',
  'AUTHOR'
);

CREATE TYPE "action" AS ENUM (
  'CREATE',
  'UPDATE',
  'DELETE'
);

CREATE TABLE "user" (
  "id" SERIAL PRIMARY KEY,
  "name" varchar[256] NOT NULL,
  "lastname" varchar[256] NOT NULL,
  "email" varchar[256] NOT NULL,
  "password" varchar[256] NOT NULL,
  "status" user_status NOT NULL,
  "role" role NOT NULL
);

CREATE TABLE "article" (
  "id" SERIAL PRIMARY KEY,
  "author" long NOT NULL,
  "title" varchar[256] NOT NULL,
  "url" varchar[256] NOT NULL,
  "status" article_status NOT NULL
);

CREATE TABLE "user_liked_articles" (
  "id" SERIAL PRIMARY KEY,
  "user_id" long NOT NULL,
  "article_id" long NOT NULL
);

CREATE TABLE "group" (
  "id" SERIAL PRIMARY KEY,
  "name" varchar[256],
  "user_id" long NOT NULL,
  "article_id" long NOT NULL
);

CREATE TABLE "user_audit" (
  "id" SERIAL PRIMARY KEY,
  "user_id" long NOT NULL,
  "action" action NOT NULL,
  "date" date
);

CREATE TABLE "article_audit" (
  "id" SERIAL PRIMARY KEY,
  "user_id" long NOT NULL,
  "article_id" long NOT NULL,
  "date" date
);

ALTER TABLE "article" ADD FOREIGN KEY ("author") REFERENCES "user" ("id");

ALTER TABLE "user_liked_articles" ADD FOREIGN KEY ("user_id") REFERENCES "user" ("id");

ALTER TABLE "user_liked_articles" ADD FOREIGN KEY ("article_id") REFERENCES "article" ("id");

ALTER TABLE "group" ADD FOREIGN KEY ("user_id") REFERENCES "user" ("id");

ALTER TABLE "group" ADD FOREIGN KEY ("article_id") REFERENCES "article" ("id");

ALTER TABLE "user_audit" ADD FOREIGN KEY ("user_id") REFERENCES "user" ("id");

ALTER TABLE "article_audit" ADD FOREIGN KEY ("user_id") REFERENCES "user" ("id");

ALTER TABLE "article_audit" ADD FOREIGN KEY ("article_id") REFERENCES "article" ("id");
