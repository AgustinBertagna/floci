package io.github.hectorvent.floci.services.appsync.graphql.scalars;

import graphql.schema.GraphQLScalarType;
import graphql.schema.CoercingParseValueException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AppSyncScalarsTest {

    @Test
    void scalar_allRegistered() {
        AppSyncScalarRegistry registry = new AppSyncScalarRegistry();
        List<GraphQLScalarType> scalars = registry.allScalars();
        assertThat(scalars, hasSize(greaterThanOrEqualTo(17)));
        assertThat(scalars.stream().map(GraphQLScalarType::getName).toList(),
            hasItems("AWSJSON", "AWSDateTime", "AWSDate", "AWSTime", "AWSTimestamp",
                     "AWSEmail", "AWSURL", "AWSPhone", "AWSIPAddress", "AWSBoolean",
                     "AWSLong", "AWSInteger", "AWSShort", "AWSFloat",
                     "AWSBigDecimal", "AWSBigInt", "AWSByte"));
    }

    @Test
    void scalar_awsdatetime_parseValue() {
        Object valid = AppSyncScalars.AWS_DATE_TIME.getCoercing().parseValue("2026-06-04T12:00:00Z");
        assertThat(valid, is("2026-06-04T12:00:00Z"));

        assertThrows(CoercingParseValueException.class, () -> AppSyncScalars.AWS_DATE_TIME.getCoercing().parseValue("not-a-date"));
    }

    @Test
    void scalar_awsjson_parseValue() {
        Object valid = AppSyncScalars.AWSJSON.getCoercing().parseValue("{\"key\": \"value\"}");
        assertThat(valid, is("{\"key\": \"value\"}"));

        assertThrows(CoercingParseValueException.class, () -> AppSyncScalars.AWSJSON.getCoercing().parseValue("not json"));
    }

    @Test
    void scalar_awsemail_parseValue() {
        Object valid = AppSyncScalars.AWS_EMAIL.getCoercing().parseValue("user@example.com");
        assertThat(valid, is("user@example.com"));

        assertThrows(CoercingParseValueException.class, () -> AppSyncScalars.AWS_EMAIL.getCoercing().parseValue("not-an-email"));
    }

    @Test
    void scalar_awstimestamp_range() {
        Object valid = AppSyncScalars.AWS_TIMESTAMP.getCoercing().parseValue(1700000000L);
        assertThat(valid, is(1700000000L));

        assertThrows(CoercingParseValueException.class, () -> AppSyncScalars.AWS_TIMESTAMP.getCoercing().parseValue(-1L));
        assertThrows(CoercingParseValueException.class, () -> AppSyncScalars.AWS_TIMESTAMP.getCoercing().parseValue(40000000000L));
    }

    @Test
    void scalar_awsurl_parseValue() {
        Object valid = AppSyncScalars.AWS_URL.getCoercing().parseValue("https://example.com");
        assertThat(valid, is("https://example.com"));

        assertThrows(CoercingParseValueException.class, () -> AppSyncScalars.AWS_URL.getCoercing().parseValue("not a url"));
    }

    @Test
    void scalar_awsphone_parseValue() {
        Object valid = AppSyncScalars.AWS_PHONE.getCoercing().parseValue("+1234567890");
        assertThat(valid, is("+1234567890"));

        assertThrows(CoercingParseValueException.class, () -> AppSyncScalars.AWS_PHONE.getCoercing().parseValue("12345"));
    }

    @Test
    void scalar_awsshort_range() {
        Object valid = AppSyncScalars.AWS_SHORT.getCoercing().parseValue(32767);
        assertThat(valid, is(32767));

        assertThrows(CoercingParseValueException.class, () -> AppSyncScalars.AWS_SHORT.getCoercing().parseValue(40000));
        assertThrows(CoercingParseValueException.class, () -> AppSyncScalars.AWS_SHORT.getCoercing().parseValue(-32769));
    }
}
