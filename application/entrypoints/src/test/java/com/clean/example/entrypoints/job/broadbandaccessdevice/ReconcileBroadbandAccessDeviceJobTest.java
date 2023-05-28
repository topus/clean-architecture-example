package com.clean.example.entrypoints.job.broadbandaccessdevice;

import com.clean.example.core.usecase.broadbandaccessdevice.reconcile.ReconcileBroadbandAccessDevicesUseCase;
import com.clean.example.core.usecase.job.OnFailure;
import com.clean.example.core.usecase.job.OnSuccess;
import com.clean.example.entrypoints.job.scheduledjob.JobResults;
import com.clean.example.entrypoints.job.scheduledjob.JobResultsCount;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReconcileBroadbandAccessDeviceJobTest {

    ReconcileBroadbandAccessDevicesUseCase reconcileBroadbandAccessDevicesUseCase = mock(ReconcileBroadbandAccessDevicesUseCase.class);
    JobResults jobResults = mock(JobResults.class);
    JobResultsCount jobResultsCount;

    ReconcileBroadbandAccessDeviceJob reconcileBroadbandAccessDeviceJob = new ReconcileBroadbandAccessDeviceJob(reconcileBroadbandAccessDevicesUseCase, jobResults);

    @Test
    public void jobHasAllDetails() {
        assertThat(reconcileBroadbandAccessDeviceJob.getName()).isNotEmpty();
        assertThat(reconcileBroadbandAccessDeviceJob.getInitialDelay()).isZero();
        assertThat(reconcileBroadbandAccessDeviceJob.getPeriod()).isNotZero();
        assertThat(reconcileBroadbandAccessDeviceJob.getTimeUnit()).isNotNull();
    }

    @Test
    public void startsUseCaseToReconcileDevices() {
        givenAJobResultsCount();

        reconcileBroadbandAccessDeviceJob.run();

        verify(reconcileBroadbandAccessDevicesUseCase).reconcile(any(), any());
    }

    @Test
    public void increasesSuccessesAndFailuresOnSuccessAndOnFailure() {
        givenAJobResultsCount();

        reconcileBroadbandAccessDeviceJob.run();
        invokeOnSuccessAndOnFailure();

        assertThat(jobResultsCount.getNumberOfSuccesses()).isEqualTo(1);
        assertThat(jobResultsCount.getNumberOfFailures()).isEqualTo(1);
    }

    @Test
    public void recordsSuccessesAndFailuresAtTheEnd() {
        givenAJobResultsCount();

        reconcileBroadbandAccessDeviceJob.run();

        verify(jobResults).recordJobResults(reconcileBroadbandAccessDeviceJob, jobResultsCount);
    }

    private void givenAJobResultsCount() {
        jobResultsCount = new JobResultsCount();
        when(jobResults.createJobResultsCount()).thenReturn(jobResultsCount);
    }

    private void invokeOnSuccessAndOnFailure() {
        ArgumentCaptor<OnSuccess> onSuccessArgumentCaptor = ArgumentCaptor.forClass(OnSuccess.class);
        ArgumentCaptor<OnFailure> onFailureArgumentCaptor = ArgumentCaptor.forClass(OnFailure.class);
        verify(reconcileBroadbandAccessDevicesUseCase).reconcile(onSuccessArgumentCaptor.capture(), onFailureArgumentCaptor.capture());
        onSuccessArgumentCaptor.getValue().auditSuccess();
        onFailureArgumentCaptor.getValue().auditFailure();
    }

}