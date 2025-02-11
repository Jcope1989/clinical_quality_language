package org.opencds.cqf.cql.engine.elm.executing;

import org.opencds.cqf.cql.engine.exception.InvalidOperatorArgument;

import java.util.Iterator;

/*
Combine(source List<String>) String
Combine(source List<String>, separator String) String

The Combine operator combines a list of strings, optionally separating each string with the given separator.
If either argument is null, or any element in the source list of strings is null, the result is null.
*/

public class CombineEvaluator {

    public static Object combine(Object source, String separator) {

        if (source == null || separator == null) {
            return null;
        }

        else {
            if (source instanceof Iterable) {
                StringBuffer buffer = new StringBuffer("");
                Iterator<?> iterator = ((Iterable<?>) source).iterator();
                boolean first = true;

                while (iterator.hasNext()) {
                    Object item = iterator.next();

                    if (item == null) {
                        return null;
                    }

                    if (item instanceof String) {
                        if (!first) {
                            buffer.append(separator);
                        } else {
                            first = false;
                        }
                        buffer.append((String) item);
                    }
                    else {
                        throw new InvalidOperatorArgument(
                                "Combine(List<String>) or Combine(List<String>, String)",
                                String.format("Combine(List<%s>%s)", item.getClass().getName(), separator.equals("") ? "" : ", " + separator)
                        );
                    }
                }
                return buffer.toString();
            }
        }

        throw new InvalidOperatorArgument(
                "Combine(List<String>) or Combine(List<String>, String)",
                String.format("Combine(%s%s)", source.getClass().getName(), separator.equals("") ? "" : ", " + separator)
        );
    }

}
