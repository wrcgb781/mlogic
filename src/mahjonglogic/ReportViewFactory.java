package mahjonglogic;

public class ReportViewFactory {

	ReportViewFactory(){

	}
	public AbstractReportView getInstance(int type) {
		AbstractReportView rv;

		switch(type){
		case 0:
			rv =new DayReportView();
			break;
		case 1:
			rv =new WeekdayReportView();
			break;
		case 2:
			rv =new HourReportView();
			break;
		case 3:
			rv =new UserReportView();
			break;

		default:
			rv = null;
			break;
		}


		return (rv);
	}


}
