create view RateReport as
select rate,count(*) as CNT ,sum(score) as TOTAL,round(sum(score)/count(*),2) as AVG
from score
group by rate
order by cast (rate as real)
