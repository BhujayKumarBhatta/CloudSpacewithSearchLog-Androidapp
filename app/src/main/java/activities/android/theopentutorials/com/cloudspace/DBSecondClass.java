package activities.android.theopentutorials.com.cloudspace;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by acer on 27-05-2016.
 */

public class DBSecondClass extends SQLiteOpenHelper {
    public static final String DB_NAME = "CSDB";

    //Table  for Data HAll
    public static final String DC_TABLE_NAME = "DcHallTable";
    public static final String COLUMN_DCID = "DCHallid";
    public static final String COLUMN_DCHall_NAME = "dcHallname";
    public static final String COLUMN_DC_KWmax = "dcKwmax";
    public static final String COLUMN_DC_MaxHumid = "dcMaxHumid";
    public static final String COLUMN_DC_SIZE = "dcHallSize";


    //Table for Rack
    public static final String TABLE_NAME = "RackTable";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME ="name";
    public static final String COLUMN_ADD = "address";
    public static final String COLUMN_DC_NAME = "dcHallname";
    public static final String COLUMN_RACK_NAME = "rackname";
    public static final String COLUMN_RACK_LOCATION = "racklocation";
    public static final String COLUMN_RK_Usize = "rackUsize";
    public static final String COLUMN_RK_Uused = "rackUused";
    public static final String COLUMN_RK_Ufree = "rackUfree";
    public static final String COLUMN_RK_MaxWeight = "rackMaxWeight";
    public static final String COLUMN_RK_CurrentWeight = "rackCurrentWeight";
    public static final String COLUMN_RK_MaxKW = "rackMaxKW";
    public static final String COLUMN_RK_USedKW = "rackUsedKw";
    public static final String COLUMN_RK_MaxTemp = "rackMAxTemp";
    public static final String COLUMN_RK_Temp = "rackTemp";
    public static final String COLUMN_RK_MaxHumid = "rackMaxHumid";
    public static final String COLUMN_RK_Humid = "rackHumid";
    public static final String COLUMN_RK_Model = "rackModel";
    public static final String COLUMN_RK_Keylock= "Keylock";

    private static final int DB_VERSION = 4;

    public DBSecondClass(Context context) {
        super(context,DB_NAME,null,DB_VERSION);
    }

    // Called when the database connection is being configured.
    // Configure database settings for things like foreign key support, write-ahead logging, etc.
   // @Override
    //public void onConfigure(SQLiteDatabase db) {
       // super.onConfigure(db);
       // db.setForeignKeyConstraintsEnabled(true);
    //}

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sqlDChall = "CREATE TABLE " +DC_TABLE_NAME
                +"(" +COLUMN_DCID+
                " INTEGER PRIMARY KEY AUTOINCREMENT, " +COLUMN_DCHall_NAME+
                " VARCHAR, " +COLUMN_DC_SIZE+
                " INTEGER, " +COLUMN_DC_KWmax+
                " INTEGER, " +COLUMN_DC_MaxHumid+
                " INTEGER);";

        String sqlRack = "CREATE TABLE " +TABLE_NAME
                +"(" +COLUMN_ID+
                " INTEGER PRIMARY KEY AUTOINCREMENT, " +COLUMN_DCID+
                " INTEGER, " +COLUMN_DC_NAME+
                " VARCHAR, " +COLUMN_RACK_NAME+
                " VARCHAR, " +COLUMN_RACK_LOCATION+
                " VARCHAR, " +COLUMN_RK_Usize+
                " INTEGER, " +COLUMN_RK_Uused+
                " INTEGER, " +COLUMN_RK_Ufree+
                " INTEGER, " +COLUMN_RK_MaxKW+
                " INTEGER, " +COLUMN_RK_USedKW+
                " INTEGER, " +COLUMN_RK_MaxTemp+
                " INTEGER, " +COLUMN_RK_Temp+
                " INTEGER, " +COLUMN_RK_MaxHumid+
                " INTEGER, " +COLUMN_RK_Humid+
                " INTEGER, " +COLUMN_RK_MaxWeight+
                " INTEGER, " +COLUMN_RK_CurrentWeight+
                " INTEGER, " +COLUMN_RK_Model+
                " VARCHAR, " +COLUMN_RK_Keylock+
                " VARCHAR);";

//,"+"FOREIGN KEY("+COLUMN_DCID+") REFERENCES "+DC_TABLE_NAME+"("+COLUMN_DCID+"
        /*String sql = "CREATE TABLE " +TABLE_NAME
                +"(" +COLUMN_ID+
                " INTEGER PRIMARY KEY AUTOINCREMENT, " +COLUMN_NAME+
                " VARCHAR, " +COLUMN_ADD+
                " VARCHAR, " +COLUMN_DC_NAME+
                " VARCHAR, " +COLUMN_RACK_NAME+
                " VARCHAR, " +COLUMN_RACK_LOCATION+
                " VARCHAR);";
*/
        db.execSQL(sqlRack);
        db.execSQL(sqlDChall);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS "+TABLE_NAME+";";
        db.execSQL(sql);
        onCreate(db);
    }

    public boolean addDChall(JSONArray cabinetArray){
        SQLiteDatabase db = this.getWritableDatabase();
        //String sqlDeleteAll="DELETE * FROM "+ TABLE_NAME +";";
        //db.execSQL(sqlDeleteAll);
        db.delete(DC_TABLE_NAME,null,null);
        ContentValues contentValues = new ContentValues();
        ArrayList dchall = new ArrayList ();
        try{
            for (int i = 0; i < cabinetArray.length(); i++) {
                JSONObject cabinetObj = cabinetArray.getJSONObject(i);
                String DataCenterName = cabinetObj.getString("DataCenterName");
                if(!dchall.contains(DataCenterName ))
                    dchall.add(DataCenterName );
            }
            for (int i = 0; i < dchall.size(); i++) {
             String dc = dchall.get(i).toString();
                contentValues.put(COLUMN_DC_NAME, dc);
                db.insert(DC_TABLE_NAME, null, contentValues);
            }
        }catch (Exception e){e.printStackTrace();}
        db.close();
        return true;
    }
    public boolean addCabinet(JSONArray cabinetArray){
        SQLiteDatabase db = this.getWritableDatabase();
        //String sqlDeleteAll="DELETE * FROM "+ TABLE_NAME +";";
        //db.execSQL(sqlDeleteAll);
        db.delete(TABLE_NAME,null,null);
        ContentValues contentValues = new ContentValues();
        try{
            for (int i = 0; i < cabinetArray.length(); i++) {
                JSONObject cabinetObj = cabinetArray.getJSONObject(i);
                String DataCenterName = cabinetObj.getString("DataCenterName");
                String CabinetID = cabinetObj.getString("CabinetID");
                String Location = cabinetObj.getString("Location");
                String  CabinetHeight =cabinetObj.getString("CabinetHeight");
                String MaxKW  =cabinetObj.getString("MaxKW");
                String MaxWeight  =cabinetObj.getString("MaxWeight");
                String Model  =cabinetObj.getString("Model");
                String Keylock  =cabinetObj.getString("Keylock");

                contentValues.put(COLUMN_DC_NAME, DataCenterName);
                contentValues.put(COLUMN_RACK_NAME, CabinetID);
                contentValues.put(COLUMN_RACK_LOCATION, Location);
                contentValues.put(COLUMN_RK_Usize,CabinetHeight);
                contentValues.put(COLUMN_RK_MaxKW,MaxKW);
                contentValues.put(COLUMN_RK_MaxWeight,MaxWeight);
                contentValues.put(COLUMN_RK_Model,Model);
                contentValues.put(COLUMN_RK_Keylock,Keylock);

                db.insert(TABLE_NAME, null, contentValues);
            }
        }catch (Exception e){e.printStackTrace();}
        db.close();
        return true;
    }

    public ArrayList getDcHalls(){
        SQLiteDatabase db = this.getReadableDatabase();
        //String sql = "SELECT * FROM Persons WHERE id="+id+";";
        //String sql = "SELECT * FROM RackTable"+";";
        String sql = "SELECT DISTINCT "+COLUMN_DC_NAME+" FROM "+TABLE_NAME+";";
        //String sql = "SELECT * FROM "+TABLE_NAME+";";
        Cursor c = db.rawQuery(sql, null);
        int cc=c.getCount();
        String dchall_name = null;
        ArrayList dc_hall_list= new ArrayList();
        try {
            c.moveToFirst();
            for (int x=0;x<c.getCount();x++ ) {
                    dchall_name= c.getString(c.getColumnIndex(COLUMN_DC_NAME));
                    dc_hall_list.add(dchall_name);
                    c.moveToNext();
                }

        }catch (Exception e){e.printStackTrace();
        }finally {
            c.close();
        }

        return dc_hall_list;

    }
    public ArrayList getDcHallsAttribs(){
        SQLiteDatabase db = this.getReadableDatabase();
        //String sql = "SELECT * FROM Persons WHERE id="+id+";";
        //String sql = "SELECT * FROM RackTable"+";";
        //String sql = "SELECT DISTINCT "+COLUMN_DC_NAME+" FROM "+TABLE_NAME+";";
        String sql = "SELECT DISTINCT "+COLUMN_DC_NAME+" as DCNAME "+
         ",(SELECT COUNT(*) FROM "+TABLE_NAME+" T1"+" WHERE T1."+COLUMN_DC_NAME+" =  T."+COLUMN_DC_NAME +") RACKCOUNT" +
         ",(SELECT SUM("+COLUMN_RK_MaxKW+") FROM "+TABLE_NAME +" T1"+" WHERE T1."+COLUMN_DC_NAME+" =  T."+COLUMN_DC_NAME+") rackmaxKw"+
         ",(SELECT SUM("+COLUMN_RK_MaxWeight+") FROM "+TABLE_NAME +" T1 " + " WHERE T1."+COLUMN_DC_NAME+" =  T."+COLUMN_DC_NAME+") rackmaxweight"+
         ",(SELECT SUM("+COLUMN_RK_Usize+") FROM "+TABLE_NAME +" T1"+ " WHERE T1."+COLUMN_DC_NAME+" =  T."+COLUMN_DC_NAME+") racMaxUsize "+
          "  FROM "+TABLE_NAME +" T;";

        Cursor c = db.rawQuery(sql, null);
        int cc=c.getCount();
        String dchall_name = null;
        ArrayList dc_hall_list= new ArrayList();
        try {
            c.moveToFirst();
            for (int x=0;x<c.getCount();x++ ) {
                dchall_name= c.getString(c.getColumnIndex("DCNAME"));
                String rackcount= c.getString(c.getColumnIndex("RACKCOUNT"));
                String dcMaxKW=c.getString(c.getColumnIndex("rackmaxKw"));
                String dcMaxUsize=c.getString(c.getColumnIndex("racMaxUsize"));
                String dcMaxWeright=c.getString(c.getColumnIndex("racMaxUsize"));
                String dcAttribs= dchall_name+","+ rackcount+" racks,"+dcMaxUsize+" U,"
                        +dcMaxKW+" kw,:"+ dcMaxWeright +" kg";
                dc_hall_list.add(dcAttribs);
                c.moveToNext();
            }

        }catch (Exception e){e.printStackTrace();
        }finally {
            c.close();
        }
        return dc_hall_list;
    }

    public  ArrayList getRackListInHall(String hallName){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM "+TABLE_NAME+" WHERE "+COLUMN_DC_NAME+"='"+hallName+"';";
        Cursor c = db.rawQuery(sql, null);
        int cc=c.getCount();
        ArrayList rackListinHalls = new ArrayList();
        String rack =null;
        try {
            c.moveToFirst();
            for (int x=0;x<c.getCount();x++ ) {
                rack= "Rack ID:- " +c.getString(c.getColumnIndex(COLUMN_RACK_NAME))+
                        ", Location & Name: - "+c.getString(c.getColumnIndex(COLUMN_RACK_LOCATION));
                rackListinHalls.add(rack);
                c.moveToNext();
            }
        }catch (Exception e){e.printStackTrace();
        }finally {
            c.close(); }
        return rackListinHalls;
    }

    public  ArrayList getRackInHallDetails(String hallName){
        SQLiteDatabase db = this.getReadableDatabase();
        String S[]=hallName.split(",");
        String sql = "SELECT * FROM "+TABLE_NAME+" WHERE "+COLUMN_DC_NAME+"='"+S[0].trim()+"';";

        Cursor c = db.rawQuery(sql, null);
        int cc=c.getCount();
        ArrayList rackListinHalls = new ArrayList();
        String rack =null;
        int totalUSize=0;
        int totlMaxKw=0;
        int totalMaxWeight=0;
        try {
            c.moveToFirst();
            for (int x=0;x<c.getCount();x++ ) {
                rack= "Rack ID:- " +c.getString(c.getColumnIndex(COLUMN_RACK_NAME))+
                        ", Location & Name: - "+c.getString(c.getColumnIndex(COLUMN_RACK_LOCATION))+
                        ", U Size: - "+c.getString(c.getColumnIndex(COLUMN_RK_Usize))+
                        ", Max KW: - "+c.getString(c.getColumnIndex(COLUMN_RK_MaxKW))+
                        ", Max Weight: - "+c.getString(c.getColumnIndex(COLUMN_RK_MaxWeight));

                rackListinHalls.add(rack);
                c.moveToNext();
            }
        }catch (Exception e){e.printStackTrace();
        }finally {
            c.close(); }
        return rackListinHalls;

    }

    public  ArrayList getRackInHallAttribTotals(String hallName){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM "+TABLE_NAME+" WHERE "+COLUMN_DC_NAME+"='"+hallName+"';";
        Cursor c = db.rawQuery(sql, null);
        int totalRacks=c.getCount();
        ArrayList rackAtrribTotals = new ArrayList();
        int totalUSize=0;
        int totlMaxKw=0;
        int totalMaxWeight=0;
        try {
            c.moveToFirst();
            for (int x=0;x<c.getCount();x++ ) {
                totalUSize=totalUSize+c.getInt((c.getColumnIndex(COLUMN_RK_Usize)));
                totlMaxKw=totlMaxKw+c.getInt((c.getColumnIndex(COLUMN_RK_MaxKW)));
                totalMaxWeight=totalMaxWeight+c.getInt((c.getColumnIndex(COLUMN_RK_MaxWeight)));
                c.moveToNext();
            }
            rackAtrribTotals.add(totalUSize);rackAtrribTotals.add(totlMaxKw);rackAtrribTotals.add(totalMaxWeight);
        }catch (Exception e){e.printStackTrace();
        }finally {
            c.close(); }
        return rackAtrribTotals;
    }
}

