CREATE TABLE "HandInfo" (
	`HandId`	TEXT NOT NULL,
	`WalletdId`	TEXT NOT NULL UNIQUE,
	`RuleSet`	TEXT,
	`PrevailingWind`	TEXT,
	PRIMARY KEY(HandId)
)