package pack;

import java.util.ArrayList;
import java.util.List;

public class Test {

    public static boolean tester (List<Cluster> clusters , List<Point> points) {


        System.out.println("Verification des resultats ...") ;


        // ------------------------------------------------------------------------------------------------------------------------------------ //


        System.out.println("Verification de l'unicite de traitement des points ...") ;
        /* Verifier que les points ne sont assignes qu'une seule fois */
        // D'abord je cree une liste qui contient une fois l'id de chaque point a traiter
        List<Integer> pointsID = new ArrayList<Integer> () ;
        int index ;
        for (Point point : points) {
            pointsID.add(point.getId()) ;
        }

        for (Cluster cluster : clusters) {
            for (Point point : cluster.getPoints()) {
                // Pour chaque point de chaque cluster, j'enleve l'id associe dans la liste cree
                index = pointsID.indexOf(point.getId()) ;
                if (index >= 0) {
                    pointsID.remove(index) ;
                } else {
                    // Dans le cas ou l'index ne s'y trouve pas, c'est qu'il a deja ete retire, il y a donc une erreur
                    System.out.println(
                            "Resultats non conformes : Le points d'id <"
                                    + point.getId()
                                    + "> est assigné plusieurs fois ! ...");
                    return false ;
                }
            }
        }

        System.out.println("OK : Tous les points sont assignes a un cluster unique") ;

        if (pointsID.size() > 0) {
            System.out.println(
                    "Erreur : Nombre de points non traités : " + pointsID.size());
            return false ;
        }

        // ------------------------------------------------------------------------------------------------------------------------------------ //


        /* Verifier que tous les points sont dans la zone du cluster qui leur est assigne */
        System.out.println("Verification de la distance des points a leur cluster ...") ;
        for (Cluster cluster : clusters) {
            for (Point point : cluster.getPoints()) {
                double distance = two_in_one.getDistance(
                        cluster.getLon(),
                        cluster.getLat(),
                        point.getLon(),
                        point.getLat()) ;
                if (distance > two_in_one.RADIUS) {
                    System.out.println(
                            "Resultats non conformes : La distance entre le point <"
                                    + point.getId()
                                    + "> et son cluster, d'id <"
                                    + cluster.getId()
                                    + "> est de "+distance+"m ...)");
                    return false ;
                }

            }
        }
        System.out.println("OK : Tous les points sont bien dans la zone de leur cluster");


        // ------------------------------------------------------------------------------------------------------------------------------------ //


        /* Verifier que chaque cluster ne depasse pas sa capacite */
        System.out.println("Verification de la capacite des clusters");
        for (Cluster cluster : clusters) {
            double utilisation = 0.0d ;
            for (Point point : cluster.getPoints()) {
                utilisation += point.getPir() ;
            }
            if (utilisation > two_in_one.CAPACITY) {
                System.out.println(
                        "Resultats non conformes : Le cluster <"
                                + cluster.getId()
                                + "> distribue un debit total de "
                                + utilisation + " ...");
                return false ;
            }
        }
        System.out.println("Tous les clusters distribuent un debit total coherent !") ;


        // ------------------------------------------------------------------------------------------------------------------------------------ //


        // Si et seulement si tous les test passent
        System.out.println("Tous les tests sont passes, les resultats sont conformes !") ;
        return true ;
    }



    public static void main (String[] args) {

        // Tester la classe test

        Point p1 = new Point (1, 1.0    , 45000.0    , 2000.0, 1900.0, 1) ;
        Point p2 = new Point (2, 30000.0, 1.0    , 1000.0, 1900.0, 1) ;
        Point p3 = new Point (3, 1.0    , 50000.0, 4000.0, 1900.0, 1) ;

        Cluster c1 = new Cluster (1, 1.0,     1.0,     2000.0, 1) ;
        Cluster c2 = new Cluster (2, 30000.0, 1.0,     1000.0, 2) ;
        Cluster c3 = new Cluster (3, 1.0,     50000.0, 4000.0, 3) ;

        c1.getPoints().add(p1) ;
        c2.getPoints().add(p2) ;
        c3.getPoints().add(p3) ;

        List<Cluster> clusters = new ArrayList<Cluster>() ;
        clusters.add(c1) ;
        clusters.add(c2) ;
        clusters.add(c3) ;

        List<Point> points = new ArrayList<Point> () ;
        points.add(p1) ;
        points.add(p2) ;
        points.add(p3) ;

        tester (clusters,points) ;

    }

}
