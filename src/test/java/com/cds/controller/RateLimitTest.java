package com.cds.controller;

import com.cds.service.Api1Service;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@RunWith(SpringRunner.class)
//@WebMvcTest(controllers = {Api1Controller.class, Api2Controller.class, Api3Controller.class})
public class RateLimitTest {

//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//    private MockMvc mockMvc;
//
//    @Before
//    public void mockMvc() {
//     //   assert webApplicationContext != null;
//        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//    }

    @Autowired
    private Api1Service api1Service;

    /**
     * 模拟每秒请求500 次
     * @throws Exception
     */
    @Test
    public void testRateLimit500Times() throws Exception {
        // 模拟1个并发线程, 就是模拟1个用户请求
        int numThreads = 1;
        // 每个线程发送500次请求
        int requestsPerThread = 10001;
        // 创建固定线程池数量
        ExecutorService executorService = Executors.newFixedThreadPool(requestsPerThread);
        CountDownLatch latch = new CountDownLatch(numThreads * requestsPerThread);
        String api = "api" + (int) (Math.random() * 3 + 1);
        String requestUrl = "http://localhost:8010/";
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < requestsPerThread; i++) {
            // 将任务提交到线程池中执行
            executorService.submit(() -> {
                try {
                        // 随机选择API路径，这里是 /api1, /api2, 或 /api3
//                        String api = "/api" + (int) (Math.random() * 3 + 1);
//                        mockMvc.perform(MockMvcRequestBuilders.get(api).contentType(MediaType.APPLICATION_JSON))
//                               // .andExpect(MockMvcResultMatchers.status().isAnyOf(HttpStatus.OK, HttpStatus.TOO_MANY_REQUESTS));
//                                .andDo(result -> {
//                                            int statusCode = result.getResponse().getStatus();
//                                            if (!HttpStatus.OK.equals(statusCode) && !HttpStatus.TOO_MANY_REQUESTS.equals(statusCode)) {
//                                                throw new AssertionError("Expected status is not OK or Too Many Requests, the statusCode is  " + statusCode);
//                                            }
//                                });

                        URL url = new URL(requestUrl+api);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        if(api.equals("api1")){
                            connection.setRequestMethod("GET");
                        }else if(api.equals("api2")){
                            connection.setRequestMethod("POST");
                        }else {
                            connection.setRequestMethod("PUT");
                        }
                        connection.connect();
                        int responseCode = connection.getResponseCode();
                        if(responseCode == HttpStatus.OK.value()){
                            System.out.println("请求次数在正常范围内，流量可以控制");
                        }else if(responseCode == HttpStatus.TOO_MANY_REQUESTS.value()){
                            throw new Exception("请求次数已经达到阈值！！！");
                        }

                        // 每次请求完成后减少计数器的计数
                        latch.countDown();
                        System.out.println("latchCount: "+latch.getCount());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        // 等待最多10秒，直到计数器的计数归零
        latch.await(10, TimeUnit.SECONDS);

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        System.out.println("Total time taken for requests: " + elapsedTime + "ms");
        // 关闭线程池
        executorService.shutdown();
    }


}