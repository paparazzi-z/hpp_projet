package corona;

public class Person {
    private int person_id;
    private int diagnosed_ts;
    private int contaminated_by;
    private String country;

    public Person(int p, int d, int c, String co){
        this.person_id = p;
        this.diagnosed_ts = d;
        this.contaminated_by = c;
        this.country = co;
    }

    public int getID(){
        return this.person_id;
    }

    public int getDiagnosedTS(){
        return this.diagnosed_ts;
    }

    public int getContaminatedBy(){
        return this.contaminated_by;
    }

    public String getCountry(){
        return this.country;
    }


}
