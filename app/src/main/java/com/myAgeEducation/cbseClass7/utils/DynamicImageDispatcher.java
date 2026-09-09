package com.myAgeEducation.cbseClass7.utils;

import android.content.Context;
import android.graphics.Bitmap;

import com.myAgeEducation.cbseClass7.maths.LineAndAngle.AngleImageGenerator;
import com.myAgeEducation.cbseClass7.maths.charts.BarChartImageGenerator;
import com.myAgeEducation.cbseClass7.maths.circlegraph.CircleGraphImageGenerator;
import com.myAgeEducation.cbseClass7.maths.datetimecalendar.CalendarImageGenerator;
import com.myAgeEducation.cbseClass7.maths.datetimecalendar.ClockImageGenerator;
import com.myAgeEducation.cbseClass7.maths.decimals.DecimalGridImageGenerator;
import com.myAgeEducation.cbseClass7.maths.decimals.DecimalImageGenerator;
import com.myAgeEducation.cbseClass7.maths.divisions.facts.DivisionPictureImageGenerator;
import com.myAgeEducation.cbseClass7.maths.fractions.EquivalentFractionImageGenerator;
import com.myAgeEducation.cbseClass7.maths.fractions.FractionChoiceGenerator;
import com.myAgeEducation.cbseClass7.maths.fractions.FractionComparisonImageGenerator;
import com.myAgeEducation.cbseClass7.maths.fractions.FractionImageGenerator;
import com.myAgeEducation.cbseClass7.maths.fractions.NumericFractionImageGenerator;
import com.myAgeEducation.cbseClass7.maths.mappingskills.DirectionDistanceImageGenerator;
import com.myAgeEducation.cbseClass7.maths.mappingskills.MetroMapImageGenerator;
import com.myAgeEducation.cbseClass7.maths.mappingskills.NeighborhoodMapImageGenerator;
import com.myAgeEducation.cbseClass7.maths.mappingskills.ZooMapImageGenerator;
import com.myAgeEducation.cbseClass7.maths.pattern.PatternSequenceImageGenerator;
import com.myAgeEducation.cbseClass7.maths.perimeterarea.PerimeterShapeImageGenerator;
import com.myAgeEducation.cbseClass7.maths.perimeterarea.TileCoveringImageGenerator;
import com.myAgeEducation.cbseClass7.maths.pictograph.PictographImageGenerator;
import com.myAgeEducation.cbseClass7.maths.symmetry.SymmetryImageGenerator;
import com.myAgeEducation.cbseClass7.maths.tabularquestions.TableImageGenerator;

public class DynamicImageDispatcher {
    public static Bitmap dispatch(Context context, String imageCode) {
        if (imageCode == null || imageCode.isEmpty()) return null;

        if (imageCode.startsWith(ImageCodeType.BARCHART)) {
            return BarChartImageGenerator.generate(imageCode);
        }
        if (imageCode.startsWith(ImageCodeType.CIRCLE_GRAPH)) {
            return CircleGraphImageGenerator.generate(imageCode);
        }
        if (imageCode.startsWith(ImageCodeType.PICTOGRAPH)) {
            return PictographImageGenerator.generate(context, imageCode);
        }
        if (imageCode.startsWith(ImageCodeType.ANGLE)) {
            return AngleImageGenerator.generateImage(imageCode);
        }
        if (imageCode.startsWith(ImageCodeType.CLOCK)) {
            return ClockImageGenerator.generateClockImage(imageCode);
        }
        if (imageCode.startsWith(ImageCodeType.CALENDAR)) {
            return CalendarImageGenerator.generateCalendarImage(imageCode);
        }
        if (imageCode.startsWith(ImageCodeType.DECIMAL_GRID)) {
            return DecimalGridImageGenerator.generate(imageCode);
        }
        if (imageCode.startsWith(ImageCodeType.DECIMAL_IMAGE)) {
            return DecimalImageGenerator.generate(imageCode);
        }
        if (imageCode.startsWith(ImageCodeType.TABLE)) {
            return TableImageGenerator.generate(imageCode);
        }
        if (imageCode.startsWith(ImageCodeType.ZOO_MAP)) {
            return ZooMapImageGenerator.generate(context, imageCode);
        }
        if (imageCode.startsWith(ImageCodeType.NEIGHBORHOOD_MAP)) {
            return NeighborhoodMapImageGenerator.generate(context, imageCode);
        }
        if (imageCode.startsWith(ImageCodeType.METRO_MAP)) {
            return MetroMapImageGenerator.generate(context, imageCode);
        }
        if (imageCode.startsWith(ImageCodeType.PATTERN_SEQUENCE)) {
            return PatternSequenceImageGenerator.generate(context, imageCode);
        }
        if (imageCode.startsWith(ImageCodeType.EQUIVALENT_FRACTION)) {
            return EquivalentFractionImageGenerator.generate(imageCode);
        }
        if (imageCode.startsWith(ImageCodeType.FRACTION_COMPARISON)) {
            return FractionComparisonImageGenerator.generate(imageCode);
        }
        if (imageCode.startsWith(ImageCodeType.NUMERIC_FRACTION)) {
            return NumericFractionImageGenerator.generate(imageCode);
        }
        if (imageCode.startsWith(ImageCodeType.TILE_COVERING)) {
            return TileCoveringImageGenerator.generate(imageCode);
        }
        if (imageCode.startsWith(ImageCodeType.PERIMETER_SHAPE)) {
            return PerimeterShapeImageGenerator.generate(imageCode);
        }
        if (imageCode.startsWith("SYMMETRY")) {
            return SymmetryImageGenerator.generate(imageCode);
        }
        if (imageCode.startsWith("CONG")) {
            return com.myAgeEducation.cbseClass7.maths.congruence.CongruenceImageGenerator.generate(imageCode);
        }
        if (imageCode.startsWith(ImageCodeType.TRIANGLE)) {
            return com.myAgeEducation.cbseClass7.maths.triangles.TriangleImageGenerator.generate(imageCode);
        }
        if(imageCode.startsWith(ImageCodeType.SHAPE_PART_FRACTION))
        {
            return FractionImageGenerator.generateFractionImage(imageCode);
        }
        if(imageCode.startsWith(ImageCodeType.DIVISION))
        {
            return DivisionPictureImageGenerator.generate(imageCode);
        }
        if(imageCode.startsWith(ImageCodeType.FRACTION_CHOICE)){
            return FractionChoiceGenerator.generateBitmap(imageCode);
        }
        if(imageCode.startsWith(ImageCodeType.DISTANCE_GRID_QUIZ)) {
            return DirectionDistanceImageGenerator.generate(context, imageCode);
        }

        
        return null;
    }
}
