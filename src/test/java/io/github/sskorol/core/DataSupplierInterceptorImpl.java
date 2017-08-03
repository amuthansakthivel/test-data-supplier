package io.github.sskorol.core;

import io.github.sskorol.model.DataSupplierMetaData;
import lombok.extern.slf4j.Slf4j;
import one.util.streamex.StreamEx;
import org.testng.ITestContext;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class DataSupplierInterceptorImpl implements DataSupplierInterceptor {

    private static final Map<Method, DataSupplierMetaData> META_DATA = new ConcurrentHashMap<>();

    @Override
    public void beforeDataPreparation(final ITestContext context, final Method method) {
        log.info("Before calling {} for {}", context.getName(), method.getName());
    }

    @Override
    public void afterDataPreparation(final ITestContext context, final Method method) {
        log.info("After calling {} for {}", context.getName(), method.getName());
    }

    @Override
    public void onDataPreparation(final DataSupplierMetaData testMetaData) {
        log.info("onDataPreparation of {} for {} ({})", testMetaData.getTestMethod().getName(),
                testMetaData.getDataSupplierMethod().getName(),
                StreamEx.of(testMetaData.getTestData()).map(Arrays::asList).toList());
        META_DATA.putIfAbsent(testMetaData.getTestMethod(), testMetaData);
    }

    @Override
    public Collection<DataSupplierMetaData> getMetaData() {
        return META_DATA.values();
    }
}
