package mk.ukim.finki.jmm.tobuylist;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ToBuyItem {
	
	String task;
	Date created;
	
	public String getTask(){
		return task;
	}
	
	public Date getCreated(){
		return created;
	}
	
	public ToBuyItem(String _task){
		this(_task, new Date(java.lang.System.currentTimeMillis()));
	}

	public ToBuyItem(String __task, Date _created) {
		task = __task;
		created = _created;
	}
	
	@Override
	public String toString(){
		SimpleDateFormat sdf =  new SimpleDateFormat("dd/MM/yy");
		String dateString = sdf.format(created);
		return "(" + dateString + ")" + task;
	}
}
