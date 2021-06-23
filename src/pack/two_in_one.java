//Clustering, 2in1 version for all clusterlists for 2 rounds
package pack;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.*;

import com.csvreader.CsvReader;
import org.gavaghan.geodesy.*;

public class two_in_one {
    //Radius of a cluster
    static double RADIUS = 45000.0;
    //Capacity of a cluster
    static double CAPACITY = 4096.0;
    static int countP = 1;//count number of Point
    static int countC = 1;//count number of Cluster
    static int countG = 9;//count number of Group
    static int countOne = 1;
    static boolean flag = false;
    static boolean flagOne = false;

    public static void main(String[] args)throws Exception{

        //Path of the file "generated.csv"
        String filePath = "/Users/fanqiyue/Downloads/啊 semestre 2/project ingénierie reseaux/generated.csv";
        File inFile = new File(filePath);
        //Output clustering result to a txt file
        //String outputPath = "/Users/fanqiyue/Downloads/ClusteringResult.txt";
        String outputPath = "/Users/fanqiyue/Downloads/log.txt";

        //initialize the pointlists and put them into pointset, for holding points in different zones
        List<Point> pointList_1 = new ArrayList<Point>();
        List<Point> pointList_2 = new ArrayList<Point>();
        List<Point> pointList_3 = new ArrayList<Point>();
        List<Point> pointList_4 = new ArrayList<Point>();
        List<Point> pointList_5 = new ArrayList<Point>();
        List<Point> pointList_6 = new ArrayList<Point>();
        List<Point> pointList_7 = new ArrayList<Point>();
        List<Point> pointList_8 = new ArrayList<Point>();
        List<Point> pointList_9 = new ArrayList<Point>();

        //List 10 contains all the points for later checking
        List<Point> pointList_10 = new ArrayList<Point>();

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

        //initialize the clusterlists and put them into clusterset, for holding clusters in different zones
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

        //for holding result of 2in1 method, contains all the clusters, also for later checking
        List<Cluster> clusterList_10 = new ArrayList<Cluster>();



        try {
            // Read CSV file with javacsv-2.0.jar
            System.out.println("Read csv file");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println(df.format(new Date()));// get current time
            BufferedReader reader = new BufferedReader(new FileReader(inFile));
            CsvReader creader = new CsvReader(reader, ',');
            creader.readHeaders();
            while(creader.readRecord()){
                //int c = countP++;
                Point p = new Point(countP++,Double.parseDouble(creader.get(0)),
                        Double.parseDouble(creader.get(1)),
                        Double.parseDouble(creader.get(2)),
                        Double.parseDouble(creader.get(3)),
                        Integer.parseInt(creader.get(4)));

                //Point partition by location, here I create 9 zones by hand
                pointList_10.add(p);
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


            //Make the whole pointlists random
//    		for(int i=1;i<=countG;i++) {
//    			Collections.shuffle(pointSet.get(i));
//    		}

            System.out.println("Start clustering");
            System.out.println(df.format(new Date()));
            for(int i=1;i<=countG;i++) {
                for(Point p : pointSet.get(i)) {

                    //In the beginning, create a new cluster
                    if(clusterSet.get(i).isEmpty()) {
                        Cluster c0 = new Cluster(countC++,p.getLon(),p.getLat(),p.getPir(),1);
                        c0.getPoints().add(p);
                        clusterSet.get(i).add(c0);
                        continue;
                    }

                    for(Cluster c : clusterSet.get(i)) {
                        flag = false;

                        //For each point, we check if it suits any cluster, otherwise, we create a new cluster.
                        if(getDistance(c.getLon(), c.getLat(), p.getLon(), p.getLat()) <= RADIUS) {
                            double tempdebit = c.getDebit()+p.getPir();
                            if(tempdebit <= CAPACITY) {
                                c.setDebit(tempdebit);
                                int temppointnum = c.getPointnum() + 1;
                                //c.setPointnum(temppointnum);
                                //c.getPoints().add(p);
                                flag = true;

                                //Change cluster's center in the mean time when putting a mew point into, DOES IMPROVE A LITTLE BIT
                                if(c.valideCenter(p)){
                                    c.getPoints().add(p);
                                    //System.out.println(countC);
                                    c.setPointnum(temppointnum);
                                    c.changeCenter();
                                }else{
                                    c.getPoints().add(p);
                                    c.setPointnum(temppointnum);
                                }

                                break;
                            }
                        }
                    }
                    if(flag==false) {
                        // If there is no existing cluster can take this point, we create a new cluster using this point as the center
                        Cluster cnew = new Cluster(countC++,p.getLon(),p.getLat(),p.getPir(),1);
                        cnew.getPoints().add(p);
                        clusterSet.get(i).add(cnew);

                    }
                }
            }



            //Number of clusters in the beginning, around 20000
            System.out.println("Before 2 in 1: "+(countC-1));
            System.out.println(df.format(new Date()));

            //Implement 2in1 method for every clusterList, do it two rounds
            int finalOne;//save the number of clusters after merging
            for(int round=1; round<3; round++) {
                System.out.println("2in1 method Round "+round);

                //For every cluster, compare it with all other clusters, if all points in these two clusters can be included by a new cluster, merge them
                for (int i = 1; i <= countG; i++) {
                    System.out.println(i + ": " + clusterSet.get(i).size());
                    finalOne = clusterSet.get(i).size();
                    for (int j = 0; j < clusterSet.get(i).size(); j++) {
                        for (int k = j + 1; k < clusterSet.get(i).size(); k++) {
                            flagOne = false;
                            if (checkCenter(clusterSet.get(i).get(j), clusterSet.get(i).get(k)) && clusterSet.get(i).get(j).valideDebit(clusterSet.get(i).get(k))) {
                                clusterSet.get(i).get(j).clusterMerge(clusterSet.get(i).get(k));
                                clusterSet.get(i).remove(k);
                                finalOne--;
                                break;
                            }
                        }
                    }
                    System.out.println("after " + i + ": " + finalOne);

                }
            }




            //Bring clusterlists together again, this means no zone devided, all clusters in clusterList_10
            for(int i=1;i<=countG;i++) {
                for(Cluster c : clusterSet.get(i)) {
                    clusterList_10.add(c);
                }
            }


            //Implement 2in1 method for all clusters, THIS TAKES A LOT OF TIME
//            System.out.println("All together");
//            System.out.println("10: "+clusterList_10.size());
//            finalOne = clusterList_10.size();
//            for(int i=0;i<clusterList_10.size();i++){
//                for (int j = i + 1; j < clusterList_10.size(); j++) {
//                    flagOne = false;
//                    //two in one general
//                    if(checkCenter(clusterList_10.get(i),clusterList_10.get(j)) && (clusterList_10.get(i).valideDebit(clusterList_10.get(j)))){
//                        clusterList_10.get(i).clusterMerge(clusterList_10.get(j));
//                        clusterList_10.remove(j);
//                        finalOne--;
//                        break;
//                    }
//                }
//            }
//            System.out.println("after 10: "+finalOne);



            //Output clustering result
            PrintStream ps = new PrintStream(outputPath);
            System.setOut(ps);

            //First line of output is the number of clusters
            System.out.println(countC-1);//Our first algo, result around 20000 clusters
            System.out.println(clusterList_10.size());//Result after implemented 2in1 method, around 16000 clusters

            //Then output every cluster line by line into a txt file
            for(int i=1;i<=countG;i++) {
                for(Cluster c : clusterSet.get(i)) {
                    System.out.println(c.toString());
                }
            }

            creader.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();}

        //Check the result
        boolean test = Test.tester(clusterList_10,pointList_10);
        System.out.println(test);
    }

    //Transform LON/LAT to distance with geodesy-1.1.3.jar
    public static double getDistance(double lon1, double lat1, double lon2, double lat2) {
        GlobalCoordinates source = new GlobalCoordinates(lat1,lon1);
        GlobalCoordinates target = new GlobalCoordinates(lat2,lon2);
        GeodeticCurve geoCurve = new GeodeticCalculator().calculateGeodeticCurve(Ellipsoid.WGS84, source, target);
        double result = geoCurve.getEllipsoidalDistance();
        return result;
    }


    //check if two clusters can be merged into one cluster
    public static boolean checkCenter(Cluster c1,Cluster c2){

        double c1_lattemp=0.0,  c1_longtemp=0.0;
        double c2_lattemp=0.0,  c2_longtemp=0.0;
        double latnew,  longnew;
        boolean flag1 = true;
        boolean flag2 = true;

        for(Point p : c1.getPoints()){
            c1_lattemp += p.getLat();
            c1_longtemp += p.getLon();
        }

        for(Point p : c2.getPoints()){
            c2_lattemp += p.getLat();
            c2_longtemp += p.getLon();
        }

        //cluster's new center potentially
        latnew = (c1_lattemp + c2_lattemp)/(c2.getPointnum()+c1.getPointnum());
        longnew = (c1_longtemp + c2_longtemp)/(c2.getPointnum()+c1.getPointnum());


        for(Point p : c1.getPoints()){
            flag1 = true;
            if(getDistance(longnew, latnew,p.getLon(),p.getLat())>45000.0){
                flag1 = false;
                //return false;
                break;
            }
        }

        for(Point p : c2.getPoints()){
            flag2 = true;
            if(getDistance( longnew, latnew,p.getLon(),p.getLat())>45000.0){
                flag2 = false;
                break;
                //return false;
            }
        }

        if(flag1 && flag2){
            return true;
        }else{
            return false;
        }
    }


}


