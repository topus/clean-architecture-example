package com.clean.example.core.usecase.exchange.getcapacity;

import com.clean.example.core.entity.BroadbandAccessDevice;
import com.clean.example.core.entity.Capacity;
import com.clean.example.core.entity.DeviceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.clean.example.core.entity.DeviceType.ADSL;
import static com.clean.example.core.entity.DeviceType.FIBRE;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetCapacityForExchangeUseCaseTest {

    private static final String EXCHANGE_CODE = "exchangeCode";
    public static final int NO_PORTS = 0;

    DoesExchangeExist doesExchangeExist = mock(DoesExchangeExist.class);
    GetAvailablePortsOfAllDevicesInExchange getAvailablePortsOfAllDevicesInExchange = mock(GetAvailablePortsOfAllDevicesInExchange.class);

    GetCapacityForExchangeUseCase getCapacityForExchangeUseCase = new GetCapacityForExchangeUseCase(doesExchangeExist, getAvailablePortsOfAllDevicesInExchange);

    @BeforeEach
    public void setUp() {
        givenExchangeExists();
    }

    @Test
    public void noCapacityIfDevicesHaveNoPorts() {
        givenDevices(device(FIBRE, NO_PORTS), device(ADSL, NO_PORTS));

        Capacity capacity = getCapacityForExchangeUseCase.getCapacity(EXCHANGE_CODE);

        assertThat(capacity.hasAdslCapacity()).isFalse();
        assertThat(capacity.hasFibreCapacity()).isFalse();
    }

    @Test
    public void hasCapacityIfAdslDeviceHasPorts() {
        givenDevices(device(ADSL, 10));

        Capacity capacity = getCapacityForExchangeUseCase.getCapacity(EXCHANGE_CODE);

        assertThat(capacity.hasAdslCapacity()).isTrue();
    }

    @Test
    public void hasOnlyAdslCapacityIfAdslDeviceHasPorts() {
        givenDevices(device(ADSL, 10));

        Capacity capacity = getCapacityForExchangeUseCase.getCapacity(EXCHANGE_CODE);

        assertThat(capacity.hasFibreCapacity()).isFalse();
    }

    @Test
    public void hasCapacityIfFibreDeviceHasPorts() {
        givenDevices(device(FIBRE, 10));

        Capacity capacity = getCapacityForExchangeUseCase.getCapacity(EXCHANGE_CODE);

        assertThat(capacity.hasFibreCapacity()).isTrue();
    }

    @Test
    public void hasOnlyFibreCapacityIfAdslDeviceHasPorts() {
        givenDevices(device(FIBRE, 10));

        Capacity capacity = getCapacityForExchangeUseCase.getCapacity(EXCHANGE_CODE);

        assertThat(capacity.hasAdslCapacity()).isFalse();
    }

    @Test
    public void capacityJoinsAvailablePortsForAdslDevices() {
        givenDevices(device(ADSL, 10), device(ADSL, NO_PORTS));

        Capacity capacity = getCapacityForExchangeUseCase.getCapacity(EXCHANGE_CODE);

        assertThat(capacity.hasAdslCapacity()).isTrue();
    }

    @Test
    public void capacityJoinsAvailablePortsForFibreDevices() {
        givenDevices(device(FIBRE, 10), device(FIBRE, NO_PORTS));

        Capacity capacity = getCapacityForExchangeUseCase.getCapacity(EXCHANGE_CODE);

        assertThat(capacity.hasFibreCapacity()).isTrue();
    }

    @Test
    public void adslHasNoCapacityIfAvailablePortIsLessThan5() {
        givenDevices(device(ADSL, 1), device(ADSL, 3));

        Capacity capacity = getCapacityForExchangeUseCase.getCapacity(EXCHANGE_CODE);

        assertThat(capacity.hasAdslCapacity()).isFalse();
    }

    @Test
    public void fibreHasNoCapacityIfAvailablePortIsLessThan5() {
        givenDevices(device(FIBRE, 1), device(FIBRE, 3));

        Capacity capacity = getCapacityForExchangeUseCase.getCapacity(EXCHANGE_CODE);

        assertThat(capacity.hasFibreCapacity()).isFalse();
    }

    @Test
    public void adslHasCapacityIfAvailablePortIsEqualTo5() {
        givenDevices(device(ADSL, 2), device(ADSL, 3));

        Capacity capacity = getCapacityForExchangeUseCase.getCapacity(EXCHANGE_CODE);

        assertThat(capacity.hasAdslCapacity()).isTrue();
    }

    @Test
    public void fibreHasCapacityIfAvailablePortIsEqualTo5() {
        givenDevices(device(FIBRE, 2), device(FIBRE, 3));

        Capacity capacity = getCapacityForExchangeUseCase.getCapacity(EXCHANGE_CODE);

        assertThat(capacity.hasFibreCapacity()).isTrue();
    }

    @Test
    public void errorWhenExchangeDoesNotExist() {
        givenExchangeDoesNotExist();

        assertThatExceptionOfType(ExchangeNotFoundException.class).isThrownBy(() ->
                getCapacityForExchangeUseCase.getCapacity(EXCHANGE_CODE)
        );

    }

    private void givenDevices(BroadbandAccessDevice... broadbandAccessDevices) {
        when(getAvailablePortsOfAllDevicesInExchange.getAvailablePortsOfAllDevicesInExchange(EXCHANGE_CODE)).thenReturn(asList(broadbandAccessDevices));
    }

    private void givenExchangeExists() {
        when(doesExchangeExist.doesExchangeExist(EXCHANGE_CODE)).thenReturn(true);
    }

    private void givenExchangeDoesNotExist() {
        when(doesExchangeExist.doesExchangeExist(EXCHANGE_CODE)).thenReturn(false);
    }

    private BroadbandAccessDevice device(DeviceType deviceType, int availablePorts) {
        BroadbandAccessDevice broadbandAccessDevice = new BroadbandAccessDevice("hostname", "serial", deviceType);
        broadbandAccessDevice.setAvailablePorts(availablePorts);
        return broadbandAccessDevice;
    }
}