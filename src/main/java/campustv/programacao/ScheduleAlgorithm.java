package campustv.programacao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import campustv.dbutils.SQLUtils;
import campustv.domain.Event;
import campustv.domain.Image;
import campustv.domain.New;
import campustv.domain.Video;
import campustv.repository.DBRepositoryUtils;
import campustv.repository.exceptions.RepositoryException;

public class ScheduleAlgorithm {
	
	private static final long emissionTV = 60 * 60 * 12; // 12 HORAS
	
	private static final long HORAS_INIT = 8;
	private static final long MINUTOS_INIT = 00;
	private static final long SEGUNDOS_INIT = 00;
	
	private static final int MIN_CONTENTS = 150;
	private static final long MIN_VIDEO = 60;
	private static final long TRANSICAO_A = 1;
	private static final long TRANSICAO_IMAGE = 1;
	private static final long ANIMATION_IMAGE = 10;
	private static final long MAX_IMAGES = 10;
	private static final long  MIN_NEW_OR_EVENT = 30;
	
	private Iterator<Event> itEvent;
	private Iterator<Video> itVideo;
	private Iterator<New> itNew;
	private List<Event> events = new LinkedList<Event>();
	private List<Video> videos = new LinkedList<Video>();
	private List<New> news = new LinkedList<New>();
	private List<Event> eventsToB = new LinkedList<Event>();
	private List<New> newsToB = new LinkedList<New>();
	private Timestamp clockTime;
	public ScheduleAlgorithm(Date day){
		events = new LinkedList<Event>();
		videos = new LinkedList<Video>();
		news = new LinkedList<New>();
		eventsToB = new LinkedList<Event>();
		newsToB = new LinkedList<New>();
		clockTime = new Timestamp(new Date().getTime());
		this.calculateSchedule(emissionTV, clockTime);
	}
	
	public void calculateSchedule(long time, Timestamp day){
		boolean orderBy = false;
		try {
			if(DBRepositoryUtils.countContentsBetweenDay(day) < MIN_CONTENTS){
				orderBy = true;
			}
			if(orderBy){
				itNew = DBRepositoryUtils.getNewsOrdeyByInsertion();
				itVideo = DBRepositoryUtils.getVideosOrderByInsertion();
			}
			else{
				itNew = DBRepositoryUtils.getNewsBetweenDay(day);
				itVideo = DBRepositoryUtils.getVideosBetweenDay(day);
			}
			itEvent = DBRepositoryUtils.getEventsBetweenDay(day);
			startSchedule(time);
			Iterator<Event> auxEvent = events.iterator();
			Iterator<New> auxNew = news.iterator();
			Iterator<Video> auxVideo = videos.iterator();
			DBRepositoryUtils.insertProgramacao(day);
			clockTime.setHours(8);
			clockTime.setMinutes(00);
			clockTime.setSeconds(00);
			while(auxEvent.hasNext() || auxNew.hasNext() || auxVideo.hasNext())){
				if(auxEvent.hasNext()){
					Event e = auxEvent.next();
					long timeEvent = e.getContentTime();
					if(timeEvent >= 60){
						clockTime
					}
					DBRepositoryUtils.insertInAgenda(e.getContentID(), day, clockTime);
					
				}
			}
			
			
	
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
		
	}

	@SuppressWarnings("deprecation")
	private void startSchedule(long time) {
		long categoryTime = time/3;
		long remainTime = 0;
		long videoTime = categoryTime;
		while(itVideo.hasNext() && videoTime > MIN_VIDEO){
			Video v = itVideo.next();
			Timestamp initialTime = SQLUtils.stringToTimestamp(v.getCheckPoint_I());
			Timestamp finalTime = SQLUtils.stringToTimestamp(v.getCheckPoint_F());
			long init = initialTime.getMinutes() * 60 + initialTime.getSeconds(); 
			long fim = finalTime.getMinutes() * 60 + initialTime.getSeconds();
			long contentTime = (fim-init) + TRANSICAO_A;
			videoTime -= contentTime + TRANSICAO_A;
			v.setContentTime(contentTime);
			videos.add(v);
		}
		remainTime += videoTime;

		long eventTime = categoryTime;
		while(itEvent.hasNext() && eventTime > MIN_NEW_OR_EVENT){
			Event e = itEvent.next();
			if(e.getVideos().hasNext()){
				Video v = e.getVideos().next();
				Timestamp initialTime = SQLUtils.stringToTimestamp(v.getCheckPoint_I());
				Timestamp finalTime = SQLUtils.stringToTimestamp(v.getCheckPoint_F());
				long init = initialTime.getMinutes() * 60 + initialTime.getSeconds(); 
				long fim = finalTime.getMinutes() * 60 + initialTime.getSeconds();
				long contentTime = (fim-init) + TRANSICAO_A;
				eventTime -= (fim - init) + TRANSICAO_A;
				e.setContentTime(contentTime);
				events.add(e);
			}else if(e.getImages().hasNext()){
				Iterator<Image> itImages = e.getImages();
				int count = 0;
				long contentTime = 0;
				while(itImages.hasNext() && count < MAX_IMAGES){
					itImages.next();
					eventTime -= ANIMATION_IMAGE + TRANSICAO_IMAGE;
					contentTime += ANIMATION_IMAGE + TRANSICAO_IMAGE;
					count++;
				}
				eventTime -= TRANSICAO_A;
				contentTime += TRANSICAO_A;
				e.setContentTime(contentTime);
				events.add(e);
			}else{
				eventsToB.add(e);
			}
		}
		
		remainTime += eventTime;
		
		long newTime = categoryTime;
		while(itNew.hasNext() && newTime > MIN_NEW_OR_EVENT){
			New n = itNew.next();
			if(n.getVideos().hasNext()){
				Video v = n.getVideos().next();
				Timestamp initialTime = SQLUtils.stringToTimestamp(v.getCheckPoint_I());
				Timestamp finalTime = SQLUtils.stringToTimestamp(v.getCheckPoint_F());
				long init = initialTime.getMinutes() * 60 + initialTime.getSeconds(); 
				long fim = finalTime.getMinutes() * 60 + initialTime.getSeconds();
				newTime -= (fim - init) + TRANSICAO_A;
				n.setContentTime(fim - init + TRANSICAO_A);
				news.add(n);
			}else if(n.getImages().hasNext()){
				Iterator<Image> itImages = n.getImages();
				int count = 0;
				long contentTime = 0;
				while(itImages.hasNext() && count < MAX_IMAGES){
					itImages.next();
					newTime -= ANIMATION_IMAGE + TRANSICAO_IMAGE;
					contentTime += ANIMATION_IMAGE + TRANSICAO_IMAGE;
					count++;
				}
				newTime -= TRANSICAO_A;
				contentTime += TRANSICAO_A;
				n.setContentTime(contentTime);
				news.add(n);
			}else{
				newsToB.add(n);
			}
		}
		
		remainTime += newTime;
		startSchedule(remainTime);
		
	}

}
