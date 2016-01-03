CREATE VIEW HourReport AS
select t1.Date AS Hour,round(sum(t1.Score),2) AS Score
from (
select substr(Date,12,2) as Date ,score
from score
) t1 
group by Date
order by t1.Date