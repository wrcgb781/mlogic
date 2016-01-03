CREATE VIEW SCORE AS
select t1.HandId,t1.WalletdID,t1.Date,t1.money||'0' as Rate,
case 
	When t2.money is null 
		then (t1.money * -1 )|| '0'
		else round(t2.money - t1.money,2) 
end AS score
from (select HandId,WalletdID,Date,money
from statement
Where StatementKubun = 1
group by HandID,WalletdID
) t1 left outer join (
select HandId,WalletdID,money from Statement Where StatementKubun = 2
) t2 on t1.HandId = t2.HandId 
order by date desc