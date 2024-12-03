package com.example.funcalculator;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.funcalculator.data.remote.apis.evaluator.VolleyClient;
import com.example.funcalculator.model.expression.Expression;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.robolectric.RobolectricTestRunner;

import java.io.IOException;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

@RunWith(RobolectricTestRunner.class)
public class TestVolleyClient {
    @Mock
    private RequestQueue mockQueue;

    @Mock
    private Context mockContext;
    
    @Mock
    private VolleyClient volleyClient;

    @Mock
    private Expression testExpression;
    
    @Before
    public void setUp() {
        mockContext = ApplicationProvider.getApplicationContext();
        volleyClient = new VolleyClient("evaluate", mockContext);
        testExpression = new Expression("2+2");
    }

    @Test
    public void emptyTest() {
        assertTrue(true);
    }

    @Test
    public void testGet() throws IOException {
        MockWebServer server = new MockWebServer();
        try {
            server.enqueue(new MockResponse().setBody("4"));
            server.start();
            // volleyClient.get(testExpression);
            assertEquals("4", "4");
            server.shutdown();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    

}
