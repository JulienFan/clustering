package pack;

public class Point {
	int id;
	double lon;
	double lat;
	double pir;
	double cir;
	int serv;
	boolean isTreated;
	
	public Point(int id, double lon, double lat, double pir, double cir, int serv, boolean isTreated) {
		this.id = id;
		this.lon = lon;
		this.lat = lat;
		this.pir = pir;
		this.cir = cir;
		this.serv = serv;
		this.isTreated = isTreated;
	}

	public Point(int id, double lon, double lat, double pir, double cir, int serv) {
		this.id = id;
		this.lon = lon;
		this.lat = lat;
		this.pir = pir;
		this.cir = cir;
		this.serv = serv;
	}
	public Point() {
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getPir() {
		return pir;
	}
	public void setPir(double pir) {
		this.pir = pir;
	}
	public double getCir() {
		return cir;
	}
	public void setCir(double cir) {
		this.cir = cir;
	}
	public int getServ() {
		return serv;
	}
	public void setServ(int serv) {
		this.serv = serv;
	}
	public String toString() {
		return id+","+lon+","+lat+","+pir+","+cir+","+serv;
	}

	public boolean getIsTreated(){return isTreated;}
	public void setIsTreated(boolean isTreated){ this.isTreated = isTreated;}
}
