CREATE TABLE "PlayerPoint" (
	`HandID`	TEXT NOT NULL,
	`WalletdId`	TEXT NOT NULL,
	`UserName`	TEXT NOT NULL,
	`Point`	TEXT NOT NULL,
	`RN`	INTEGER NOT NULL,
	PRIMARY KEY(HandID,WalletdId,UserName)
)