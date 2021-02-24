DROP TYPE IF EXISTS "user_status" CASCADE;
DROP TYPE IF EXISTS "article_status" CASCADE;
DROP TYPE IF EXISTS "role" CASCADE;
DROP TYPE IF EXISTS "action" CASCADE;

DROP TABLE IF EXISTS "user" CASCADE;
DROP TABLE IF EXISTS "article" CASCADE;
DROP TABLE IF EXISTS "user_liked_articles" CASCADE;
DROP TABLE IF EXISTS "group" CASCADE;
DROP TABLE IF EXISTS "user_audit" CASCADE;
DROP TABLE IF EXISTS "article_audit" CASCADE;

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
  "author"  bigint NOT NULL,
  "title" varchar[256] NOT NULL,
  "url" varchar[256] NOT NULL,
  "status" article_status NOT NULL
);

CREATE TABLE "user_liked_articles" (
  "id" SERIAL PRIMARY KEY,
  "user_id" bigint NOT NULL,
  "article_id" bigint NOT NULL
);

CREATE TABLE "group" (
  "id" SERIAL PRIMARY KEY,
  "name" varchar[256] NOT NULL,
  "user_id" bigint NOT NULL,
  "article_id" bigint NOT NULL
);

CREATE TABLE "user_audit" (
  "id" SERIAL PRIMARY KEY,
  "user_id" bigint NOT NULL,
  "action" action NOT NULL,
  "timestamp" timestamp
);

CREATE TABLE "article_audit" (
  "id" SERIAL PRIMARY KEY,
  "user_id" bigint NOT NULL,
  "article_id" bigint NOT NULL,
  "timestamp" timestamp
);

ALTER TABLE "user_liked_articles" ADD FOREIGN KEY ("user_id") REFERENCES "user" ("id");

ALTER TABLE "user_liked_articles" ADD FOREIGN KEY ("article_id") REFERENCES "article" ("id");

ALTER TABLE "group" ADD FOREIGN KEY ("user_id") REFERENCES "user" ("id");

ALTER TABLE "group" ADD FOREIGN KEY ("article_id") REFERENCES "article" ("id");

ALTER TABLE "user_audit" ADD FOREIGN KEY ("user_id") REFERENCES "user" ("id");

ALTER TABLE "article_audit" ADD FOREIGN KEY ("user_id") REFERENCES "user" ("id");

ALTER TABLE "article_audit" ADD FOREIGN KEY ("article_id") REFERENCES "article" ("id");

ALTER TABLE "article" ADD FOREIGN KEY ("author") REFERENCES "user" ("id");
