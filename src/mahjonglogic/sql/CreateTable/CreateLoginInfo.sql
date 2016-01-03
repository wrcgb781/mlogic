CREATE TABLE "LoginInfo" (
	`SiteType`	INT NOT NULL,
	`Attribute`	TEXT NOT NULL,
	`VALUE`	TEXT,
	PRIMARY KEY(SiteType,Attribute)
)