package com.myAgeEducation.cbseClass7.maths.datetimecalendar;

import com.myAgeEducation.cbseClass7.Question;
import com.myAgeEducation.cbseClass7.utils.OptionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class TimeConceptQuestionGenerator {
    private static final Random RANDOM = new Random();

    public static Question generateQuestion() {
        int type = RANDOM.nextInt(6);
        if (type == 0) {
            return generateStaticConceptQuestion();
        } else if (type == 1) {
            return generateConversionQuestion();
        } else if (type == 2) {
            return generateTimeArithmeticQuestion();
        } else if (type == 3) {
            return TimeStoryQuestionGenerator.generateQuestion();
        } else if (type == 4) {
            return generateFormatConversionQuestion();
        } else {
            return generateTimeElapsedQuestion();
        }
    }

    private static Question generateTimeElapsedQuestion() {
        int startHour, startMin, endHour, endMin;
        boolean isAM;

        int rand = RANDOM.nextInt(10);
        if (rand < 2) { // 01:15 p.m. to 01:42 p.m.
            startHour = 1; startMin = 15; endHour = 1; endMin = 42; isAM = false;
        } else if (rand < 4) { // 03:18 p.m. to 08:18 p.m.
            startHour = 3; startMin = 18; endHour = 8; endMin = 18; isAM = false;
        } else if (rand < 6) { // 09:15 a.m. to 11:30 a.m.
            startHour = 9; startMin = 15; endHour = 11; endMin = 30; isAM = true;
        } else {
            isAM = RANDOM.nextBoolean();
            startHour = 1 + RANDOM.nextInt(10);
            startMin = RANDOM.nextInt(60);
            int durationMin = 10 + RANDOM.nextInt(300); // 10m to 5h
            int totalStartMin = startHour * 60 + startMin;
            int totalEndMin = totalStartMin + durationMin;
            
            endHour = (totalEndMin / 60);
            endMin = totalEndMin % 60;
            
            if (endHour > 12) {
                return generateTimeElapsedQuestion(); 
            }
        }

        String suffix = isAM ? " a.m." : " p.m.";
        String startTimeStr = String.format(Locale.US, "%02d:%02d%s", startHour, startMin, suffix);
        String endTimeStr = String.format(Locale.US, "%02d:%02d%s", endHour, endMin, suffix);

        int durationTotalMin = (endHour * 60 + endMin) - (startHour * 60 + startMin);
        int h = durationTotalMin / 60;
        int m = durationTotalMin % 60;

        String correctAnswer;
        if (h > 0 && m > 0) {
            correctAnswer = h + " hours " + m + " minutes";
        } else if (h > 0) {
            correctAnswer = h + " hours";
        } else {
            correctAnswer = m + " minutes";
        }

        Question question = new Question();
        question.setQuestion("Find the time elapsed between the given time periods.\n" + startTimeStr + " to " + endTimeStr);
        question.setAnswer(correctAnswer);

        List<String> options = new ArrayList<>();
        options.add(correctAnswer);
        options.add((h + 1) + " hours " + m + " minutes");
        options.add(h + " hours " + (m + 15) % 60 + " minutes");
        options.add((h > 0 ? h - 1 : h + 2) + " hours " + (m + 5) % 60 + " minutes");

        for (int i = 0; i < options.size(); i++) {
            if (options.get(i).startsWith("0 hours ")) {
                options.set(i, options.get(i).replace("0 hours ", ""));
            }
        }

        Collections.shuffle(options);
        OptionUtils.setQuestionOptions(question, options.toArray(new String[0]));
        return question;
    }

    private static Question generateFormatConversionQuestion() {
        int hour;
        int minute;

        if (RANDOM.nextInt(10) < 6) {
            int[][] userExamples = {{5, 30}, {11, 55}, {14, 30}, {17, 30}, {21, 35}, {8, 35}};
            int[] choice = userExamples[RANDOM.nextInt(userExamples.length)];
            hour = choice[0];
            minute = choice[1];
        } else {
            hour = RANDOM.nextInt(24);
            minute = RANDOM.nextInt(60);
        }

        boolean to24Hour = RANDOM.nextBoolean();

        String suffix = hour < 12 ? " a.m." : " p.m.";
        int displayHour = (hour % 12 == 0) ? 12 : hour % 12;
        String twelveHourTime = String.format(Locale.US, "%02d:%02d%s", displayHour, minute, suffix);
        String twentyFourHourTime = String.format(Locale.US, "%02d:%02d hours", hour, minute);

        Question question = new Question();
        List<String> options = new ArrayList<>();

        if (to24Hour) {
            question.setQuestion("Time in 12-hour format: " + twelveHourTime + "\nTime in 24-hour format: _______");
            question.setAnswer(twentyFourHourTime);
            options.add(twentyFourHourTime);
            options.add(String.format(Locale.US, "%02d:%02d hours", (hour + 12) % 24, minute));
            options.add(String.format(Locale.US, "%02d:%02d hours", hour, (minute + 30) % 60));
            int wrongHour = hour >= 12 ? hour - 12 : hour + 12;
            options.add(String.format(Locale.US, "%02d:%02d hours", wrongHour, minute));
        } else {
            question.setQuestion("Time in 24-hour format: " + twentyFourHourTime + "\nTime in 12-hour format: _______");
            question.setAnswer(twelveHourTime);
            options.add(twelveHourTime);
            String otherSuffix = hour < 12 ? " p.m." : " a.m.";
            options.add(String.format(Locale.US, "%02d:%02d%s", displayHour, minute, otherSuffix));
            int otherHour = (displayHour % 12) + 1;
            options.add(String.format(Locale.US, "%02d:%02d%s", otherHour, minute, suffix));
            options.add(String.format(Locale.US, "%02d:%02d%s", displayHour, (minute + 30) % 60, suffix));
        }

        List<String> uniqueOptions = new ArrayList<>();
        for (String opt : options) {
            if (!uniqueOptions.contains(opt)) {
                uniqueOptions.add(opt);
            }
        }
        while (uniqueOptions.size() < 4) {
            if (to24Hour) {
                uniqueOptions.add(String.format(Locale.US, "%02d:%02d hours", RANDOM.nextInt(24), RANDOM.nextInt(60)));
            } else {
                int h = RANDOM.nextInt(12) + 1;
                String s = RANDOM.nextBoolean() ? " a.m." : " p.m.";
                uniqueOptions.add(String.format(Locale.US, "%02d:%02d%s", h, RANDOM.nextInt(60), s));
            }
        }

        Collections.shuffle(uniqueOptions);
        OptionUtils.setQuestionOptions(question, uniqueOptions.toArray(new String[0]));
        return question;
    }

    private static Question generateStaticConceptQuestion() {
        String[][] concepts = {
            {"The short hand in a clock is called _____ hand", "hour", "minute", "second", "fast"},
            {"The ______ hand in a clock moves faster", "second", "hour", "minute", "short"},
            {"The minute hand takes one _____ to go around the clock once.", "hour", "minute", "day", "second"},
            {"A leap year has ______ days.", "366", "365", "364", "360"},
            {"There are ______ months in a year.", "12", "10", "11", "13"},
            {"The long hand in a clock is called the _______ hand.", "minute", "hour", "second", "slow"},
            {"12:00 in the night is called _______.", "midnight", "noon", "morning", "evening"},
            {"12:00 in the day is called _______.", "noon", "midnight", "a.m.", "p.m."},
            {"The time from midnight to 12 noon is called _______.", "a.m.", "p.m.", "noon", "midnight"},
            {"The time from 12 noon to midnight is called _______.", "p.m.", "a.m.", "noon", "morning"}
        };
        int idx = RANDOM.nextInt(concepts.length);
        String[] item = concepts[idx];
        String questionText = item[0];
        String correctAnswer = item[1];
        List<String> options = new ArrayList<>(Arrays.asList(item[1], item[2], item[3], item[4]));
        Collections.shuffle(options);

        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(correctAnswer);
        OptionUtils.setQuestionOptions(question, options.toArray(new String[0]));
        return question;
    }

    private static Question generateConversionQuestion() {
        int subType = RANDOM.nextInt(3);
        String questionText;
        String correctAnswer;
        List<String> options = new ArrayList<>();

        if (subType == 0) { // Hours and half to minutes
            int hours = RANDOM.nextInt(5) + 1;
            boolean hasHalf = RANDOM.nextBoolean();
            int totalMinutes = hours * 60 + (hasHalf ? 30 : 0);
            questionText = hours + (hasHalf ? " and half" : "") + " hour = _______ minutes";
            correctAnswer = String.valueOf(totalMinutes);
            options.add(correctAnswer);
            options.add(String.valueOf(totalMinutes + 30));
            options.add(String.valueOf(totalMinutes - 30));
            options.add(String.valueOf(hours * 60));
        } else if (subType == 1) { // Minutes to seconds
            int minutes = (RANDOM.nextInt(10) + 1) * 5;
            int totalSeconds = minutes * 60;
            questionText = minutes + " minutes = ______ seconds";
            correctAnswer = String.valueOf(totalSeconds);
            options.add(correctAnswer);
            options.add(String.valueOf(totalSeconds + 60));
            options.add(String.valueOf(totalSeconds - 60));
            options.add(String.valueOf(minutes * 100));
        } else { // Days to hours
            int days = RANDOM.nextInt(5) + 1;
            int totalHours = days * 24;
            questionText = days + " days = ______ hours";
            correctAnswer = String.valueOf(totalHours);
            options.add(correctAnswer);
            options.add(String.valueOf(totalHours + 12));
            options.add(String.valueOf(totalHours - 12));
            options.add(String.valueOf(days * 12));
        }

        Collections.shuffle(options);
        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(correctAnswer);
        OptionUtils.setQuestionOptions(question, options.toArray(new String[0]));
        return question;
    }

    private static Question generateTimeArithmeticQuestion() {
        int hoursBefore = RANDOM.nextInt(5) + 1;
        int targetHour = 12 - hoursBefore;
        String questionText = String.format(Locale.US, "%d hours before 12 noon is %d:00 _____", hoursBefore, targetHour);
        String correctAnswer = "a.m.";
        List<String> options = new ArrayList<>(Arrays.asList("a.m.", "p.m.", "noon", "midnight"));
        Collections.shuffle(options);
        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(correctAnswer);
        OptionUtils.setQuestionOptions(question, options.toArray(new String[0]));
        return question;
    }
}
