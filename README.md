# Boxer Challenge Notes  
Create an Android application that displays a list of email addresses and fulfills the requirements and specs listed below, and demonstrates your ability to code.


## Requirements:  
* Application that displays email addresses 
* Activity and ListFragment  
* Has an Actionbar  
* “ADD” menu item displays a DialogFragment with an EditText  
* “Ok” DialogFragment button adds the email entry to the list  
* The list should stay sorted with each addition  
* Tapping an item should fire an email intent with the selected email as the “To:”  
* Orientation changes don’t lose data  
* Orientation changes don’t cause crashes  
* Attempting to add an existing email address displays an AlertDialog  
* AlertDialog informs the user the email already exists  
* Duplicate emails are not added  
* All Strings are localized, nothing hardcoded  
* Empty emails are not added  
* List persists throughout the Activity/Fragment lifecycle until they’re destroyed  
* Leaving to home screen and reopening app should not lose data or list position  
* Project well documented with comments and JavaDoc  
* Project is thoroughly tested  
* All Requirements/Specifications met  


## Specifications:    
* Email addresses displayed in ascending order  
* Starts with fifteen (15) random email addresses in the list  
* Supports any number of email addresses  
* Has one (1) menu item – “ADD”  
* DialogFragment EditText has hint of “Enter an e-mail address”  
* DialogFragment has two (2) buttons – “Cancel” and “Ok”  
* Email addresses should be horizontally centered  
* Min SDK 14  
* Target SDK 21  
* Application Theme set to Theme.AppCompat.Light  


# Problem solving discussion:  
## Data Structures:  
Initially, I wanted to use a TreeSet to hold all of the email String because it would self-sort. However, when I was building a custom Adapter to handle Sets I realized that because of the inability to index in to a Set, polling the email addresses to populate the ListView would become very inefficient the farther in to the List you got. (I’ve included that SetAdapter, which actually works alright, in misc/).  
I migrated to using an ArrayList to be able to index and handled the sorting myself. To do that, I wrapped an ArrayList in a class called AlphabeticalArrayList. It overrides the #add() and #addAll() methods to insert a String in the correct alphabetical location up front, instead of sorting after the list is populated. To accomplish this, the #add*() methods defer to a #sortedAdd() method, which calls a binary search to determine where the passed String would be, if it existed in the List. I feel like this is good compromise on being able to sort, add data, and retrieve data efficiently.

## Class Hierarchy:  
Because this is a relatively simple project, I left the hierarchy flat and didn’t try to coerce it into a MVC or other pattern. I did not design any parent classes; there is some inheritance of existing classes to modify their behavior.
Fragment callback Interfaces are defined as inner classes in their respective Fragments for organizational purposes.
Some anonymous inner classes were utilized for onClick handling.

## Persisting Data:  
Since the amount of data I’m dealing with is relatively small, I made use of the savedInstanceState Bundle and methods (#putStringArrayList(), notably) to persist the list of emails through Activity recreations. Other options that I considered for this included a headless data Fragment (set to retainInstanceState) or writing out the List to an SQLite Database and reading it back in on-demand. 

## Documentation and Testing:  
Code is verbosely commented with JavaDoc, block, and line comments. Some tests were written against the AlphabeticalArrayList class to verify its logic. Additionally, live testing was performed on two Android devices, a tablet and phone, running 5.1.1 and 4.4 respectively.
