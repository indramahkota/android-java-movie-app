package com.indramahkota.moviecatalogue.data.source.remote.response;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class ApiResponseTest {
    @Test
    public void empty() {
        ApiResponse apiResponse = ApiResponse.empty("empty", new ArrayList<>());
        Assert.assertEquals(StatusResponse.EMPTY, apiResponse.status);
    }

    @Test
    public void success() {
        ApiResponse apiResponse = ApiResponse.success("test");
        Assert.assertEquals("test", apiResponse.body);
        Assert.assertEquals(StatusResponse.SUCCESS, apiResponse.status);
    }

    @Test
    public void exception() {
        Exception exception = new Exception("test");
        ApiResponse apiResponse = ApiResponse.error(exception.getMessage(), exception);
        Assert.assertEquals("test", apiResponse.message);
        Assert.assertEquals(StatusResponse.ERROR, apiResponse.status);
    }
}