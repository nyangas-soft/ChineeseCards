@Entity
public class Card { 
   @PrimaryKey(autoGenerate = true)
   public long card_id; 
   public String hiero; 
   public String pinyin;
   public long level;
   public boolean isAnswered;
   
}

@Entity(foreignKeys = @ForeignKey(entity = Card.class, parentColumns = "card_id", childColumns = "card_id", onDelete = CASCADE), 
       (primaryKeys = {"translation", "card_id;"}))
public class RussianTranslation { 
   public long card_id; 
   public String translation; 
}

@Entity
public class Subject { 
   @PrimaryKey(autoGenerate = true)
   public long subj_id; 
   public String subject; 
}

@Entity(foreignKeys = @ForeignKey(entity = Card.class, parentColumns = "card_id", childColumns = "card_id", onDelete = CASCADE),
       @ForeignKey(entity = Subject.class, parentColumns = "subj_id", childColumns = "subj_id", onDelete = CASCADE),
       (primaryKeys = {"subj_id", "card_id;"}))
public class HieroSubject { 
   public long subj_id; 
   public long card_id; 
}

@Dao
public interface CardDao {
 
   @Query("SELECT * FROM card")
   List<Card> getAll();
 
   @Query("SELECT * FROM card WHERE card_id = :id")
   Card getById(long id);
 
   @Insert
   void insert(Card card);
 
   @Update
   void update(Card card);
 
   @Update("UPDATE card SET isAnswered=true WHERE card_id = :id")
   Card setCardAnswered(long id);
 
    @Update("UPDATE card SET isAnswered=false WHERE card_id = :id")
   Card setCardNotAnswered(long id);
 
   @Delete
   void delete(Card card);
 
  @Query("SELECT * FROM card")
  Cursor getAll();
 
}

@Database(entities = {Card.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
   public abstract CardDao cardDao();
}
