package org.opencds.cqf.cql.engine.execution;

import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class DateComparatorTest extends CqlTestBase {

    @Test
    public void test_date_comparator() {

        EvaluationResult evaluationResult;

        evaluationResult = engineVisitor.evaluate(toElmIdentifier("DateComparatorTest"));
        Object result = evaluationResult.expressionResults.get("Date Comparator Test").value();
        assertThat(result, is(true));

    }
}
