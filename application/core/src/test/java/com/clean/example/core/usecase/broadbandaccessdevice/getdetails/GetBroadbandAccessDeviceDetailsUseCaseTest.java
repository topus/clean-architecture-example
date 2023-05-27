package com.clean.example.core.usecase.broadbandaccessdevice.getdetails;

import com.clean.example.core.entity.BroadbandAccessDevice;
import org.junit.jupiter.api.Test;

import static com.clean.example.core.entity.DeviceType.ADSL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetBroadbandAccessDeviceDetailsUseCaseTest {

    GetDeviceDetails getDeviceDetails = mock(GetDeviceDetails.class);

    GetBroadbandAccessDeviceDetailsUseCase getBroadbandAccessDeviceDetailsUseCase = new GetBroadbandAccessDeviceDetailsUseCase(getDeviceDetails);

    @Test
    public void returnsDeviceDetails() {
        BroadbandAccessDevice expectedDevice = givenADeviceIsFound();

        BroadbandAccessDevice actualDevice = getBroadbandAccessDeviceDetailsUseCase.getDeviceDetails("hostname1");

        assertThat(actualDevice).isEqualTo(expectedDevice);
    }

    @Test
    public void errorWhenDeviceIsNotFound() {
        givenADeviceIsNotFound();

        assertThatThrownBy( () -> getBroadbandAccessDeviceDetailsUseCase.getDeviceDetails("hostnameX") )
                .isInstanceOf(DeviceNotFoundException.class);
    }

    private BroadbandAccessDevice givenADeviceIsFound() {
        BroadbandAccessDevice expectedDevice = new BroadbandAccessDevice("hostname1", "serialNumber", ADSL);
        when(getDeviceDetails.getDetails("hostname1")).thenReturn(expectedDevice);
        return expectedDevice;
    }

    private void givenADeviceIsNotFound() {
        when(getDeviceDetails.getDetails(anyString())).thenReturn(null);
    }

}