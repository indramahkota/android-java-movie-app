package com.indramahkota.moviecatalogue.data.source;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class ResourceTest {
    @Test
    public void loading() {
        Resource resource = Resource.loading(new ArrayList<>());
        Assert.assertEquals(Status.LOADING, resource.status);
    }

    @Test
    public void success() {
        Resource resource = Resource.success("test");
        Assert.assertEquals("test", resource.data);
        Assert.assertEquals(Status.SUCCESS, resource.status);
    }

    @Test
    public void exception() {
        Exception exception = new Exception("test");
        Resource resource = Resource.error(exception.getMessage(), exception);
        Assert.assertEquals("test", resource.message);
        Assert.assertEquals(Status.ERROR, resource.status);
    }
}