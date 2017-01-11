

import java.sql.Timestamp;

import org.joda.time.LocalDateTime;



public class JodaToMilllis {

	private static long localMillis;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LocalDateTime time =LocalDateTime.now().plusMinutes(5);
		localMillis = time.toDateTime().getMillis();
		
		Timestamp t= new Timestamp(localMillis);
		
		System.out.println(localMillis);
		System.out.println(System.currentTimeMillis());
		
	}

}
