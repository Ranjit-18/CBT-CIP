package com.internship;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Book
 {
    private String title;
    private String author;

    
    public Book(String title, String author) 
    {
        this.title = title;
        this.author = author;
    }

    
    public String getTitle() 
    {
        return title;
    }

    public String getAuthor() 
    {
        return author;
    }

    public String toString()
    {
        return "Title: " + title + ", Author: " + author;
    }
}

class Library {
    private List<Book> library;

    
    public Library()
    {
        this.library = new ArrayList<>();
    }

    public void addBook(String title, String author)
    {
        Book newBook = new Book(title, author);
        library.add(newBook);
        System.out.println("Book added successfully!");
    }

    
    public void searchBooks(String query)
    {
        boolean found = false;
        for (Book book : library)
        {
            if (book.getTitle().equalsIgnoreCase(query) || book.getAuthor().equalsIgnoreCase(query)) 
             {
                System.out.println(book);
                found = true;
            }
        }
        if (!found)
        {
            System.out.println("No books found with the search query: " + query);
        }
    }

     public void listAllBooks()
    {
        if (library.isEmpty())
        {
            System.out.println("The library is empty.");
        } 
	else
	 {
            System.out.println("Listing all books:");
            for (Book book : library)
	    {
                System.out.println(book);
            }
        }
    }
}

public class LibrarySystem 
{
    public static void main(String[] args)
    {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit)
         {
            System.out.println("\nLibrary System");
            System.out.println("1. Add Book");
            System.out.println("2. Search Books");
            System.out.println("3. List All Books");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  
            switch (choice)
             {
                case 1:
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter book author: ");
                    String author = scanner.nextLine();
                    library.addBook(title, author);
                    break;
                case 2:
                    System.out.print("Enter title or author to search: ");
                    String query = scanner.nextLine();
                    library.searchBooks(query);
                    break;
                case 3:
                    library.listAllBooks();
                    break;
                case 4:
                    exit = true;
                    System.out.println("Exiting Library System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }
}

