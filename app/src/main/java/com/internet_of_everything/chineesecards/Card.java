@Entity
public class Card {
 
   @PrimaryKey
   public long id; 
   public String hiero; 
   public String pinyin;
   public long translate_to_rus;
   
}

@Dao
public interface CardDao {
 
   @Query("SELECT * FROM card")
   List<Card> getAll();
 
   @Query("SELECT * FROM card WHERE id = :id")
   Card getById(long id);
 
   @Insert
   void insert(Card card);
 
   @Update
   void update(Card card);
 
   @Delete
   void delete(Card card);
 
}
