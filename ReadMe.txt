—-Anagrams Checker—-

- Version 1.0 04/08/18 -

- Designed and developed by Gerret Kubota ©2018 -


========================
Purpose of program.
========================
- Purpose
  - Checks a list of words from the input field to see if any of those words are anagrams of each other.
  - The list of words inputted will be displayed on the Input section of the box.
  - The list of anagrams will be grouped up in the Output section of the box.

=========================
How to RUN program.
=========================
- Prerequisite
  - Make sure to have Java Runtime Environment (JRE) installed on the machine.

- You may run the program by running the .jar file or compiling/running the .java file manually.

=========================
How to USE program.
=========================
- Manual input
  - Type a list of words under the “Enter a list of words:” textfield, separate the words with spaces between.
  - Click the Submit button or simply press your return key to generate the results on the right.

- Textfile input
  - The user may browse through their local directory to choose a text file that contains a list of the words that may contain anagrams.

- Test run
  - Assuming the this program is unzipped from a zip file, there are 4 text files that contains a list of words (anagram/non-anagram) that can be tested with this program.

* * * IMPORTANT NOTE * * *
- There seems to be a format issue from an outside source when a user tries to copy/paste words onto the textfield. I believe that it is a unicode misunderstanding, it is not translating correctly.

- SOLUTION
  - Enter the list of words manually in the text field.
  - Create a text file that contains the list of words and open it through the File Directory.


=========================
Design and Issues
=========================
- I have first decided to write the program to be able to run through a command line or an IDE, but I have then later decided to create a GUI for the program as I have implemented/developed the program earlier than expected.

- The GUI is sectioned off by displaying a textfield where the user may enter their list of words manually, with an addition of a submitting button to display the results, and a file directory button where the user may choose a text file from their local machine that has a list of words to check for anagrams. Input/Output display is sectioned on the other half of the program.

- An issue I have ran into was removing the duplicate words from the output list for some reason, but I may have been overthinking and finally resolved it, as it was an easy fix.

=========================

=========================