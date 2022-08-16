package bgu.spl.net.impl.BGRSServer;
import java.util.Comparator;

public class ComparatorBySerialNum implements Comparator<Short> {
    Database database= Database.getInstance();
    @Override
    public int compare(Short o1, Short o2) {
        Course a= database.getCoursesMap().get(o1);
        Course b= database.getCoursesMap().get(o2);
        if(a.getSerialNumber()>b.getSerialNumber())
            return 1;
        else
            return -1;
    }


}
