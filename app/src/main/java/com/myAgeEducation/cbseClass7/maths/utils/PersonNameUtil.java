package com.myAgeEducation.cbseClass7.maths.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PersonNameUtil
{
    private static final Random RANDOM = new Random();

    private static final String[] MALE_NAMES =
            {
                    "Aarav", "Aayush", "Abhay", "Aditya", "Ajay", "Akash", "Amar", "Amit", "Amol", "Animesh", "Ankur", "Anthony", "Arjun", "Arman", "Arnab",
                    "Aman", "Amrit", "Ananth", "Anil", "Anirudh", "Ankit",
                    "Arun", "Ashish", "Atul", "Ayan",
                    "Benny", "Bijo", "Biman", "Binay",
                    "Dev", "Deb", "Deepti", "Dheeraj", "Dinesh",
                    "Ganesh", "Gaurav", "Gautam", "Giridhar", "Girish", "Gopal", "Gyanesh",
                    "Hari", "Harish", "Hitesh", "Hrithik",
                    "Ishana", "Iswar", "Irfan",
                    "James", "Jitendra", "Jitender", "Jitin", "Johny", "Joseph",
                    "Kabir", "Kamal", "Kapil", "Karan", "Kartik", "Kavita", "Kavitha", "Kiran", "Kishan", "Kripa", "Krishna", "Kunal",
                    "Madhav", "Magesh", "Mahesh", "Manish", "Manjunath", "Manoj", "Manu", "Mohan", "Mohit", "Mukesh",
                    "Nagaraj", "Nand", "Nataraj", "Naveen", "Nilay", "Nitin",
                    "Om", "Omprakash", "Omkar",
                    "Pavan", "Peter", "Piyush", "Pradeep", "Prabhu", "Prasanth", "Pratap", "Praveen", "Pronit",
                    "Raghupathi", "Rahul", "Rajat", "Rajeev", "Rajnish", "Ram", "Raman", "Ramesh",   "Ranjeet", "Ravi", "Rohan", "Rupam",
                    "Sachin", "Sai", "Sandeep", "Sanjay", "Sanjeev", "Satya", "Shashi", "Srihari", "Suresh", "Sohan", "Sridhar", "Srikanth", "Sunil",  "Sudeep", "Sumit",
                    "Tushar",
                    "Vasu",  "Vijay", "Vikram", "Vinay", "Vinil", "Vinod", "Virat", "Vivaan"
            };

    private static final String[] FEMALE_NAMES =
            {
                    "Aditi", "Anika", "Anita", "Anjali", "Anu", "Arushi", "Asha",
                    "Disha",
                    "Gauri", "Gita", "Geeta",
                    "Joya", "Jyoti",
                    "Kamala", "Karishma", "Karuna", "Kavya", "Karina", "Kavitha",
                    "Lakshmi",
                    "Manju", "Meena", "Meenu", "Meera", "Mita", "Megha", "Mihika",
                    "Namita", "Nandita", "Neha", "Nisha", "Nita", "Neeta",
                    "Pooja", "Poonam", "Preeti", "Priya", "Priyanka", "Pushpa",
                    "Rita", "Ritu", "Riya",
                    "Sanjana", "Sanju", "Saniya", "Shruti", "Simran", "Sita", "Sneha", "Sonia", "Sudha", "Sunita", "Sunitha", "Sushma",
                    "Veena", "Vineetha"
            };


    public static String getOneName()
    {
        if (RANDOM.nextBoolean())
        {
            return getMaleName();
        }

        return getFemaleName();
    }

    public static String getMaleName()
    {
        return MALE_NAMES[RANDOM.nextInt(MALE_NAMES.length)];
    }

    public static String getFemaleName()
    {
        return FEMALE_NAMES[RANDOM.nextInt(FEMALE_NAMES.length)];
    }

    public static String[] getOneMaleAndOneFemaleName()
    {
        return new String[]
                {
                        getMaleName(),
                        getFemaleName()
                };
    }


    public static String[] getOneFemaleAndOneMaleName()
    {
        return new String[]
                {
                        getFemaleName(),
                        getMaleName()
                };
    }

    public static String[] getDifferentNames(int count)
    {
        String[] all_names = new String[MALE_NAMES.length + FEMALE_NAMES.length];
        System.arraycopy(MALE_NAMES, 0, all_names, 0, MALE_NAMES.length);
        System.arraycopy(FEMALE_NAMES, 0, all_names, MALE_NAMES.length, FEMALE_NAMES.length);
        List<String> list = Arrays.asList(all_names);
        Collections.shuffle(list);
        return list.subList(0, count).toArray(new String[count]);
    }

    public static String[] getDifferentMaleNames(int count)
    {
        String[] all_names = new String[MALE_NAMES.length];
        System.arraycopy(MALE_NAMES, 0, all_names, 0, MALE_NAMES.length);
        List<String> list = Arrays.asList(all_names);
        Collections.shuffle(list);
        return list.subList(0, count).toArray(new String[count]);
    }

    public static String[] getDifferentFemaleNames(int count)
    {
        String[] all_names = new String[FEMALE_NAMES.length];
        System.arraycopy(FEMALE_NAMES, 0, all_names, 0, FEMALE_NAMES.length);
        List<String> list = Arrays.asList(all_names);
        Collections.shuffle(list);
        return list.subList(0, count).toArray(new String[count]);
    }

    private PersonNameUtil()
    {
        // Prevent object creation
    }
}
