package book;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;

/**
 @author Team Cosmos:
         Erni Ali,
         Phil Vaca,
         Randy Zaatri

 Solution for CS157B Project #1
 Genre.java is a class that creates the Genre entity table.
 It stores book genres. Multiple books may have multiple genres.
 */
@Entity
public class Genre
{
   private String genreName;
   private long id;
   private List<Book> books = new ArrayList<>();

   public Genre(String genreName)
   {
      this.genreName = genreName;
   }

   public Genre()
   {
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

   @Column(name="genre_name")
   public String getGenreName()
   {
      return genreName;
   }

   public void setGenreName(String genreName)
   {
      this.genreName = genreName;
   }

   @ManyToMany
   @JoinTable(name="Book_Genre",
               joinColumns={@JoinColumn(name="genre_id")},
               inverseJoinColumns={@JoinColumn(name="book_id")})
   public List<Book> getBooks()
   {
      return books;
   }

   public void setBooks(List<Book> books)
   {
      this.books = books;
   }

   /**
    Loads the genres of books.
   */
   public static void load()
   {
      Session session = HibernateContext.getSession();
      Transaction tx = session.beginTransaction();
      {
         session.save(new Genre("Mystery"));
         session.save(new Genre("Horror"));
         session.save(new Genre("Education"));
         session.save(new Genre("Math"));
         session.save(new Genre("Database"));
         session.save(new Genre("Thriller"));
         session.save(new Genre("Child Book"));
         session.save(new Genre("Adventure"));
      }
      tx.commit();
      session.close();

      System.out.println("Genre table loaded.");
   }

   /**
    Fetch the genre with a matching name.

    @param genre the genre to match.
    @return the genre or null.
    */
   public static Genre find(String genre)
   {
      // Query by example.
      Genre prototype = new Genre();
      prototype.setGenreName(genre);
      Example example = Example.create(prototype);

      Session session = HibernateContext.getSession();
      Criteria criteria = session.createCriteria(Genre.class);
      criteria.add(example);

      Genre gen = (Genre) criteria.uniqueResult();

      session.close();
      return gen;
   }

   /**
    List the genres and also the books that belong in that genre.
   */
   public static void list()
   {
      Session session = HibernateContext.getSession();
      Criteria criteria = session.createCriteria(Genre.class);
      criteria.addOrder(Order.asc("genreName"));

      List<Genre> genres = criteria.list();
      System.out.println("Titles by Genre.");

      // Loop over each student.
      for (Genre genre : genres)
      {
         genre.print();

         for (Book books : genre.getBooks())
         {
            System.out.printf("    Books: %s (%s)\n", books.getTitle(),
                    books.getPublishedDate());
         }
      }
   }

   /**
    Prints the ID and genre name.
   */
   private void print()
   {
      System.out.printf("%d: %s \n", id, genreName);
   }
}
