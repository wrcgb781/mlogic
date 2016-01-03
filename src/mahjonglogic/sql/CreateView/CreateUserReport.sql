CREATE VIEW UserReport AS
select t1.username AS Name,count(*) as CNT, sum(t1.point -35000) as Total
,sum(t1.point -35000)/count(*) as AVG
from playerpoint t1
group by username
order by total