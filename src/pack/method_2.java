
//run through the pointlist many times，every time "fullfill" a cluster
package pack;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.csvreader.CsvReader;
import org.gavaghan.geodesy.*;

public class method_2 {
    //Radius of a cluster
    static double RADIUS = 45000.0;
    //Capacity of a cluster
    static double CAPACITY = 4096.0;
    static int countP = 1;//count number of Point
    static int countC = 1;//count number of Cluster
    static int countG = 9;//count number of Group
    static boolean flag = false;

    public static void main(String[] args)throws Exception{

        //Path of the file "generated.csv"
        String filePath = "/Users/fanqiyue/Downloads/啊 semestre 2/project ingénierie reseaux/generated.csv";
        File inFile = new File(filePath);
        //Output clustering result to a txt file
        String outputPath = "/Users/fanqiyue/Downloads/ClusteringResult.txt";

        //initialize the pointlists and put them into pointset
        List<Point> pointList_1 = new ArrayList<Point>();
        List<Point> pointList_2 = new ArrayList<Point>();
        List<Point> pointList_3 = new ArrayList<Point>();
        List<Point> pointList_4 = new ArrayList<Point>();
        List<Point> pointList_5 = new ArrayList<Point>();
        List<Point> pointList_6 = new ArrayList<Point>();
        List<Point> pointList_7 = new ArrayList<Point>();
        List<Point> pointList_8 = new ArrayList<Point>();
        List<Point> pointList_9 = new ArrayList<Point>();
        Map<Integer,List<Point>> pointSet = new HashMap<Integer,List<Point>>();
        pointSet.put(countG--, pointList_9);
        pointSet.put(countG--, pointList_8);
        pointSet.put(countG--, pointList_7);
        pointSet.put(countG--, pointList_6);
        pointSet.put(countG--, pointList_5);
        pointSet.put(countG--, pointList_4);
        pointSet.put(countG--, pointList_3);
        pointSet.put(countG--, pointList_2);
        pointSet.put(countG--, pointList_1);

        //initialize the clusterlists and put them into clusterset
        List<Cluster> clusterList_1 = new ArrayList<Cluster>();
        List<Cluster> clusterList_2 = new ArrayList<Cluster>();
        List<Cluster> clusterList_3 = new ArrayList<Cluster>();
        List<Cluster> clusterList_4 = new ArrayList<Cluster>();
        List<Cluster> clusterList_5 = new ArrayList<Cluster>();
        List<Cluster> clusterList_6 = new ArrayList<Cluster>();
        List<Cluster> clusterList_7 = new ArrayList<Cluster>();
        List<Cluster> clusterList_8 = new ArrayList<Cluster>();
        List<Cluster> clusterList_9 = new ArrayList<Cluster>();
        Map<Integer,List<Cluster>> clusterSet = new HashMap<Integer,List<Cluster>>();
        clusterSet.put(++countG, clusterList_1);
        clusterSet.put(++countG, clusterList_2);
        clusterSet.put(++countG, clusterList_3);
        clusterSet.put(++countG, clusterList_4);
        clusterSet.put(++countG, clusterList_5);
        clusterSet.put(++countG, clusterList_6);
        clusterSet.put(++countG, clusterList_7);
        clusterSet.put(++countG, clusterList_8);
        clusterSet.put(++countG, clusterList_9);


        try {
            // Read CSV file with javacsv-2.0.jar
            BufferedReader reader = new BufferedReader(new FileReader(inFile));
            CsvReader creader = new CsvReader(reader, ',');
            creader.readHeaders();
            while(creader.readRecord()){

                Point p = new Point(countP++,Double.parseDouble(creader.get(0)),
                        Double.parseDouble(creader.get(1)),
                        Double.parseDouble(creader.get(2)),
                        Double.parseDouble(creader.get(3)),
                        Integer.parseInt(creader.get(4)));

                //Point partition by location
                if(p.getLat()>5) {
                    if((p.getLon()<-90)) {
                        pointSet.get(1).add(p);
                    }else if((p.getLon()<0)) {
                        pointSet.get(2).add(p);
                    }else if((p.getLon()<35)) {
                        pointSet.get(3).add(p);
                    }else if((p.getLon()<80)) {
                        pointSet.get(4).add(p);
                    }else if((p.getLon()<120)) {
                        pointSet.get(5).add(p);
                    }else {
                        pointSet.get(6).add(p);
                    }
                }else {
                    if((p.getLon()<-50)) {
                        pointSet.get(7).add(p);
                    }else if((p.getLon()<40)) {
                        pointSet.get(8).add(p);
                    }else {
                        pointSet.get(9).add(p);
                    }
                }
            }

            //Sort the whole pointList before put them into clusters
//            Comparator<Object> comp = new Mycomparator();
//            for(int i=1;i<=countG;i++) {
//                Collections.sort(pointSet.get(i),comp);
//            }

            //Make the whole pointlist random before put them into clusters
//		for(int i=1;i<=countG;i++) {
//			Collections.shuffle(pointSet.get(i));
//		}


            for(int i=1;i<=countG;i++) {
//                
                int countNum = 0;
                int sizeOfGroup = pointSet.get(i).size();
                double latTemp=-1;
                double lonTemp=-1;
                while(countNum < sizeOfGroup){

                    Cluster c0 = new Cluster();
                    c0.setId(countC++);
                    if(latTemp==-1&&lonTemp==-1){
                        Point p = pointSet.get(i).get(0);
                        c0.setLon(p.getLon());
                        c0.setLat(p.getLat());
                        c0.setDebit(p.getPir());
                        c0.setPointnum(1);
                        pointSet.get(i).remove(0);
                        countNum++;
                    }else{
                        for(int k=0;k<pointSet.get(i).size();k++){
                            if(getDistance(lonTemp,latTemp,pointSet.get(i).get(k).getLon(),pointSet.get(i).get(k).getLat())>RADIUS*2){
                                Point p = pointSet.get(i).get(k);
                                c0.setLon(p.getLon());
                                c0.setLat(p.getLat());
                                c0.setDebit(p.getPir());
                                c0.setPointnum(1);
                                pointSet.get(i).remove(pointSet.get(i).get(k));
                                countNum++;
                                latTemp = c0.getLat();
                                lonTemp = c0.getLon();
                                break;
                            }
                        }
                    }


                    for(int j=0;j< pointSet.get(i).size();j++){

                        if(getDistance(c0.getLon(), c0.getLat(), pointSet.get(i).get(j).getLon(), pointSet.get(i).get(j).getLat()) <= RADIUS){
                            double tempdebit = c0.getDebit()+pointSet.get(i).get(j).getPir();
                            if(tempdebit <= CAPACITY) {
                                c0.getPoints().add(pointSet.get(i).get(j));
                                c0.setDebit(tempdebit);
                                int temppointnum = c0.getPointnum() + 1;
                                c0.setPointnum(temppointnum);
                                countNum++;
                                pointSet.get(i).remove(pointSet.get(i).get(j));

                            }

                        }
                    }
                    clusterSet.get(i).add(c0);
                }

            }

            //Output clustering result
            PrintStream ps = new PrintStream(outputPath);
            System.setOut(ps);

            //First line of output is the number of clusters
            System.out.println(countC-1);
            //Then output every cluster line by line
            for(int i=1;i<=countG;i++) {
                for(Cluster c : clusterSet.get(i)) {
                    System.out.println(c.toString());
                }
            }
            creader.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();}
    }

    //Transform LON/LAT to distance with geodesy-1.1.3.jar
    public static double getDistance(double lon1, double lat1, double lon2, double lat2) {
        GlobalCoordinates source = new GlobalCoordinates(lat1,lon1);
        GlobalCoordinates target = new GlobalCoordinates(lat2,lon2);
        GeodeticCurve geoCurve = new GeodeticCalculator().calculateGeodeticCurve(Ellipsoid.WGS84, source, target);
        double result = geoCurve.getEllipsoidalDistance();
        return result;
    }

}

