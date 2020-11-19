public class Day {
    private int month;
    private int day;
    private int high;
    private int low;
    private String weather;
    public Day(int m, int d, int h, int l, String w) {
        month=m;
        day=d;
        high=h;
        low=l;
        weather=w;
    }
    
    public void setHigh(int h) {
        high=h;
    }
    
    public String getWeather() {
        return weather;
    }
    
    public void setLow(int l) {
        low=l;
    }
    
    public int getHigh() {
        return high;
    }
    
    public int getLow() {
        return low;
    }
    
    public int getMonth() {
        return month;
    }
    
    public int getDay() {
        return day;
    }
}