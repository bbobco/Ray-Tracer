import static java.lang.System.*;
class timer {
	static double start;
	private long secondsPassed;
	private long lastcalled;
	public timer() 
	{
		start=System.currentTimeMillis();
	}
	public void setTime()
	{
		lastcalled=System.currentTimeMillis();
	}
	public long getTimePassed()
	{
		secondsPassed=System.currentTimeMillis()-lastcalled;
	//	secondsPassed/=1000;
		return secondsPassed;
	}
	public double getTotalTimePassed()
	{
		double t = (double)System.currentTimeMillis()-(double)start;
		t/=1000;
		return t;
	}
}
