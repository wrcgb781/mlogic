CREATE VIEW DayReport AS
select t1.Date as Date,w.WeekNameJP AS Week,round(sum(t1.Score),2) AS Score
from (
select substr(Date,1,10) as Date ,score
from score
) t1 ,WeekDay w
Where strftime('%w',t1.date) = w.WeekID
group by t1.Date
order by Date desc