package net.harvey.didyourhomework;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by Administrator on 2015-06-06.
 */
public class MySQLiteControl {

    private SQLiteDatabase db;
    private MySQLiteOpenHelper helper;

    // insert
    public void insert(String name, Calendar e_start, Calendar e_end,int e_iter_time,String e_iter_type, boolean e_homework) {
        db = helper.getWritableDatabase(); // db 객체를 얻어온다. 쓰기 가능
        ContentValues values = new ContentValues();
        // db.insert의 매개변수인 values가 ContentValues 변수이므로 그에 맞춤
        // 데이터의 삽입은 put을 이용한다.
        values.put("e_name", name);
//        values.put("e_start", e_start);
//        values.put("e_end", e_end);
        values.put("e_iter_time", e_iter_time);
        values.put("e_iter_type", e_iter_type);
        values.put("e_homework", e_homework);

        db.insert("event", null, values); // 테이블/널컬럼핵/데이터(널컬럼핵=디폴트)
        // tip : 마우스를 db.insert에 올려보면 매개변수가 어떤 것이 와야 하는지 알 수 있다.
    }

    // update
    public void update(String name, int age) {
        db = helper.getWritableDatabase(); //db 객체를 얻어온다. 쓰기가능

        ContentValues values = new ContentValues();
        values.put("age", age);    //age 값을 수정
        db.update("student", values, "name=?", new String[]{name});
        /*
         * new String[] {name} 이런 간략화 형태가 자바에서 가능하다
         * 당연하지만, 별도로 String[] asdf = {name} 후 사용하는 것도 동일한 결과가 나온다.
         */

        /*
         * public int update (String table,
         * ContentValues values, String whereClause, String[] whereArgs)
         */
    }

    // delete
    public void delete(String name) {
        db = helper.getWritableDatabase();
        db.delete("student", "name=?", new String[]{name});
        Log.i("db", name + "정상적으로 삭제 되었습니다.");
    }

    // select
    public void select() {

        // 1) db의 데이터를 읽어와서, 2) 결과 저장, 3)해당 데이터를 꺼내 사용

        db = helper.getReadableDatabase(); // db객체를 얻어온다. 읽기 전용
        Cursor c = db.query("student", null, null, null, null, null, null);

        /*
         * 위 결과는 select * from student 가 된다. Cursor는 DB결과를 저장한다. public Cursor
         * query (String table, String[] columns, String selection, String[]
         * selectionArgs, String groupBy, String having, String orderBy)
         */

        while (c.moveToNext()) {
            // c의 int가져와라 ( c의 컬럼 중 id) 인 것의 형태이다.
            int _id = c.getInt(c.getColumnIndex("_id"));
            String name = c.getString(c.getColumnIndex("name"));
            int age = c.getInt(c.getColumnIndex("age"));
            String address = c.getString(c.getColumnIndex("address"));
            Log.i("db", "id: " + _id + ", name : " + name + ", age : " + age
                    + ", address : " + address);
        }
    }
}
