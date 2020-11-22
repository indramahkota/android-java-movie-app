package com.indramahkota.moviecatalogue.data.source;

import org.junit.Assert;
import org.junit.Test;

public class ResourceTest {
    @Test
    public void loading() {
        Resource<String> resource = Resource.loading("test");
        Assert.assertEquals(Status.LOADING, resource.status);
    }

    @Test
    public void success() {
        Resource<String> resource = Resource.success("test");
        Assert.assertEquals("test", resource.data);
        Assert.assertEquals(Status.SUCCESS, resource.status);
    }

    @Test
    public void exception() {
        Exception exception = new Exception("test message");
        Resource<String> resource = Resource.error(exception.getMessage(), "test");
        Assert.assertEquals("test message", resource.message);
        Assert.assertEquals(Status.ERROR, resource.status);
    }
}