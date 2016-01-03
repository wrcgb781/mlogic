CREATE TABLE 'Statement' (
	'StatementCode' TEXT NOT NULL,
	'Date' TEXT NOT NULL,
	'StatementKubun' INT NOT NULL,
	'HandId' TEXT NOT NULL,
	'WalletdId' TEXT NOT NULL,
	'Money' REAL NOT NULL,
	'TotalMoney' REAL NOT NULL
,PRIMARY KEY(StatementCode)
)
