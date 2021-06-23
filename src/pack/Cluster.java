package pack;

import java.util.ArrayList;
import java.util.List;

public class Cluster {
	int id;
	int pointnum;
	double lon;
	double lat;
	double debit;
	public List<Point> points = new ArrayList<Point>();


	public Cluster() {}

	public Cluster(int id, double lon, double lat, double debit,int pointnum) {
		this.id = id;
		this.lon = lon;
		this.lat = lat;
		this.debit = debit;
		//this.points = points;
		this.pointnum = pointnum;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public int getPointnum() {
		return pointnum;
	}
	public void setPointnum(int pointnum) {
		this.pointnum = pointnum;
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

	public double getDebit() {
		return debit;
	}
	public void setDebit(double debit) {
		this.debit = debit;
	}

	public List<Point> getPoints() {
		return points;
	}
	public void setPoints(List<Point> points) {
		this.points = points;
	}
	public String toString() {
		return id+","+lon+","+lat+","+debit+","+pointnum;
	}

	public void changeCenter(){

		double lattemp=0.0, longtemp=0.0;

		for(Point p : this.getPoints()){
			lattemp += p.getLat();
			longtemp += p.getLon();
		}
		this.lat = lattemp / this.getPointnum();
		this.lon = longtemp / this.getPointnum();

	}

	//Check if we can change the cluster's center after adding this new point into
	public boolean valideCenter(Point point){

		double lattemp=0.0, longtemp=0.0;

		for(Point p : this.getPoints()){
			lattemp += p.getLat();
			longtemp += p.getLon();
		}
		lattemp = (lattemp + point.getLat()) / (this.getPointnum()+1);
		longtemp = (longtemp + point.getLon()) / (this.getPointnum()+1);
		boolean flag = false;
		for(Point p : this.getPoints()){
			flag = true;
			if(greedy_approach.getDistance(longtemp,lattemp,p.getLon(),p.getLat())>45000.0 || greedy_approach.getDistance(longtemp,lattemp,point.getLon(),point.getLat())>45000.0){
				flag=false;
				break;
			}
		}
		if(flag) return true;
		else return false;
	}


	//Check if after merging 2 clusters into a new one, the capacity of this new cluster is still under limit
	public boolean valideDebit(Cluster c1){

		if((this.getDebit()+c1.getDebit())<4096.0){
			return true;
		}else return false;
	}

	public boolean valideDebit(Point p){

		if((this.getDebit()+p.getPir())<4096.0){
			return true;
		}else return false;
	}

	//Merge two clusters into a bigger one
	public void clusterMerge(Cluster c1){
		double c1_lattemp=0.0,  c1_longtemp=0.0;
		double c2_lattemp=0.0,  c2_longtemp=0.0;
		double lattemp=0.0,  longtemp=0.0;

		for(Point p : c1.getPoints()){
			c1_lattemp += p.getLat();
			c1_longtemp += p.getLon();
		}
		for(Point p : this.getPoints()){
			c2_lattemp += p.getLat();
			c2_longtemp += p.getLon();
		}

		lattemp = (c1_lattemp + c2_lattemp)/(this.getPointnum()+c1.getPointnum());
		longtemp = (c1_longtemp + c2_longtemp)/(this.getPointnum()+c1.getPointnum());

		this.setLon(longtemp);
		this.setLat(lattemp);
		this.setPointnum(this.getPointnum()+c1.getPointnum());
		this.setDebit(this.getDebit()+c1.getDebit());

		for(Point p : c1.getPoints()){
			this.getPoints().add(p);
		}
	}
}
