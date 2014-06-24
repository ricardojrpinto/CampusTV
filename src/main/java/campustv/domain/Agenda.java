package campustv.domain;

public class Agenda {

	
	private Content content;
	private String scheduleDate;
	private String estimatedTime;
	private String timeTransmission;
	
	public Agenda() {
		super();
	}

	public Agenda(Content content, String scheduleDate, String estimatedTime,
			String timeTransmission) {
		this.content = content;
		this.scheduleDate = scheduleDate;
		this.estimatedTime = estimatedTime;
		this.timeTransmission = timeTransmission;
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public String getScheduleDate() {
		return scheduleDate;
	}

	public void setScheduleDate(String scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	public String getEstimatedTime() {
		return estimatedTime;
	}

	public void setEstimatedTime(String estimatedTime) {
		this.estimatedTime = estimatedTime;
	}

	public String getTimeTransmission() {
		return timeTransmission;
	}

	public void setTimeTransmission(String timeTransmission) {
		this.timeTransmission = timeTransmission;
	}
	
}
