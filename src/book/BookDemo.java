package book;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 @author Team Cosmos:
         Erni Ali,
         Phil Vaca,
         Randy Zaatri

 Solution for CS157B Project #1
 BookDemo.java is a class with the main class.
 */
public class BookDemo
{
    private static final String HELP_MESSAGE =
        "*** Commands: create, load, quit\n"+
        "***           books, authors, publishers, genres";

    public static void main(String args[])
    {
        BufferedReader stdin =
                new BufferedReader(new InputStreamReader(System.in));
        String command;

        Class klasses[] = {Book.class, Publisher.class, ISBN.class,
                           Author.class, Genre.class};
        HibernateContext.addClasses(klasses);

        do {
            System.out.print("\nCommand? ");

            try {
                command = stdin.readLine();
            }
            catch (java.io.IOException ex) {
                command = "?";
            }

            String parts[] = command.split(" ");

            if (command.equalsIgnoreCase("create")) {
                HibernateContext.createSchema();
            }
            else if (command.equalsIgnoreCase("load")) {
               Publisher.load();
               Author.load();
               Genre.load();
               Book.load();
            }
            else if (command.equalsIgnoreCase("publishers")) {
                Publisher.list();
            }
            else if (command.equalsIgnoreCase("books")) {
                Book.list();
            }
            else if (command.equalsIgnoreCase("authors")) {
                Author.list();
            }
            else if (command.equalsIgnoreCase("genres")) {
                Genre.list();
            }
            else if (!command.equalsIgnoreCase("quit")) {
                System.out.println(HELP_MESSAGE);
            }
        } while (!command.equalsIgnoreCase("quit"));
    }
}
