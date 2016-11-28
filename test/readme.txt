Andrew Bielecki
CSCI E55 Fall 2016 - HW5

For this Homework the purpose was to write JUnit tests for the Video, Account, and VideoRental classes of the
VideoRental Package.

My general philosophy for this is to attempt to exercise each function within each of those classes to the best of
my ability.

For the account class, I looked to see whether we could create accounts that are missing any of the key pieces of
information (first name, last name, email address) and whether two accounts with the same email (which I consider the
unique identifier) could be created.  In this case, there was no exceptions being thrown for these examples, so I
created an AccountException modeled on the VideoException.

For the Video class I created test to see if videos could be created without a name or year, or with a year that is
out of bounds.

I also test a variety of cases for checking out a video and checking on it's status.  This showed that when a video is
out of stock it does not test as unAvailable, which was fixed.

For the videoRental class I tested the ability to check out and in videos and their status.  This revealed that
the overdue functionality was backwards.

I was able to get the ant build running, but had some issues getting my classpath set for the build.

