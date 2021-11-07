package ibm.blueacademy.nullbank.helpers.providers;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class TransferArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
            Arguments.of(1_000, 100, 900, new double[] { 300, 600 }),
            Arguments.of(1_000, 0, 1_000, new double[] { 1_000 }),
            Arguments.of(2_000, 1_000, 1_000, new double[] { 500, 500 })
        );
    }
}