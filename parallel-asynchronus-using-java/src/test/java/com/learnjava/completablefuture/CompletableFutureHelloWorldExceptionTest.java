package com.learnjava.completablefuture;

import com.learnjava.service.HelloWorldService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompletableFutureHelloWorldExceptionTest {

    @Mock
    private HelloWorldService helloWorldService = mock(HelloWorldService.class);

    @InjectMocks
    private CompletableFutureHelloWorldException hwcfe;

    @Test
    void helloWorld_3_async_calls_handle() {
        when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception Occurred"));
        when(helloWorldService.world()).thenCallRealMethod();

        String result = hwcfe.helloWorld_3_async_calls_handle();

        String expectedResult = " WORLD! HI COMPLETABLEFUTURE!";
        assertEquals(expectedResult, result);
    }

    @Test
    void helloWorld_3_async_calls_handle_2() {
        when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception Occurred"));
        when(helloWorldService.world()).thenThrow(new RuntimeException("Exception Occurred"));

        String result = hwcfe.helloWorld_3_async_calls_handle();

        String expectedResult = " HI COMPLETABLEFUTURE!";
        assertEquals(expectedResult, result);
    }

    @Test
    void helloWorld_3_async_calls_handle_checkedException() {
        when(helloWorldService.hello()).thenAnswer( answer -> new SQLException("Exception Occurred"));
        when(helloWorldService.world()).thenAnswer(answer -> new SQLException("Exception Occurred"));

        String result = hwcfe.helloWorld_3_async_calls_handle();

        String expectedResult = " HI COMPLETABLEFUTURE!";
        assertEquals(expectedResult, result);
    }



    @Test
    void helloWorld_3_async_calls_handle_3() {
        when(helloWorldService.hello()).thenCallRealMethod();
        when(helloWorldService.world()).thenCallRealMethod();

        String result = hwcfe.helloWorld_3_async_calls_handle();

        String expectedResult = "HELLO WORLD! HI COMPLETABLEFUTURE!";
        assertEquals(expectedResult, result);
    }


    @Test
    void helloWorld_3_async_calls_exceptionally() {
        when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception Occurred"));
        when(helloWorldService.world()).thenThrow(new RuntimeException("Exception Occurred"));

        String result = hwcfe.helloWorld_3_async_calls_exceptionally();

        String expectedResult = " HI COMPLETABLEFUTURE!";
        assertEquals(expectedResult, result);
    }

    @Test
    void helloWorld_3_async_whenComplete() {
        when(helloWorldService.hello()).thenCallRealMethod();
        when(helloWorldService.world()).thenCallRealMethod();

        String result = hwcfe.helloWorld_3_async_whenComplete();

        String expectedResult = "HELLO WORLD! HI COMPLETABLEFUTURE!";
        assertEquals(expectedResult, result);
    }


    @Test
    void helloWorld_3_async_whenComplete_2() {
        when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception Occurred"));
        when(helloWorldService.world()).thenCallRealMethod();

        String result = hwcfe.helloWorld_3_async_whenComplete();

        String expectedResult = " HI COMPLETABLEFUTURE!";
        assertEquals(expectedResult, result);
    }
}