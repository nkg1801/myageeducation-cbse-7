package com.myAgeEducation.cbseClass7.maths.datetimecalendar;

import com.myAgeEducation.cbseClass7.Question;
import com.myAgeEducation.cbseClass7.maths.utils.PersonNameUtil;
import com.myAgeEducation.cbseClass7.utils.OptionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class TimeStoryQuestionGenerator {
    private static final Random RANDOM = new Random();

    private static class StoryTemplate {
        String questionPattern;
        int minStartHour;
        int maxStartHour;
        String forceStartSuffix; // null if random
        int minDurationMin;
        int maxDurationMin;
        boolean forceEndPM;

        StoryTemplate(String pattern, int minH, int maxH, String suffix, int minD, int maxD, boolean endPM) {
            this.questionPattern = pattern;
            this.minStartHour = minH;
            this.maxStartHour = maxH;
            this.forceStartSuffix = suffix;
            this.minDurationMin = minD;
            this.maxDurationMin = maxD;
            this.forceEndPM = endPM;
        }
    }

    private static final StoryTemplate[] TEMPLATES = {
        new StoryTemplate("A movie that started at {start} got over at {end}. How long was the movie?", 1, 9, null, 135, 165, false),
        new StoryTemplate("A train started from a station at {start} and reached its destination at {end}. Find the duration of the journey.", 6, 9, " a.m.", 180, 420, false),
        new StoryTemplate("{name} started doing homework at {start} and finished at {end}. How much time did {name} take?", 4, 6, " p.m.", 30, 80, true),
        new StoryTemplate("Mother started cooking lunch at {start} and finished at {end}. How long did she take to cook?", 10, 11, " a.m.", 75, 95, false),
        new StoryTemplate("{name} goes for swimming practice. If practice starts at {start} and ends at {end}, what is the duration of the practice?", 6, 7, null, 45, 90, false),
        new StoryTemplate("An annual school function started at {start} and concluded at {end}. How long did the function last?", 9, 9, " a.m.", 210, 210, true),
        new StoryTemplate("{name} left home at {start} to visit a grandmother. If {name} reached at {end}, how long was the travel time?", 8, 8, " a.m.", 165, 165, false),
        new StoryTemplate("{name} started a project at {start} and finished it at {end}. How long did {name} work on the project?", 2, 4, " p.m.", 80, 110, true)
    };

    public static Question generateQuestion() {
        StoryTemplate template = TEMPLATES[RANDOM.nextInt(TEMPLATES.length)];
        String name = PersonNameUtil.getOneName();

        int startHour = template.minStartHour + (template.maxStartHour > template.minStartHour ? RANDOM.nextInt(template.maxStartHour - template.minStartHour + 1) : 0);
        int startMin;
        if (template.questionPattern.contains("cooking") || template.questionPattern.contains("train")) {
            startMin = RANDOM.nextInt(6) * 10;
        } else {
            startMin = (RANDOM.nextInt(4)) * 15;
        }
        
        String sSuffix = template.forceStartSuffix != null ? template.forceStartSuffix : (RANDOM.nextBoolean() ? " a.m." : " p.m.");
        int durationMin = template.minDurationMin + (template.maxDurationMin > template.minDurationMin ? RANDOM.nextInt(template.maxDurationMin - template.minDurationMin + 1) : 0);

        int totalStartMin = (startHour % 12) * 60 + startMin;
        if (sSuffix.equals(" p.m.")) {
            totalStartMin += 12 * 60;
        }

        int totalEndMin = totalStartMin + durationMin;
        int endHourRaw = (totalEndMin / 60) % 24;
        String eSuffix = endHourRaw >= 12 ? " p.m." : " a.m.";
        if (template.forceEndPM) eSuffix = " p.m.";
        
        int endHourDisplay = endHourRaw % 12 == 0 ? 12 : endHourRaw % 12;
        int endMinDisplay = totalEndMin % 60;

        String startTimeStr = String.format(Locale.US, "%d:%02d%s", startHour, startMin, sSuffix);
        String endTimeStr = String.format(Locale.US, "%d:%02d%s", endHourDisplay, endMinDisplay, eSuffix);

        String questionText = template.questionPattern
                .replace("{name}", name)
                .replace("{start}", startTimeStr)
                .replace("{end}", endTimeStr);

        return createTimeStoryQuestion(questionText, durationMin / 60, durationMin % 60);
    }

    private static Question createTimeStoryQuestion(String questionText, int h, int m) {
        String correctAnswer;
        if (h > 0 && m > 0) {
            correctAnswer = h + " hours " + m + " minutes";
        } else if (h > 0) {
            correctAnswer = h + " hours";
        } else {
            correctAnswer = m + " minutes";
        }

        List<String> options = new ArrayList<>();
        options.add(correctAnswer);
        options.add((h + 1) + " hours " + m + " minutes");
        options.add(h + " hours " + (m + 15) % 60 + " minutes");
        options.add((h > 0 ? h - 1 : h + 2) + " hours " + (m + 10) % 60 + " minutes");

        for (int i = 0; i < options.size(); i++) {
            if (options.get(i).startsWith("0 hours ")) {
                options.set(i, options.get(i).replace("0 hours ", ""));
            }
        }

        Collections.shuffle(options);
        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(correctAnswer);
        OptionUtils.setQuestionOptions(question, options.toArray(new String[0]));
        return question;
    }
}
