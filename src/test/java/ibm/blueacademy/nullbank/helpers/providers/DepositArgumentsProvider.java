package ibm.blueacademy.nullbank.helpers.providers;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class DepositArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
            Arguments.of(5_000, new double[] { 5_000 }),
            Arguments.of(1_000, new double[] { 300, 600, 100 })
        );
    }
}
