package com.myAgeEducation.cbseClass7.maths.tabularquestions;

import com.myAgeEducation.cbseClass7.Question;
import com.myAgeEducation.cbseClass7.maths.utils.CountriesNameUtil;
import com.myAgeEducation.cbseClass7.utils.OptionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class TableQuestionGenerator {
    private static final Random RANDOM = new Random();

    public static Question generateQuestion()
    {
        TableQuestionData data = generate();
        Question question = new Question();
        question.setQuestion(data.questionText);
        OptionUtils.setQuestionOptions(question, data.options);
        question.setAnswer(data.correctAnswer);
        question.setImage(createImageCode(data.tableData));
        return question;
    }

    private static String createImageCode(TableData data)
    {
        StringBuilder code = new StringBuilder("TABLE");
        code.append("_").append(data.scenario.scenarioCode);

        // Store the randomly selected display labels
        for (String displayLabel : data.labels)
        {
            code.append("_").append(displayLabel);
        }

        for (int value : data.values)
        {
            code.append("_").append(value);
        }

        return code.toString();
    }

    public static TableQuestionData generate()
    {
        // Select a random scenario
        TableScenario scenario = SCENARIOS[RANDOM.nextInt(SCENARIOS.length)];

        // Generate values for the table
        TableData tableData = TableDataGenerator.generate(scenario);

        // Select a random question type
        TableQuestionType type = getRandomQuestionType();

        // Generate question and correct answer
        TableQuestionData questionData = generateQuestionData(tableData, type);

        // Generate exactly four options
        questionData.options = generateOptions(questionData);

        return questionData;
    }

    private static TableQuestionType getRandomQuestionType()
    {
        TableQuestionType[] types = TableQuestionType.values();
        return types[RANDOM.nextInt(types.length)];
    }

    private static final TableScenario[] SCENARIOS =
            {
                    // 1
                    new TableScenario(
                            "ROADNETWORK",
                            "The table shows the length of road networks in four different cities.",
                            "City",
                            "Road Network",

                            new String[]
                                    {
                                            "Bangalore",
                                            "Delhi",
                                            "Chennai",
                                            "Mumbai"
                                    },

                            "km",
                            "road network",
                            "road networks",
                            "length of road network",
                            5000,       // minValue
                            30000,      // maxValue
                            500,        // valueStep

                            "Which city has the largest network of roads?",
                            "Which city has the smallest network of roads?",
                            "Which city has the second largest network of roads?",
                            "Which city has the second smallest network of roads?"
                    ),

                    // 2
                    new TableScenario(
                            "AIRPORTS",
                            "The table shows the number of airports "
                                    + "in four different countries.",
                            "Country",
                            "Number of Airports",
                            CountriesNameUtil.getDifferentCountryNames(4),
                            "",
                            "airport",
                            "airports",
                            "number of airports",
                            50,         // minValue
                            500,        // maxValue
                            10,         // valueStep

                            "Which country has the most airports?",
                            "Which country has the fewest airports?",
                            "Which country has the second most airports?",
                            "Which country has the second fewest airports?"
                    ),

                    // 3
                    new TableScenario(
                            "LIBRARIES",
                            "The table shows the number of libraries in four different cities.",
                            "City",
                            "Number of Libraries",

                            new String[]
                                    {
                                            "Delhi",
                                            "Mumbai",
                                            "Chennai",
                                            "Bangalore"
                                    },

                            "",

                            "library",
                            "libraries",
                            "number of libraries",

                            50,         // minValue
                            500,        // maxValue
                            10,         // valueStep

                            "Which city has the most libraries?",
                            "Which city has the fewest libraries?",
                            "Which city has the second most libraries?",
                            "Which city has the second fewest libraries?"
                    ),

                    // 4
                    new TableScenario(
                            "MOUNTAINPEAKS",
                            "The table shows the heights of different mountain peaks.",
                            "Mountain Peak",
                            "Height",
                            new String[]{"Mount Everest", "K2", "Kanchenjunga", "Lhotse"},
                            "m",
                            "peak",
                            "peaks",
                            "height",
                            8000,       // minValue
                            9000,       // maxValue
                            10,         // valueStep
                            "Which peak is the highest?",
                            "Which peak is the lowest?",
                            "Which peak is the second highest?",
                            "Which peak is the second lowest?"
                    ),

                    // 5
                    new TableScenario(
                            "PLANETS",
                            "The table shows the approximate diameters of four planets.",
                            "Planet",
                            "Diameter",
                            new String[]{"Earth", "Mars", "Jupiter", "Saturn"},
                            "km",
                            "planet",
                            "planets",
                            "diameter",
                            6000,       // minValue
                            140000,     // maxValue
                            1000,       // valueStep
                            "Which planet has the largest diameter?",
                            "Which planet has the smallest diameter?",
                            "Which planet has the second largest diameter?",
                            "Which planet has the second smallest diameter?"
                    ),

                    // 6
                    new TableScenario(
                            "CROPPRODUCTION",
                            "The table shows the production of wheat in four different states.",
                            "State",
                            "Wheat Produced",
                            new String[]{"Punjab", "Haryana", "Uttar Pradesh", "Madhya Pradesh"},
                            "tonnes",
                            "state",
                            "states",
                            "amount of wheat produced",
                            15000,      // minValue
                            60000,      // maxValue
                            500,        // valueStep
                            "Which state produced the most wheat?",
                            "Which state produced the least wheat?",
                            "Which state produced the second most wheat?",
                            "Which state produced the second least wheat?"
                    ),

                    // 7
                    new TableScenario(
                            "FORESTAREA",
                            "The table shows the forest area cover in four different Indian states.",
                            "State",
                            "Forest Area",
                            new String[]{"Madhya Pradesh", "Arunachal Pradesh", "Chhattisgarh", "Odisha"},
                            "sq km",
                            "state",
                            "states",
                            "forest area cover",
                            30000,      // minValue
                            80000,      // maxValue
                            1000,       // valueStep
                            "Which state has the largest forest cover?",
                            "Which state has the smallest forest cover?",
                            "Which state has the second largest forest cover?",
                            "Which state has the second smallest forest cover?"
                    ),

                    // 8
                    new TableScenario(
                            "TOWNPOPULATION",
                            "The table shows the population of four different towns.",
                            "Town",
                            "Population",
                            new String[]{"Town A", "Town B", "Town C", "Town D"},
                            "",
                            "town",
                            "towns",
                            "population",
                            10000,      // minValue
                            50000,      // maxValue
                            100,        // valueStep
                            "Which town has the highest population?",
                            "Which town has the lowest population?",
                            "Which town has the second highest population?",
                            "Which town has the second lowest population?"
                    ),

                    // 9
                    new TableScenario(
                            "RIVERLENGTHS",
                            "The table shows the length of four major Indian rivers.",
                            "River",
                            "Length",
                            new String[]{"Ganga", "Godavari", "Krishna", "Yamuna"},
                            "km",
                            "river",
                            "rivers",
                            "length of the river",
                            1300,       // minValue
                            2600,       // maxValue
                            50,         // valueStep
                            "Which river is the longest?",
                            "Which river is the shortest?",
                            "Which river is the second longest?",
                            "Which river is the second shortest?"
                    ),

                    // 10
                    new TableScenario(
                            "STADIUMCAPACITY",
                            "The table shows the seating capacity of four different cricket stadiums.",
                            "Stadium",
                            "Capacity",
                            new String[]{"Narendra Modi Stadium", "Eden Gardens", "Wankhede Stadium", "M. Chinnaswamy Stadium"},
                            "seats",
                            "stadium",
                            "stadiums",
                            "seating capacity",
                            30000,      // minValue
                            140000,     // maxValue
                            1000,       // valueStep
                            "Which stadium has the highest capacity?",
                            "Which stadium has the lowest capacity?",
                            "Which stadium has the second highest capacity?",
                            "Which stadium has the second lowest capacity?"
                    ),

                    // 11
                    new TableScenario(
                            "ANNUALRAINFALL",
                            "The table shows the annual rainfall received by four different cities.",
                            "City",
                            "Annual Rainfall",
                            new String[]{"Mawsynram", "Mumbai", "Chennai", "Delhi"},
                            "mm",
                            "city",
                            "cities",
                            "amount of annual rainfall",
                            500,        // minValue
                            12000,      // maxValue
                            100,        // valueStep
                            "Which city received the highest rainfall?",
                            "Which city received the lowest rainfall?",
                            "Which city received the second highest rainfall?",
                            "Which city received the second lowest rainfall?"
                    ),

                    // 12
                    new TableScenario(
                            "PLANETDISTANCE",
                            "The table shows the approximate distance of four planets from the Sun.",
                            "Planet",
                            "Distance",
                            new String[]{"Mercury", "Venus", "Earth", "Mars"},
                            "million km",
                            "planet",
                            "planets",
                            "distance from the Sun",
                            50,         // minValue
                            250,        // maxValue
                            5,          // valueStep
                            "Which planet is farthest from the Sun?",
                            "Which planet is closest to the Sun?",
                            "Which planet is the second farthest from the Sun?",
                            "Which planet is the second closest to the Sun?"
                    ),

                    // 13
                    new TableScenario(
                            "ANIMALPOPULATION",
                            "The table shows the estimated population of four endangered animals in a forest reserve.",
                            "Animal",
                            "Population",
                            new String[]{"Tiger", "Elephant", "Rhino", "Snow Leopard"},
                            "",
                            "animal",
                            "animals",
                            "estimated population",
                            100,        // minValue
                            3000,       // maxValue
                            50,         // valueStep
                            "Which animal has the highest population?",
                            "Which animal has the lowest population?",
                            "Which animal has the second highest population?",
                            "Which animal has the second lowest population?"
                    ),

                    // 14
                    new TableScenario(
                            "LIBRARYBOOKS",
                            "The table shows the number of books in four different school libraries.",
                            "School",
                            "Number of Books",
                            new String[]{"Greenwood High", "St. Mary's", "Model School", "City Public School"},
                            "books",
                            "school",
                            "schools",
                            "number of books",
                            5000,       // minValue
                            25000,      // maxValue
                            250,        // valueStep
                            "Which school has the most books in its library?",
                            "Which school has the fewest books in its library?",
                            "Which school has the second most books?",
                            "Which school has the second fewest books?"
                    ),

                    // 15
                    new TableScenario(
                            "CRICKETRUNS",
                            "The table shows the total runs scored by four batsmen in a cricket tournament.",
                            "Batsman",
                            "Runs Scored",
                            new String[]{"Virat", "Rohit", "Shubman", "KL Rahul"},
                            "runs",
                            "batsman",
                            "batsmen",
                            "number of runs scored",
                            300,        // minValue
                            900,        // maxValue
                            1,          // valueStep
                            "Which batsman scored the most runs?",
                            "Which batsman scored the fewest runs?",
                            "Which batsman scored the second most runs?",
                            "Which batsman scored the second fewest runs?"
                    ),

                    // 16
                    new TableScenario(
                            "ELECTRICITY",
                            "The table shows the electricity consumed by four families in a month.",
                            "Family",
                            "Units Consumed",
                            new String[]{"Sharma Family", "Verma Family", "Gupta Family", "Singh Family"},
                            "units",
                            "family",
                            "families",
                            "amount of electricity consumed",
                            150,        // minValue
                            800,        // maxValue
                            10,         // valueStep
                            "Which family consumed the most electricity?",
                            "Which family consumed the least electricity?",
                            "Which family consumed the second most electricity?",
                            "Which family consumed the second least electricity?"
                    ),

                    // 17
                    new TableScenario(
                            "ANIMALSPEED",
                            "The table shows the maximum speeds of four different animals.",
                            "Animal",
                            "Top Speed",
                            new String[]{"Cheetah", "Lion", "Gazelle", "Horse"},
                            "km/hr",
                            "animal",
                            "animals",
                            "maximum speed",
                            40,         // minValue
                            120,        // maxValue
                            5,          // valueStep
                            "Which animal is the fastest?",
                            "Which animal is the slowest?",
                            "Which animal is the second fastest?",
                            "Which animal is the second slowest?"
                    ),

                    // 18
                    new TableScenario(
                            "STEPCOUNT",
                            "The table shows the total steps walked by four friends in a week.",
                            "Friend",
                            "Total Steps",
                            new String[]{"Amit", "Rahul", "Sneha", "Priya"},
                            "steps",
                            "friend",
                            "friends",
                            "total number of steps",
                            30000,      // minValue
                            85000,      // maxValue
                            100,        // valueStep
                            "Who walked the most steps?",
                            "Who walked the fewest steps?",
                            "Who walked the second most steps?",
                            "Who walked the second fewest steps?"
                    ),

                    // 19
                    new TableScenario(
                            "GADGETCOST",
                            "The table shows the prices of four different electronic gadgets.",
                            "Gadget",
                            "Price",
                            new String[]{"Smartphone", "Laptop", "Tablet", "Smartwatch"},
                            "rupees",
                            "gadget",
                            "gadgets",
                            "price of the gadget",
                            5000,       // minValue
                            75000,      // maxValue
                            500,        // valueStep
                            "Which gadget is the most expensive?",
                            "Which gadget is the cheapest?",
                            "Which gadget is the second most expensive?",
                            "Which gadget is the second cheapest?"
                    ),

                    // 20
                    new TableScenario(
                            "STATUEHEIGHT",
                            "The table shows the heights of four famous statues in the world.",
                            "Statue",
                            "Height",
                            new String[]{"Statue of Unity", "Spring Temple Buddha", "Laykyun Sekkya", "Statue of Liberty"},
                            "meters",
                            "statue",
                            "statues",
                            "height of the statue",
                            90,         // minValue
                            200,        // maxValue
                            2,          // valueStep
                            "Which statue is the tallest?",
                            "Which statue is the shortest?",
                            "Which statue is the second tallest?",
                            "Which statue is the second shortest?"
                    ),

                    //21
                    new TableScenario(
                            "WATERUSAGE",
                            "The table shows the daily water consumption of four different households.",
                            "Household",
                            "Water Used",
                            new String[]{"House A", "House B", "House C", "House D"},
                            "litres",
                            "household",
                            "households",
                            "amount of water used",
                            400,        // minValue
                            1500,       // maxValue
                            25,         // valueStep
                            "Which household used the most water?",
                            "Which household used the least water?",
                            "Which household used the second most water?",
                            "Which household used the second least water?"
                    ),

                    // 22
                    new TableScenario(
                            "LAKEAREA",
                            "The table shows the surface area of four major lakes in the world.",
                            "Lake",
                            "Surface Area",
                            new String[]{"Caspian Sea", "Lake Superior", "Lake Victoria", "Lake Huron"},
                            "sq km",
                            "lake",
                            "lakes",
                            "surface area",
                            50000,      // minValue
                            400000,     // maxValue
                            1000,       // valueStep
                            "Which lake has the largest surface area?",
                            "Which lake has the smallest surface area?",
                            "Which lake has the second largest surface area?",
                            "Which lake has the second smallest surface area?"
                    ),

                    // 23
                    new TableScenario(
                            "ANIMALLIFESPAN",
                            "The table shows the average lifespan of four different animals.",
                            "Animal",
                            "Average Lifespan",
                            new String[]{"Elephant", "Blue Whale", "Giant Tortoise", "Horse"},
                            "years",
                            "animal",
                            "animals",
                            "average lifespan",
                            25,         // minValue
                            160,        // maxValue
                            5,          // valueStep
                            "Which animal has the longest lifespan?",
                            "Which animal has the shortest lifespan?",
                            "Which animal has the second longest lifespan?",
                            "Which animal has the second shortest lifespan?"
                    ),

                    // 24
                    new TableScenario(
                            "OCEANDEPTH",
                            "The table shows the maximum depth of four major oceans.",
                            "Ocean",
                            "Maximum Depth",
                            new String[]{"Pacific Ocean", "Atlantic Ocean", "Indian Ocean", "Arctic Ocean"},
                            "meters",
                            "ocean",
                            "oceans",
                            "maximum depth",
                            4000,       // minValue
                            11000,      // maxValue
                            100,        // valueStep
                            "Which ocean is the deepest?",
                            "Which ocean is the shallowest?",
                            "Which ocean is the second deepest?",
                            "Which ocean is the second shallowest?"
                    ),

                    //25
                    new TableScenario(
                            "BUILDINGHEIGHT",
                            "The table shows the heights of four of the tallest buildings in the world.",
                            "Building",
                            "Height",
                            new String[]{"Burj Khalifa", "Shanghai Tower", "Abraj Al-Bait", "Ping An Center"},
                            "meters",
                            "building",
                            "buildings",
                            "height of the building",
                            500,        // minValue
                            850,        // maxValue
                            1,          // valueStep
                            "Which building is the tallest?",
                            "Which building is the shortest?",
                            "Which building is the second tallest?",
                            "Which building is the second shortest?"
                    ),

                    // 26
                    new TableScenario(
                            "MONTHLYSAVINGS",
                            "The table shows the amount saved by four friends in a month.",
                            "Friend",
                            "Amount Saved",
                            new String[]{"Arjun", "Kavita", "Sonia", "Tushar"},
                            "rupees",
                            "rupee",
                            "rupees",
                            "amount saved",
                            500,        // minValue
                            5000,       // maxValue
                            50,         // valueStep
                            "Who saved the most money?",
                            "Who saved the least money?",
                            "Who saved the second most money?",
                            "Who saved the second least money?"
                    ),

                    // 27
                    new TableScenario(
                            "STORAGE",
                            "The table shows the storage capacity of four different pen drives.",
                            "Pen Drive",
                            "Storage",
                            new String[]{"Red Drive", "Blue Drive", "Silver Drive", "Black Drive"},
                            "GB",
                            "drive",
                            "drives",
                            "storage capacity",
                            8,          // minValue
                            128,        // maxValue
                            8,          // valueStep
                            "Which drive has the largest storage capacity?",
                            "Which drive has the smallest storage capacity?",
                            "Which drive has the second largest capacity?",
                            "Which drive has the second smallest capacity?"
                    ),

                    // 28
                    new TableScenario(
                            "BOOKPAGES",
                            "The table shows the number of pages in four different storybooks.",
                            "Book Title",
                            "Number of Pages",
                            new String[]{"Jungle Book", "Harry Potter", "Panchatantra", "Treasure Island"},
                            "pages",
                            "book",
                            "books",
                            "number of pages",
                            120,        // minValue
                            600,        // maxValue
                            10,         // valueStep
                            "Which book is the thickest (has the most pages)?",
                            "Which book is the thinnest (has the fewest pages)?",
                            "Which book has the second most pages?",
                            "Which book has the second fewest pages?"
                    ),

                    // 29
                    new TableScenario(
                            "MOVIETIME",
                            "The table shows the duration of four different animated movies.",
                            "Movie",
                            "Duration",
                            new String[]{"Movie A", "Movie B", "Movie C", "Movie D"},
                            "minutes",
                            "movie",
                            "movies",
                            "duration of the movie",
                            80,         // minValue
                            160,        // maxValue
                            5,          // valueStep
                            "Which movie is the longest?",
                            "Which movie is the shortest?",
                            "Which movie is the second longest?",
                            "Which movie is the second shortest?"
                    ),

                    // 30
                    new TableScenario(
                            "CITYDISTANCE",
                            "The table shows the distance of four cities from Delhi.",
                            "City",
                            "Distance",
                            new String[]{"Jaipur", "Chandigarh", "Lucknow", "Ahmedabad"},
                            "km",
                            "city",
                            "cities",
                            "distance from Delhi",
                            250,        // minValue
                            950,        // maxValue
                            10,         // valueStep
                            "Which city is farthest from Delhi?",
                            "Which city is closest to Delhi?",
                            "Which city is the second farthest?",
                            "Which city is the second closest?"
                    ),

                    // 31
                    new TableScenario(
                            "FRUITPRODUCTION",
                            "The table shows the weight of apples harvested from four different trees.",
                            "Tree",
                            "Apples Harvested",
                            new String[]{"Tree 1", "Tree 2", "Tree 3", "Tree 4"},
                            "kg",
                            "tree",
                            "trees",
                            "weight of apples harvested",
                            40,         // minValue
                            250,        // maxValue
                            5,          // valueStep
                            "Which tree produced the highest yield of apples?",
                            "Which tree produced the lowest yield of apples?",
                            "Which tree produced the second highest yield?",
                            "Which tree produced the second lowest yield?"
                    ),

                    // 32
                    new TableScenario(
                            "ATTENDANCE",
                            "The table shows the number of students present in four different classes on Monday.",
                            "Class",
                            "Students Present",
                            new String[]{"Class 5A", "Class 5B", "Class 5C", "Class 5D"},
                            "students",
                            "class",
                            "classes",
                            "number of students present",
                            30,         // minValue
                            50,         // maxValue
                            1,          // valueStep
                            "Which class had the highest attendance?",
                            "Which class had the lowest attendance?",
                            "Which class had the second highest attendance?",
                            "Which class had the second lowest attendance?"
                    ),

                    // 33
                    new TableScenario(
                            "CITYTEMP",
                            "The table shows the maximum temperature recorded in four cities on a summer day.",
                            "City",
                            "Temperature",
                            new String[]{"Nagpur", "Jodhpur", "Ahmedabad", "Lucknow"},
                            "°C",
                            "city",
                            "cities",
                            "maximum temperature",
                            35,         // minValue
                            48,         // maxValue
                            1,          // valueStep
                            "Which city was the hottest?",
                            "Which city was the coolest?",
                            "Which city had the second highest temperature?",
                            "Which city had the second lowest temperature?"
                    ),

                    // 34
                    new TableScenario(
                            "VEHICLESPEED",
                            "The table shows the top speed of four different types of vehicles.",
                            "Vehicle",
                            "Top Speed",
                            new String[]{"Racing Car", "Bullet Train", "Helicopter", "Superbike"},
                            "km/hr",
                            "vehicle",
                            "vehicles",
                            "maximum speed",
                            150,        // minValue
                            450,        // maxValue
                            10,         // valueStep
                            "Which vehicle is the fastest?",
                            "Which vehicle is the slowest?",
                            "Which vehicle is the second fastest?",
                            "Which vehicle is the second slowest?"
                    ),

                    // 35
                    new TableScenario(
                            "ANIMALWEIGHT",
                            "The table shows the weight of four different animals at the city zoo.",
                            "Animal",
                            "Weight",
                            new String[]{"Elephant", "Hippopotamus", "Giraffe", "Rhino"},
                            "kg",
                            "animal",
                            "animals",
                            "weight",
                            800,        // minValue
                            5000,       // maxValue
                            50,         // valueStep
                            "Which animal is the heaviest?",
                            "Which animal is the lightest?",
                            "Which animal is the second heaviest?",
                            "Which animal is the second lightest?"
                    ),

                    // 36
                    new TableScenario(
                            "FRUITSALES",
                            "The table shows the total sales of four different fruits at a shop in one day.",
                            "Fruit",
                            "Total Sales",
                            new String[]{"Mangoes", "Apples", "Grapes", "Oranges"},
                            "rupees",
                            "fruit",
                            "fruits",
                            "total sales amount",
                            1200,       // minValue
                            8500,       // maxValue
                            100,        // valueStep
                            "Which fruit had the highest sales?",
                            "Which fruit had the lowest sales?",
                            "Which fruit had the second highest sales?",
                            "Which fruit had the second lowest sales?"
                    ),

                    // 37
                    new TableScenario(
                            "SOLARENERGY",
                            "The table shows the energy produced by four solar panels in a week.",
                            "Solar Panel",
                            "Energy Produced",
                            new String[]{"Panel A", "Panel B", "Panel C", "Panel D"},
                            "units",
                            "panel",
                            "panels",
                            "amount of energy produced",
                            100,        // minValue
                            500,        // maxValue
                            5,          // valueStep
                            "Which panel produced the most energy?",
                            "Which panel produced the least energy?",
                            "Which panel produced the second most energy?",
                            "Which panel produced the second least energy?"
                    ),

                    // 38
                    new TableScenario(
                            "SPORTSPOINTS",
                            "The table shows the total points scored by four houses on Sports Day.",
                            "House",
                            "Total Points",
                            new String[]{"Red House", "Blue House", "Green House", "Yellow House"},
                            "points",
                            "house",
                            "houses",
                            "total points scored",
                            80,         // minValue
                            250,        // maxValue
                            1,          // valueStep
                            "Which house came first (scored the most points)?",
                            "Which house came last (scored the fewest points)?",
                            "Which house came second?",
                            "Which house came third?"
                    ),

                    // 39
                    new TableScenario(
                            "PARKVISITORS",
                            "The table shows the number of people who visited four different parks on Sunday.",
                            "Park",
                            "Visitors",
                            new String[]{"Central Park", "Deer Park", "Rose Garden", "Children's Park"},
                            "people",
                            "park",
                            "parks",
                            "number of visitors",
                            300,        // minValue
                            2500,       // maxValue
                            50,         // valueStep
                            "Which park had the most visitors?",
                            "Which park had the fewest visitors?",
                            "Which park had the second most visitors?",
                            "Which park had the second fewest visitors?"
                    ),

                    // 40
                    new TableScenario(
                            "APPDOWNLOADS",
                            "The table shows the number of downloads for four different mobile games.",
                            "Game",
                            "Downloads",
                            new String[]{"Game X", "Game Y", "Game Z", "Game W"},
                            "lakhs",
                            "game",
                            "games",
                            "number of downloads",
                            10,         // minValue
                            95,         // maxValue
                            1,          // valueStep
                            "Which game has the most downloads?",
                            "Which game has the fewest downloads?",
                            "Which game has the second most downloads?",
                            "Which game has the second fewest downloads?"
                    ),

                    // 41
                    new TableScenario(
                            "EXAMSCORES",
                            "The table shows the total marks obtained by four students in their final exams.",
                            "Student",
                            "Total Marks",
                            new String[]{"Karan", "Ishani", "Zoya", "Aditya"},
                            "marks",
                            "student",
                            "students",
                            "total marks obtained",
                            350,        // minValue
                            500,        // maxValue
                            1,          // valueStep
                            "Who scored the highest marks?",
                            "Who scored the lowest marks?",
                            "Who scored the second highest marks?",
                            "Who scored the second lowest marks?"
                    ),
            };

    private static TableQuestionData generateQuestionData(TableData data, TableQuestionType type)
    {
        String questionText;
        String correctAnswer;

        switch (type)
        {
            case VALUE:
            {
                int index = RANDOM.nextInt(data.values.length);

                questionText =
                        data.scenario.introduction
                                + " What is the "
                                + data.scenario.valueDescription
                                + " for "
                                + data.getLabel(index)
                                + "?";

                correctAnswer = formatAnswer(data.values[index], data.scenario.unit);
                break;
            }

            case LARGEST:
            {
                int index = getIndexOfLargest(data);
                questionText = data.scenario.introduction + " " + data.scenario.largestQuestion;
                correctAnswer = data.getLabel(index);
                break;
            }

            case SMALLEST:
            {
                int index = getIndexOfSmallest(data);
                questionText = data.scenario.introduction + " " + data.scenario.smallestQuestion;
                correctAnswer = data.getLabel(index);
                break;
            }

            case SECOND_LARGEST:
            {
                int index = getIndexOfSecondLargest(data);
                questionText = data.scenario.introduction + " " + data.scenario.secondLargestQuestion;
                correctAnswer = data.getLabel(index);
                break;
            }

            case SECOND_SMALLEST:
            {
                int index = getIndexOfSecondSmallest(data);
                questionText = data.scenario.introduction + " " + data.scenario.secondSmallestQuestion;
                correctAnswer = data.getLabel(index);
                break;
            }

            case MORE_THAN:
            {
                int[] indices = getTwoDifferentIndices(data);

                int larger = indices[0];
                int smaller = indices[1];

                if (data.values[larger] < data.values[smaller])
                {
                    int temp = larger;
                    larger = smaller;
                    smaller = temp;
                }

                String comparisonUnit = data.scenario.unit;
                String question;

                if (comparisonUnit == null || comparisonUnit.trim().isEmpty())
                {
                    if (data.scenario.scenarioCode.contains("POPULATION") || data.scenario.valueDescription.contains("population"))
                    {
                        String popItem = data.scenario.pluralItemName.equalsIgnoreCase("towns") || data.scenario.pluralItemName.equalsIgnoreCase("cities") ? "people" : data.scenario.pluralItemName;
                        question = " How many more " + popItem + " does " + data.getLabel(larger) + " have than " + data.getLabel(smaller) + "?";
                    }
                    else
                    {
                        question = " How many more " + data.scenario.pluralItemName + " does " + data.getLabel(larger) + " have than " + data.getLabel(smaller) + "?";
                    }
                }
                else if (comparisonUnit.equalsIgnoreCase("minutes") || comparisonUnit.equalsIgnoreCase("hours") || comparisonUnit.equalsIgnoreCase("days") || comparisonUnit.equalsIgnoreCase("years"))
                {
                    question = " How many " + comparisonUnit + " longer is " + data.getLabel(larger) + " than " + data.getLabel(smaller) + "?";
                }
                else if (comparisonUnit.equalsIgnoreCase("meters") || comparisonUnit.equalsIgnoreCase("m"))
                {
                    if (data.scenario.scenarioCode.contains("DEPTH") || data.scenario.valueDescription.contains("depth"))
                    {
                        question = " How many " + comparisonUnit + " deeper is " + data.getLabel(larger) + " than " + data.getLabel(smaller) + "?";
                    }
                    else
                    {
                        question = " How many " + comparisonUnit + " taller is " + data.getLabel(larger) + " than " + data.getLabel(smaller) + "?";
                    }
                }
                else if (comparisonUnit.equalsIgnoreCase("km") && (data.scenario.scenarioCode.contains("DIAMETER") || data.scenario.valueDescription.contains("diameter")))
                {
                    question = " How many " + comparisonUnit + " larger in diameter is " + data.getLabel(larger) + " than " + data.getLabel(smaller) + "?";
                }
                else if (comparisonUnit.equalsIgnoreCase("GB") || comparisonUnit.equalsIgnoreCase("km")
                        || comparisonUnit.equalsIgnoreCase("kg") || comparisonUnit.equalsIgnoreCase("rupees")
                        || comparisonUnit.equalsIgnoreCase("litres") || comparisonUnit.equalsIgnoreCase("ml")
                        || comparisonUnit.equalsIgnoreCase("tonnes") || comparisonUnit.equalsIgnoreCase("sq km")
                        || comparisonUnit.equalsIgnoreCase("mm"))
                {
                    question = " How much more " + comparisonUnit + " does " + data.getLabel(larger) + " have than " + data.getLabel(smaller) + "?";
                }
                else
                {
                    question = " How many more " + comparisonUnit + " does " + data.getLabel(larger) + " have than " + data.getLabel(smaller) + "?";
                }

                questionText = data.scenario.introduction + question;

                correctAnswer =
                        formatAnswer(
                                data.values[larger]
                                        - data.values[smaller],
                                data.scenario.unit);

                break;
            }

            case FEWER_THAN:
            {
                int[] indices = getTwoDifferentIndices(data);
                int smaller = indices[0];
                int larger = indices[1];

                if (data.values[smaller] > data.values[larger])
                {
                    int temp = smaller;
                    smaller = larger;
                    larger = temp;
                }

                String comparisonUnit = data.scenario.unit;
                String question;

                if (comparisonUnit == null || comparisonUnit.trim().isEmpty())
                {
                    if (data.scenario.scenarioCode.contains("POPULATION") || data.scenario.valueDescription.contains("population"))
                    {
                        String popItem = data.scenario.pluralItemName.equalsIgnoreCase("towns") || data.scenario.pluralItemName.equalsIgnoreCase("cities") ? "people" : data.scenario.pluralItemName;
                        question = " How many fewer " + popItem + " does " + data.getLabel(smaller) + " have than " + data.getLabel(larger) + "?";
                    }
                    else
                    {
                        question = " How many fewer " + data.scenario.pluralItemName + " does " + data.getLabel(smaller) + " have than " + data.getLabel(larger) + "?";
                    }
                }
                else if (comparisonUnit.equalsIgnoreCase("minutes") || comparisonUnit.equalsIgnoreCase("hours") || comparisonUnit.equalsIgnoreCase("days") || comparisonUnit.equalsIgnoreCase("years"))
                {
                    question = " How many " + comparisonUnit + " shorter is " + data.getLabel(smaller) + " than " + data.getLabel(larger) + "?";
                }
                else if (comparisonUnit.equalsIgnoreCase("meters") || comparisonUnit.equalsIgnoreCase("m"))
                {
                    if (data.scenario.scenarioCode.contains("DEPTH") || data.scenario.valueDescription.contains("depth"))
                    {
                        question = " How many " + comparisonUnit + " shallower is " + data.getLabel(smaller) + " than " + data.getLabel(larger) + "?";
                    }
                    else
                    {
                        question = " How many " + comparisonUnit + " shorter is " + data.getLabel(smaller) + " than " + data.getLabel(larger) + "?";
                    }
                }
                else if (comparisonUnit.equalsIgnoreCase("km") && (data.scenario.scenarioCode.contains("DIAMETER") || data.scenario.valueDescription.contains("diameter")))
                {
                    question = " How many " + comparisonUnit + " smaller in diameter is " + data.getLabel(smaller) + " than " + data.getLabel(larger) + "?";
                }
                else if (comparisonUnit.equalsIgnoreCase("GB") || comparisonUnit.equalsIgnoreCase("km")
                        || comparisonUnit.equalsIgnoreCase("kg") || comparisonUnit.equalsIgnoreCase("rupees")
                        || comparisonUnit.equalsIgnoreCase("litres") || comparisonUnit.equalsIgnoreCase("ml")
                        || comparisonUnit.equalsIgnoreCase("tonnes") || comparisonUnit.equalsIgnoreCase("sq km")
                        || comparisonUnit.equalsIgnoreCase("mm"))
                {
                    question = " How much fewer " + comparisonUnit + " does " + data.getLabel(smaller) + " have than " + data.getLabel(larger) + "?";
                }
                else
                {
                    question = " How many fewer " + comparisonUnit + " does " + data.getLabel(smaller) + " have than " + data.getLabel(larger) + "?";
                }

                questionText = data.scenario.introduction + question;

                correctAnswer =
                        formatAnswer(
                                data.values[larger]
                                        - data.values[smaller],
                                data.scenario.unit);

                break;
            }

            case DIFFERENCE:
            {
                int[] indices = getTwoDifferentIndices(data);
                int first = indices[0];
                int second = indices[1];
                int difference = Math.abs(data.values[first] - data.values[second]);

                questionText =
                        data.scenario.introduction
                                + " What is the difference in the "
                                + data.scenario.valueDescription
                                + " between "
                                + data.getLabel(first)
                                + " and "
                                + data.getLabel(second)
                                + "?";

                correctAnswer =
                        formatAnswer(
                                difference,
                                data.scenario.unit);

                break;
            }

            case TOTAL_TWO:
            {
                int[] indices =
                        getTwoDifferentIndices(data);

                int first = indices[0];
                int second = indices[1];

                int total =
                        data.values[first]
                                + data.values[second];

                questionText =
                        data.scenario.introduction
                                + " What is the total "
                                + data.scenario.valueDescription
                                + " for "
                                + data.getLabel(first)
                                + " and "
                                + data.getLabel(second)
                                + " altogether?";

                correctAnswer =
                        formatAnswer(
                                total,
                                data.scenario.unit);

                break;
            }

            case TOTAL_ALL:
            {
                int total = 0;
                for (int value : data.values)
                {
                    total += value;
                }

                questionText = data.scenario.introduction + " What is the total " + data.scenario.valueDescription + " altogether?";
                correctAnswer = formatAnswer(total, data.scenario.unit);
                break;
            }

            default:
                throw new IllegalArgumentException("Unknown table question type: " + type);
        }

        return new TableQuestionData(data, type, questionText, correctAnswer);
    }

    private static int[] getTwoDifferentIndices(TableData data)
    {
        int firstIndex = RANDOM.nextInt(data.values.length);
        int secondIndex;

        do
        {
            secondIndex = RANDOM.nextInt(data.values.length);
        }
        while (secondIndex == firstIndex);

        return new int[]
                {
                        firstIndex,
                        secondIndex
                };
    }

    private static String formatAnswer(int value, String unit)
    {
        if (unit == null || unit.trim().isEmpty())
        {
            return String.valueOf(value);
        }

        return value + " " + unit;
    }

    private static int[] getTwoDifferentIndexes(TableData data)
    {
        int first =
                RANDOM.nextInt(
                        data.values.length);

        int second;

        do
        {
            second =
                    RANDOM.nextInt(
                            data.values.length);
        }
        while (second == first);

        return new int[]
                {
                        first,
                        second
                };
    }

    private static int getIndexOfLargest(TableData data)
    {
        int index = 0;

        for (int i = 1; i < data.values.length; i++)
        {
            if (data.values[i] > data.values[index])
            {
                index = i;
            }
        }

        return index;
    }

    private static int getIndexOfSmallest(TableData data)
    {
        int index = 0;

        for (int i = 1; i < data.values.length; i++)
        {
            if (data.values[i] < data.values[index])
            {
                index = i;
            }
        }

        return index;
    }

    private static int getIndexOfSecondLargest(TableData data)
    {
        int largest = -1;
        int secondLargest = -1;

        for (int i = 0; i < data.values.length; i++)
        {
            if (largest == -1 || data.values[i] > data.values[largest])
            {
                secondLargest = largest;
                largest = i;
            }
            else if (secondLargest == -1 || data.values[i] > data.values[secondLargest])
            {
                secondLargest = i;
            }
        }

        return secondLargest;
    }

    private static int getIndexOfSecondSmallest(TableData data)
    {
        int smallest = -1;
        int secondSmallest = -1;

        for (int i = 0; i < data.values.length;i++)
        {
            if (smallest == -1 || data.values[i] < data.values[smallest])
            {
                secondSmallest = smallest;
                smallest = i;
            }
            else if (secondSmallest == -1 || data.values[i] < data.values[secondSmallest])
            {
                secondSmallest = i;
            }
        }

        return secondSmallest;
    }

    private static String[] generateOptions(TableQuestionData questionData)
    {
        TableQuestionType type = questionData.type;

        switch (type)
        {
            // LABEL-BASED OPTIONS
            case LARGEST:
            case SMALLEST:
            case SECOND_LARGEST:
            case SECOND_SMALLEST:
                return generateLabelOptions(questionData);

            // NUMERIC OPTIONS
            case VALUE:
            case MORE_THAN:
            case FEWER_THAN:
            case DIFFERENCE:
            case TOTAL_TWO:
            case TOTAL_ALL:
                return generateNumberOptions(questionData);

            default:
                throw new IllegalArgumentException("Unknown table question type: " + type);
        }
    }

    private static String[] generateLabelOptions(TableQuestionData questionData)
    {
        List<String> options = new ArrayList<>();
        Collections.addAll(options, questionData.tableData.labels);
        Collections.shuffle(options);
        return options.toArray(new String[0]);
    }

    private static String[] generateNumberOptions(TableQuestionData questionData)
    {
        TableScenario scenario =
                questionData.tableData.scenario;

        String unit =
                scenario.unit;

        int correctValue =
                Integer.parseInt(
                        questionData.correctAnswer
                                .replace(unit, "")
                                .trim());

        Set<Integer> values =
                new LinkedHashSet<>();

        values.add(correctValue);

        int step =
                scenario.valueStep;

        int[] offsets =
                {
                        -step,
                        step,
                        -2 * step,
                        2 * step,
                        -3 * step,
                        3 * step
                };

        List<Integer> offsetList =
                new ArrayList<>();

        for (int offset : offsets)
        {
            offsetList.add(offset);
        }

        Collections.shuffle(offsetList);

        for (int offset : offsetList)
        {
            int optionValue =
                    correctValue + offset;

            if (optionValue > 0)
            {
                values.add(optionValue);
            }

            if (values.size() == 4)
            {
                break;
            }
        }

        // Safety fallback
        while (values.size() < 4)
        {
            int optionValue =
                    correctValue
                            + (RANDOM.nextInt(10) + 1)
                            * step;

            values.add(optionValue);
        }

        List<String> options =
                new ArrayList<>();

        for (int value : values)
        {
            options.add(
                    formatAnswer(
                            value,
                            unit));
        }

        Collections.shuffle(options);

        return options.toArray(
                new String[0]);
    }
}
