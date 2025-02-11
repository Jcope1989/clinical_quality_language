package org.opencds.cqf.cql.engine.execution;

import org.opencds.cqf.cql.engine.runtime.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.TimeZone;


public class CqlInternalTypeRepresentationSuiteTest extends CqlTestBase {

    @Test
    public void test_all_internal_type_representation() {
        EvaluationResult evaluationResult;

        evaluationResult = engineVisitor.evaluate(toElmIdentifier("CqlInternalTypeRepresentationSuite"), ZonedDateTime.of(2018, 1, 1, 7, 0, 0, 0, TimeZone.getDefault().toZoneId()));

        Object result;

        result = evaluationResult.expressionResults.get("BoolTrue").value();
        Assert.assertTrue(result instanceof Boolean);
        Assert.assertTrue((Boolean) result);

        result = evaluationResult.expressionResults.get("BoolFalse").value();
        Assert.assertTrue(result instanceof Boolean);
        Assert.assertTrue(!(Boolean) result);

        result = evaluationResult.expressionResults.get("IntOne").value();
        Assert.assertTrue(result instanceof Integer);
        Assert.assertTrue((Integer) result == 1);

        result = evaluationResult.expressionResults.get("DecimalTenth").value();
        Assert.assertTrue(result instanceof BigDecimal);
        Assert.assertTrue(((BigDecimal) result).compareTo(new BigDecimal("0.1")) == 0);

        result = evaluationResult.expressionResults.get("StringTrue").value();
        Assert.assertTrue(result instanceof String);
        Assert.assertTrue(result.equals("true"));

        result = evaluationResult.expressionResults.get("DateTimeX").value();
        Assert.assertTrue(result instanceof DateTime);
        Assert.assertTrue(((DateTime) result).equal(new DateTime(new BigDecimal("0.0"), 2012, 2, 15, 12, 10, 59, 456)));

        result = evaluationResult.expressionResults.get("DateTimeFX").value();
        Assert.assertTrue(result instanceof DateTime);
        Assert.assertTrue(((DateTime) result).equal(new DateTime(new BigDecimal("0.0"), 2012, 2, 15, 12, 10, 59, 456)));

        result = evaluationResult.expressionResults.get("TimeX").value();
        Assert.assertTrue(result instanceof Time);
        Assert.assertTrue(((Time) result).equal(new Time(12, 10, 59, 456)));

        result = evaluationResult.expressionResults.get("DateTime_Year").value();
        Assert.assertTrue(result instanceof DateTime);
        Assert.assertTrue(((DateTime) result).equal(new DateTime(null, 2012)));

        result = evaluationResult.expressionResults.get("DateTime_Month").value();
        Assert.assertTrue(result instanceof DateTime);
        Assert.assertTrue(((DateTime) result).equal(new DateTime(null, 2012, 2)));

        result = evaluationResult.expressionResults.get("DateTime_Day").value();
        Assert.assertTrue(result instanceof DateTime);
        Assert.assertTrue(((DateTime) result).equal(new DateTime(null, 2012, 2, 15)));

        result = evaluationResult.expressionResults.get("DateTime_Hour").value();
        Assert.assertTrue(result instanceof DateTime);
        Assert.assertTrue(((DateTime) result).equal(new DateTime(null, 2012, 2, 15, 12)));

        result = evaluationResult.expressionResults.get("DateTime_Minute").value();
        Assert.assertTrue(result instanceof DateTime);
        Assert.assertTrue(((DateTime) result).equal(new DateTime(null, 2012, 2, 15, 12, 10)));

        result = evaluationResult.expressionResults.get("DateTime_Second").value();
        Assert.assertTrue(result instanceof DateTime);
        Assert.assertTrue(((DateTime) result).equal(new DateTime(null, 2012, 2, 15, 12, 10, 59)));

        result = evaluationResult.expressionResults.get("DateTime_Millisecond").value();
        Assert.assertTrue(result instanceof DateTime);
        Assert.assertTrue(((DateTime) result).equal(new DateTime(null, 2012, 2, 15, 12, 10, 59, 456)));

        result = evaluationResult.expressionResults.get("DateTime_TimezoneOffset").value();
        Assert.assertTrue(result instanceof DateTime);
        Assert.assertTrue(((DateTime) result).equal(new DateTime(new BigDecimal("-8.0"), 2012, 2, 15, 12, 10, 59, 456)));

        result = evaluationResult.expressionResults.get("Time_Hour").value();
        Assert.assertTrue(result instanceof Time);
        Assert.assertTrue(((Time) result).equal(new Time(12)));

        result = evaluationResult.expressionResults.get("Time_Minute").value();
        Assert.assertTrue(result instanceof Time);
        Assert.assertTrue(((Time) result).equal(new Time(12, 10)));

        result = evaluationResult.expressionResults.get("Time_Second").value();
        Assert.assertTrue(result instanceof Time);
        Assert.assertTrue(((Time) result).equal(new Time(12, 10, 59)));

        result = evaluationResult.expressionResults.get("Time_Millisecond").value();
        Assert.assertTrue(result instanceof Time);
        Assert.assertTrue(((Time) result).equal(new Time(12, 10, 59, 456)));

        result = evaluationResult.expressionResults.get("Clinical_quantity").value();
        Assert.assertTrue(result instanceof Quantity);
        Assert.assertTrue(((Quantity) result).equal(new Quantity().withValue(new BigDecimal(12)).withUnit("a")));

        result = evaluationResult.expressionResults.get("Clinical_QuantityA").value();
        Assert.assertTrue(result instanceof Quantity);
        Assert.assertTrue(((Quantity) result).equal(new Quantity().withValue(new BigDecimal(12)).withUnit("a")));

        result = evaluationResult.expressionResults.get("Clinical_CodeA").value();
        Assert.assertTrue(result instanceof Code);
        Assert.assertTrue(((Code) result).equal(new Code().withCode("12345").withSystem("http://loinc.org").withVersion("1").withDisplay("Test Code")));

        result = evaluationResult.expressionResults.get("Clinical_ConceptA").value();
        Assert.assertTrue(result instanceof Concept);
        Assert.assertTrue(((Concept) result).equal(
                        new Concept()
                                .withCode(
                                        new Code().withCode("12345").withSystem("http://loinc.org").withVersion("1").withDisplay("Test Code")
                                ).withDisplay("Test Concept")
                )
        );

        LinkedHashMap<String, Object> elements = new LinkedHashMap<>();
        elements.put("a", 1);
        elements.put("b", 2);
        result = evaluationResult.expressionResults.get("Structured_tuple").value();
        Assert.assertTrue(result instanceof Tuple);
        Assert.assertTrue(((Tuple) result).equal(new Tuple(engineVisitor.getState()).withElements(elements)));

        elements.clear();
        elements.put("class", "Portable CQL Test Suite");
        elements.put("versionNum", new BigDecimal("1.0"));
        elements.put("date", new DateTime(null, 2018, 7, 18));
        elements.put("developer", "Christopher Schuler");

        result = evaluationResult.expressionResults.get("Structured_TupleA").value();
        Assert.assertTrue(result instanceof Tuple);
        Assert.assertTrue(((Tuple) result).equal(new Tuple(engineVisitor.getState()).withElements(elements)));

        result = evaluationResult.expressionResults.get("Interval_Open").value();
        Assert.assertTrue(result instanceof Interval);
        Assert.assertTrue(
                ((Interval) result).equal(
                        new Interval(
                                new DateTime(null, 2012, 1, 1), false,
                                new DateTime(null, 2013, 1, 1), false
                        )
                )
        );

        result = evaluationResult.expressionResults.get("Interval_LeftOpen").value();
        Assert.assertTrue(result instanceof Interval);
        Assert.assertTrue(
                ((Interval) result).equal(
                        new Interval(
                                new DateTime(null, 2012, 1, 1), false,
                                new DateTime(null, 2013, 1, 1), true
                        )
                )
        );

        result = evaluationResult.expressionResults.get("Interval_RightOpen").value();
        Assert.assertTrue(result instanceof Interval);
        Assert.assertTrue(
                ((Interval) result).equal(
                        new Interval(
                                new DateTime(null, 2012, 1, 1), true,
                                new DateTime(null, 2013, 1, 1), false
                        )
                )
        );

        result = evaluationResult.expressionResults.get("Interval_Closed").value();
        Assert.assertTrue(result instanceof Interval);
        Assert.assertTrue(
                ((Interval) result).equal(
                        new Interval(
                                new DateTime(null, 2012, 1, 1), true,
                                new DateTime(null, 2013, 1, 1), true
                        )
                )
        );

        result = evaluationResult.expressionResults.get("List_BoolList").value();
        Assert.assertTrue(result instanceof Iterable);
        Boolean listComp = CqlList.equal((Iterable<?>) result, Arrays.asList(true, false, true), engineVisitor.getState());
        Assert.assertTrue(listComp != null && listComp);

        result = evaluationResult.expressionResults.get("List_IntList").value();
        Assert.assertTrue(result instanceof Iterable);
        listComp = CqlList.equal((Iterable<?>) result, Arrays.asList(9, 7, 8), engineVisitor.getState());
        Assert.assertTrue(listComp != null && listComp);

        result = evaluationResult.expressionResults.get("List_DecimalList").value();
        Assert.assertTrue(result instanceof Iterable);
        listComp = CqlList.equal((Iterable<?>) result, Arrays.asList(new BigDecimal("1.0"), new BigDecimal("2.1"), new BigDecimal("3.2")), engineVisitor.getState());
        Assert.assertTrue(listComp != null && listComp);

        result = evaluationResult.expressionResults.get("List_StringList").value();
        Assert.assertTrue(result instanceof Iterable);
        listComp = CqlList.equal((Iterable<?>) result, Arrays.asList("a", "bee", "see"), engineVisitor.getState());
        Assert.assertTrue(listComp != null && listComp);

        result = evaluationResult.expressionResults.get("List_DateTimeList").value();
        Assert.assertTrue(result instanceof Iterable);
        listComp = CqlList.equal(
                (Iterable<?>) result,
                Arrays.asList(
                        new DateTime(new BigDecimal("0.0"), 2012, 2, 15, 12, 10, 59, 456),
                        new DateTime(new BigDecimal("0.0"), 2012, 3, 15, 12, 10, 59, 456),
                        new DateTime(new BigDecimal("0.0"), 2012, 4, 15, 12, 10, 59, 456)
                ),
                engineVisitor.getState()
        );
        Assert.assertTrue(listComp != null && listComp);

        result = evaluationResult.expressionResults.get("List_TimeList").value();
        Assert.assertTrue(result instanceof Iterable);
        listComp = CqlList.equal(
                (Iterable<?>) result,
                Arrays.asList(
                        new Time(12, 10, 59, 456),
                        new Time(13, 10, 59, 456),
                        new Time(14, 10, 59, 456)
                ),
                engineVisitor.getState()
        );
        Assert.assertTrue(listComp != null && listComp);

        result = evaluationResult.expressionResults.get("List_QuantityList").value();
        Assert.assertTrue(result instanceof Iterable);
        listComp = CqlList.equal(
                (Iterable<?>) result,
                Arrays.asList(
                        new Quantity().withValue(new BigDecimal("1.0")).withUnit("m"),
                        new Quantity().withValue(new BigDecimal("2.1")).withUnit("m"),
                        new Quantity().withValue(new BigDecimal("3.2")).withUnit("m")
                ),
                engineVisitor.getState()
        );
        Assert.assertTrue(listComp != null && listComp);

        result = evaluationResult.expressionResults.get("List_CodeList").value();
        Assert.assertTrue(result instanceof Iterable);
        listComp = CqlList.equal(
                (Iterable<?>) result,
                Arrays.asList(
                        new Code().withCode("12345").withSystem("http://loinc.org").withVersion("1").withDisplay("Test Code"),
                        new Code().withCode("123456").withSystem("http://loinc.org").withVersion("1").withDisplay("Another Test Code")
                ),
                engineVisitor.getState()
        );
        Assert.assertTrue(listComp != null && listComp);

        result = evaluationResult.expressionResults.get("List_ConceptList").value();
        Assert.assertTrue(result instanceof Iterable);
        listComp = CqlList.equal(
                (Iterable<?>) result,
                Arrays.asList(
                        new Concept().withCode(
                                new Code().withCode("12345").withSystem("http://loinc.org").withVersion("1").withDisplay("Test Code")
                        ).withDisplay("Test Concept"),
                        new Concept().withCode(
                                new Code().withCode("123456").withSystem("http://loinc.org").withVersion("1").withDisplay("Another Test Code")
                        ).withDisplay("Another Test Concept")

                ),
                engineVisitor.getState()
        );
        Assert.assertTrue(listComp != null && listComp);

        elements.clear();
        elements.put("a", 1);
        elements.put("b", "2");
        LinkedHashMap<String, Object> elements2 = new LinkedHashMap<>();
        elements2.put("x", 2);
        elements2.put("z", "3");
        result = evaluationResult.expressionResults.get("List_TupleList").value();
        Assert.assertTrue(result instanceof Iterable);
        listComp = CqlList.equal(
                (Iterable<?>) result,
                Arrays.asList(
                        new Tuple(engineVisitor.getState()).withElements(elements), new Tuple(engineVisitor.getState()).withElements(elements2)

                ),
                engineVisitor.getState()
        );
        Assert.assertTrue(listComp != null && listComp);

        result = evaluationResult.expressionResults.get("List_ListList").value();
        Assert.assertTrue(result instanceof Iterable);
        listComp = CqlList.equal(
                (Iterable<?>) result,
                Arrays.asList(Arrays.asList(1,2,3), Arrays.asList("a", "b", "c")), engineVisitor.getState()
        );
        Assert.assertTrue(listComp != null && listComp);

        result = evaluationResult.expressionResults.get("List_IntervalList").value();
        Assert.assertTrue(result instanceof Iterable);
        listComp = CqlList.equal(
                (Iterable<?>) result,
                Arrays.asList(
                        new Interval(1, true, 5, true), new Interval(5, false, 9, false), new Interval(8, true, 10, false)
                ),
                engineVisitor.getState()
        );
        Assert.assertTrue(listComp != null && listComp);

        result = evaluationResult.expressionResults.get("List_MixedList").value();
        Assert.assertTrue(result instanceof Iterable);
        listComp = CqlList.equal((Iterable<?>) result, Arrays.asList(1, "two", 3), engineVisitor.getState());
        Assert.assertTrue(listComp != null && listComp);

        result = evaluationResult.expressionResults.get("List_EmptyList").value();
        Assert.assertTrue(result instanceof Iterable);
        listComp = CqlList.equal((Iterable<?>) result, Collections.EMPTY_LIST, engineVisitor.getState());
        Assert.assertTrue(listComp != null && listComp);

    }
}
