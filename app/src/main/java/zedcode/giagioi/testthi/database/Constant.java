package zedcode.giagioi.testthi.database;

public interface Constant  {
    String TABLE_NAME = "AddMapDAO";

    String COLUMN_latitude = "latitude";
    String COLUMN_longitude = "longitude";


    String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +

            COLUMN_latitude + " NVARCHAR PRIMARY KEY," +

            COLUMN_longitude + " NVARCHAR" +
            ")";

}
