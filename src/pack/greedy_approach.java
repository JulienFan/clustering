//Greedy approach，
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
        import sun.util.resources.cldr.lu.CalendarData_lu_CD;

public class greedy_approach {
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
        String outputPath = "/Users/fanqiyue/Downloads/GreedyClusteringResult.txt";

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

        //List 10 contains all the points for later checking
        List<Point> pointList_10 = new ArrayList<Point>();

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

        //holding the buffer clusters
        List<Cluster> clusterList_10 = new ArrayList<Cluster>();

        //holding the best clusters
        List<Cluster> clusterList_11 = new ArrayList<Cluster>();



        try {
            // Read CSV file with javacsv-2.0.jar
            System.out.println("Read csv file");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
            BufferedReader reader = new BufferedReader(new FileReader(inFile));
            CsvReader creader = new CsvReader(reader, ',');
            creader.readHeaders();
            while(creader.readRecord()){
                //int c = countP++;
                Point p = new Point(countP++,Double.parseDouble(creader.get(0)),
                        Double.parseDouble(creader.get(1)),
                        Double.parseDouble(creader.get(2)),
                        Double.parseDouble(creader.get(3)),
                        Integer.parseInt(creader.get(4)),false);

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




            System.out.println("Start clustering");
            System.out.println(df.format(new Date()));
            for(int i=1;i<=countG;i++) {
            //for(int i=1;i<=1;i++) {
                for (Point p : pointSet.get(i)) {

                    Cluster c0 = new Cluster(p.getId(), p.getLon(), p.getLat(), p.getPir(), 1);
                    c0.getPoints().add(p);

                    for (Point p2 : pointSet.get(i)) {
                        if (getDistance(c0.getLon(), c0.getLat(), p2.getLon(), p2.getLat()) <= RADIUS && c0.valideDebit(p2) && !p2.equals(p)) {
                            c0.setPointnum(c0.getPointnum() + 1);
                            c0.setDebit(c0.getDebit() + p2.getPir());
                            c0.getPoints().add(p2);
                        }
                    }
                    clusterSet.get(i).add(c0);
                }
                System.out.println("Zone: "+i);
                System.out.println(clusterSet.get(i).size());
                System.out.println(df.format(new Date()));

            }


            Comparator<Object> comp = new Mycomparator();
            for(int i=1;i<=countG;i++) {
            //for(int i=1;i<=1;i++) {
                Collections.sort(clusterSet.get(i),comp);
            }


            int index_count = 0;
            for(int i=1;i<=countG;i++) {


                for(int j = 0; j < clusterSet.get(i).size(); j++){
                    boolean check=true;
                    for(Point p : clusterSet.get(i).get(j).getPoints()){
                        if(p.getIsTreated() == true) {
                            check = false;
                            break;
                        }
                    }
                    if(check){
                        clusterList_10.add(clusterSet.get(i).get(j));

                        for(Point p : clusterSet.get(i).get(j).getPoints()){
                            p.setIsTreated(true);
                            index_count++;
                            }
                        }
                }




                System.out.println("Choosing cluster, zone: " + i);
                System.out.println(clusterList_10.size());
                System.out.println("pointnum: "+index_count);
                System.out.println("pointList size: "+pointSet.get(i).size());
                System.out.println(df.format(new Date()));

                for(Point p: pointSet.get(i)){
                    if(p.getIsTreated() == false) {
                        index_count++;
                        Cluster c0 = new Cluster(p.getId(), p.getLon(), p.getLat(), p.getPir(), 1);
                        c0.getPoints().add(p);
                        clusterList_10.add(c0);
                    }
                }

                System.out.println("after 2nd count, pointnum: "+index_count);

                System.out.println("Before 2in1: "+clusterList_10.size());
                int finalOne;
                for(int cycle=1;cycle<5;cycle++) {
                    finalOne = clusterList_10.size();
                    for (int k = 0; k < clusterList_10.size(); k++) {
                        for (int m = k + 1; m < clusterList_10.size(); m++) {
                            flagOne = false;
                            //two in one general
                            if (checkCenter(clusterList_10.get(k), clusterList_10.get(m)) && (clusterList_10.get(k).valideDebit(clusterList_10.get(m)))) {
                                clusterList_10.get(k).clusterMerge(clusterList_10.get(m));
                                clusterList_10.remove(m);
                                finalOne--;
                                break;
                            }
                        }
                    }
                    System.out.println("after "+ cycle +" round: " + finalOne);
                }

                for(Cluster c : clusterList_10){
                    clusterList_11.add(c);
                }
                clusterList_10.clear();


            }




            //System.out.println(clusterList_10.size());

            //Output clustering result
            PrintStream ps = new PrintStream(outputPath);
            System.setOut(ps);

            //First line of output is the number of clusters

            System.out.println(clusterList_11.size());//Result after implemented 2in1 method, around 16000 clusters
            System.out.println("pointnum:"+index_count);
            //System.out.println("poingList1:"+pointList_1.size());
            //Then output every cluster line by line into a txt file
//            for(int i=1;i<=countG;i++) {
//                for(Cluster c : clusterSet.get(i)) {
//                    System.out.println(c.toString());
//                }
//            }
            for(Cluster c : clusterList_11) {
                    System.out.println(c.toString());
                }

            creader.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();}

        //TODO: Check the result
        boolean test = Test.tester(clusterList_11,pointList_10);
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


