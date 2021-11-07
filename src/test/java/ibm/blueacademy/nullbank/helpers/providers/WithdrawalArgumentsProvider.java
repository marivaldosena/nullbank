package ibm.blueacademy.nullbank.helpers.providers;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class WithdrawalArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
            Arguments.of(1_000, 100, new double[] { 300, 300, 300 }),
            Arguments.of(2_000, 500, new double[] { 1_200, 300 }),
            Arguments.of(2_500, 0, new double[] { 1_000, 500, 700, 300 })
        );
    }
}
