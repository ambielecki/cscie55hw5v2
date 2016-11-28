package rentalstest;

import org.junit.Test;
import rentals.*;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.Calendar;

/**
 * Created by Bielecki on 10/29/2016.
 */
public class VideoTest {
    // test the Video class, make sure we cannot add videos that trigger the exceptions

    // Test that we cannot add a video with a date before 1900
    @Test
    public void videoDateEarly()
    {
        try {
            Video video = new Video("Testing", 1899);
            fail();
        } catch (VideoException e) {
            // Expected
        }
    }
    // Code to get current year from http://stackoverflow.com/questions/136419/get-integer-value-of-the-current-year-in-java
    @Test
    public void videoDateLate()
    {
        int year = Calendar.getInstance().get(Calendar.YEAR) + 1;
        try {
            Video video = new Video("Testing", year);
            fail();
        } catch (VideoException e) {
            // Expected
        }
    }
    // Test that we cannot add a video with no title
    @Test
    public void videoNoTitle()
    {
        try {
            Video video = new Video("", 2000);
            fail();
        } catch (VideoException e) {
            // Expected
        }
    }

    //Tests for creating and checking out a video
    @Test
    public void checkOutVideo()
    {
        try {
            Video test = new Video("Test", 2000); //create a new video
            assertTrue(test.getTitle().equals("Test")); //check that title is stored and reporting
            assertTrue(test.getYear().equals(2000)); //check that year is stored and reporting
            try {
                test.checkOut();
                assertFalse(test.isAvailable()); //check availability status is correct
                assertTrue(test.isNotAvailable());
                try {
                    test.checkOut(); //try a duplicate checkout
                    fail();
                } catch (RuntimeException e) {
                    //expected
                }
                test.checkIn(); // check back in
                assertTrue(test.isAvailable()); //check status
                assertFalse(test.isNotAvailable());
            } catch (RuntimeException e) {
                fail(); //fail if we cannot checkout a newly created video
            }
            test.removeFromStock();
            assertFalse(test.isAvailable()); //check status if we remove from stock
            assertTrue(test.isNotAvailable()); //learned that we want OUT_OF_STOCK to trigger this
            try {
                test.checkOut();
                fail(); //fail if we can checkout a removed video
            } catch (RuntimeException e) {
                //expected
            }
            test.replaceToStock(); //return the video
            assertTrue(test.isAvailable()); //check status
            assertFalse(test.isNotAvailable());

        } catch (VideoException e) {
            fail(); //fail if we cannot create a video
        }
    }
    // Tests for the account class

    // Test that we cannot add an account without a last name.  These tests initially failed, added an exception
    @Test
    public void accountNoLastName() {
        try {
            Account account = new Account("Andrew", "", "test@test.com");
            fail();
        } catch (AccountException e) {
            // Expected
        }
    }

    // Test that we cannot add an account without a first name.  These tests initially failed, added an exception
    @Test
    public void accountNoFirstName()
    {
        try {
            Account account = new Account("", "Bielecki", "test@test.com");
            fail();
        } catch (AccountException e) {
            // Expected
        }
    }

    // Test that we cannot add an account without an email.  These tests initially failed, added an exception
    @Test
    public void accountNoEmail()
    {
        try {
            Account account = new Account("Andrew","Bielecki","");
            fail();
        } catch (AccountException e) {
            //Expected
        }

    }

    // Test that we cannot add a duplicate account through the videoStore (using email as unique identifier)
    @Test
    public void duplicateAccount()
    {
        try {
            VideoStore videoStore = new VideoStore();
            videoStore.createAccount("Andrew", "Bielecki", "ambielecki@gmail.com");
            videoStore.createAccount("Andrew", "Bielecki", "ambielecki@gmail.com");
            fail();
        } catch (AccountException e) {
            // Expected
        } catch (VideoException v) {
            // Expected
        }
    }

    // Test that details are being stored correctly;
    @Test
    public void checkDetails() {
        try {
            Account test = new Account("Andrew", "Bielecki", "ambielecki@gmail.com");
            assertTrue(test.getEmail().equals("ambielecki@gmail.com"));
        } catch (AccountException e) {
            fail();
        }
    }

    // Tests for the VideoRental Class
    @Test
    public void testRental() {
        try {
            Account account = new Account("Andrew", "Bielecki", "ambielecki@gmail.com");
            Video video1 = new Video("Test", 2000);
            Video video2 = new Video("Test2", 2000);
            VideoRental videoRental = new VideoRental(video1, account);
            VideoRental videoRental2 = new VideoRental(video2, account); // check that multiple videos can be rented
            assertTrue(video1.isNotAvailable()); // check that video has been rented
            assertTrue(videoRental.getDateDue().equals(LocalDate.now().plusDays(14))); //check that due date is correct
            assertTrue(videoRental.isOpen());
            //this turned up an error, Overdue was testing After instead of before
            assertFalse(videoRental.isOverDue()); //cannot find a way to mock the date being later than current
            videoRental.rentalReturn();
            assertTrue(video1.isAvailable());
        } catch (AccountException e) {
            fail();
        } catch (VideoException e) {
            fail();
        }
    }

}