package book;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 @author Team Cosmos:
         Erni Ali,
         Phil Vaca,
         Randy Zaatri

 Solution for CS157B Project #1
 Publisher.java is a class that creates the Publisher entity table.
 It stores the Publishers of books. And one publisher can publish several books.
 */
@Entity
public class Publisher
{
   private String name;
   private long id;
   private List<Book> books = new ArrayList<>();

   public Publisher(String name)
   {
      this.name = name;
      this.id = id;
   }

   public Publisher()
   {
   }

   @Id
   @GeneratedValue
   @Column(name = "pub_id")
   public long getId()
   {
      return id;
   }

   public void setId(long id)
   {
      this.id = id;
   }

   @Column(name = "pub_name")
   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   @OneToMany(mappedBy = "publisher", targetEntity = Book.class,
   cascade = CascadeType.ALL, fetch = FetchType.EAGER)
   public List<Book> getBooks()
   {
      return books;
   }

   public void setBooks(List<Book> books)
   {
      this.books = books;
   }

   //load all the publishers into the database.
   public static void load()
   {
      Session session = HibernateContext.getSession();
      Transaction tx = session.beginTransaction();
      {
         session.save(new Publisher("Pocket Books"));
         session.save(new Publisher("Pearson"));
         session.save(new Publisher("Prentice Hall"));
         session.save(new Publisher("Gallery Books"));
         session.save(new Publisher("Dales Large Print Books"));
         session.save(new Publisher("Random House Digital, Inc"));
      }
      tx.commit();
      session.close();

      System.out.println("Publisher table loaded.");

   }

   /**
    Fetch the publisher with a matching name.

    @param pub the publisher to match.
    @return the publisher or null.
    */
   public static Publisher find(String pub)
   {
      // Query by example.
      Publisher prototype = new Publisher();
      prototype.setName(pub);
      Example example = Example.create(prototype);

      Session session = HibernateContext.getSession();
      Criteria criteria = session.createCriteria(Publisher.class);
      criteria.add(example);

      Publisher publisher = (Publisher) criteria.uniqueResult();

      session.close();
      return publisher;
   }

   /*
    List all the name of the publishers and titles published by publishers.
   */
   public static void list()
   {
      Session session = HibernateContext.getSession();
      Criteria criteria = session.createCriteria(Publisher.class);

      criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
      criteria.addOrder(Order.asc("name"));

      List<Publisher> publishers = criteria.list();
      System.out.println("Titles that are published by publisher.");

      // Loop over each student.
      for (Publisher pub : publishers)
      {
         pub.print();

         for (Book books : pub.getBooks())
         {
            System.out.printf("    Books: %s (%s)\n", books.getTitle(),
                    books.getPublishedDate());

         }
      }
   }

   public static String getList()
   {
      String list = "";
      Session session = HibernateContext.getSession();
      Criteria criteria = session.createCriteria(Publisher.class);
      criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
      criteria.addOrder(Order.asc("id"));

      List<Publisher> publishers = criteria.list();
      list += "Titles that are published by publishers:";

      for (Publisher pub : publishers)
      {
         list+= "\n \n" + pub.getId() + ". " + pub.getName();
         for (Book books : pub.getBooks())
         {
            list+= "\n       " + books.getTitle() + " ("
                    + books.getPublishedDate() + ")";
         }
      }
      return list;
   }

   public static String getList(boolean typeToggle, String attribute,
                                      String findAttribute, String findValue)
   {
      String list = "";
      Session session = HibernateContext.getSession();
      Criteria criteria = session.createCriteria(Publisher.class);
      criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

      //Do only if ordering was specified
      if (attribute != null)
      {
        if (typeToggle)
        {
          criteria.addOrder(Order.asc(attribute));
        }
        else
        {
          criteria.addOrder(Order.desc(attribute));
        }
      }

      //Do only selection was specified
      if (findAttribute != null && findValue != null)
      {
          criteria.add(Restrictions.like(findAttribute,"%"+findValue+"%"));
      }

      List<Publisher> publishers = criteria.list();
      list += "Titles that are published by publishers:";

      for (Publisher pub : publishers)
      {
         list+= "\n \n" + pub.getId() + ". " + pub.getName();
         for (Book books : pub.getBooks())
         {
            list+= "\n       " + books.getTitle() + " ("
                    + books.getPublishedDate() + ")";
         }
      }
      return list;
   }
   /**
    Print the id and name of the publishers.
   */
   private void print()
   {
      System.out.printf("%d: %s \n", id, name);
   }
}
