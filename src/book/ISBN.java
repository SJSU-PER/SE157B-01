package book;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 @author Team Cosmos:
         Erni Ali,
         Phil Vaca,
         Randy Zaatri

 Solution for CS157B Project #1
 ISBN.java is a class that creates the ISBN entity table. It stores the ISBN13
 number of a book.
 */
@Entity
public class ISBN
{
   private String ISBNNumber;
   private long id;

   public ISBN()
   {
   }

   public ISBN(String ISBNNumber)
   {
      this.ISBNNumber = ISBNNumber;
   }

   @Column(name="isbn_number")
   public String getISBNNumber()
   {
      return ISBNNumber;
   }
   public void setISBNNumber(String ISBNNumber)
   {
      this.ISBNNumber = ISBNNumber;
   }

   @Id
   @GeneratedValue
   @Column(name="id")
   public long getId()
   {
      return id;
   }
   public void setId(long id)
   {
      this.id = id;
   }
}
