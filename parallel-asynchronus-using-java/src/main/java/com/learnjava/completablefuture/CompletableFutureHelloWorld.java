package com.learnjava.completablefuture;

import com.learnjava.service.HelloWorldService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.learnjava.util.CommonUtil.delay;
import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;
import static com.learnjava.util.LoggerUtil.log;
import static java.util.stream.Collectors.joining;

public class CompletableFutureHelloWorld {

    private HelloWorldService hws;

    public CompletableFutureHelloWorld(HelloWorldService helloWorldService) {
        this.hws = helloWorldService;
    }

    public CompletableFuture<String> helloWorld() {
        return CompletableFuture.supplyAsync(() -> hws.helloWorld())   //runs this in a common fork-join pool
                .thenApply(String::toUpperCase);
    }

    public CompletableFuture<String> helloWorld_withSize() {
        return CompletableFuture.supplyAsync(() -> hws.helloWorld())   //runs this in a common fork-join pool
                .thenApply(String::toUpperCase)
                .thenApply((s) -> s.length() + " - " + s);
    }

    public String helloWorld_multiple_async_calls() {
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> hws.hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> hws.world());

        String hw = hello
                .thenCombine(world, (h, w) -> h + w) // (first,second)
                .thenApply(String::toUpperCase)
                .join();

        timeTaken();
        return hw;
    }


    public String helloWorld_3_async_calls() {
        startTimer();
        CompletableFuture<String> helloCF = CompletableFuture.supplyAsync(() -> this.hws.hello());
        CompletableFuture<String> worldCF = CompletableFuture.supplyAsync(() -> this.hws.world());
        CompletableFuture<String> hiCF = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " HI CompletableFuture!";
        });

        String hw = helloCF
                .thenCombine(worldCF, (h, w) -> h + w) // (first,second)
                .thenCombine(hiCF, (previous, current) -> previous + current)
                .thenApply(String::toUpperCase)
                .join();

        timeTaken();
        return hw;
    }

    public String helloWorld_3_async_calls_log() {
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> this.hws.hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> this.hws.world());
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " HI CompletableFuture!";
        });

        String hw = hello
                .thenCombine(world, (h, w) -> {
                    log("thenCombine h/w ");
                    return h + w;
                })
                .thenCombine(hiCompletableFuture, (previous, current) -> {
                    log("thenCombine , previous/current");
                    return previous + current;
                })
                .thenApply(s -> {
                    log("thenApply");
                    return s.toUpperCase();
                })
                .join();
        timeTaken();
        return hw;
    }

    public String helloWorld_3_async_calls_log_async() {
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> this.hws.hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> this.hws.world());
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " HI CompletableFuture!";
        });

        String hw = hello
                .thenCombineAsync(world, (h, w) -> {
                    log("thenCombine h/w ");
                    return h + w;
                })
                .thenCombineAsync(hiCompletableFuture, (previous, current) -> {
                    this.hws.hello();
                    log("thenCombine , previous/current");
                    return previous + current;
                })
                .thenApplyAsync(s -> {
                    this.hws.hello();
                    log("thenApply");
                    return s.toUpperCase();
                })
                .join();
        timeTaken();
        return hw;
    }


    public String helloWorld_3_async_calls_custom_threadPool() {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> this.hws.hello(), executorService);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> this.hws.world(), executorService);

        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " HI CompletableFuture!";
        }, executorService);

        String hw = hello
                .thenCombine(world, (h, w) -> {
                    log("thenCombine h/w ");
                    return h + w;
                })
                .thenCombine(hiCompletableFuture, (previous, current) -> {
                    log("thenCombine , previous/current");
                    return previous + current;
                })
                .thenApply(s -> {
                    log("thenApply");
                    return s.toUpperCase();
                })
                .join();

        timeTaken();
        return hw;
    }

    public String helloWorld_3_async_calls_custom_threadpool_async() {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> this.hws.hello(), executorService);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> this.hws.world(), executorService);

        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            // delay(1000);
            return " HI CompletableFuture!";
        }, executorService);

        String hw = hello
                .thenCombineAsync(world, (h, w) -> {
                    log("thenCombine h/w ");
                    return h + w;
                }, executorService)
                .thenCombineAsync(hiCompletableFuture, (previous, current) -> {
                    log("thenCombine , previous/current");
                    return previous + current;
                }, executorService)
                .thenApply(s -> {
                    log("thenApply");
                    return s.toUpperCase();
                })
                .join();

        timeTaken();
        return hw;
    }


    public String helloWorld_4_async_calls() {
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> this.hws.hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> this.hws.world());
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " HI CompletableFuture!";
        });
        CompletableFuture<String> byeCompletableFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Bye!";
        });

        String hw = hello
                .thenCombine(world, (h, w) -> h + w) // (first,second)
                .thenCombine(hiCompletableFuture, (previous, current) -> previous + current)
                .thenCombine(byeCompletableFuture, (previous, current) -> previous + current)
                .thenApply(String::toUpperCase)
                .join();

        timeTaken();
        return hw;
    }

    public CompletableFuture<String> helloWorld_thenCompose() {
        CompletableFuture<String> helloWorldFuture = CompletableFuture.supplyAsync(() -> this.hws.hello())
                .thenCompose(previous -> hws.worldFuture(previous))
                .thenApply(String::toUpperCase);

        return helloWorldFuture;

    }

    public String allOf() {
        startTimer();

        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return "Hello";
        });

        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
            delay(2000);
            return " World";
        });

        List<CompletableFuture<String>> cfList = List.of(cf1, cf2);
        CompletableFuture<Void> cfAllOf = CompletableFuture.allOf(cfList.toArray(new CompletableFuture[cfList.size()]));
        String result = cfAllOf.thenApply(v -> cfList.stream()
                .map(CompletableFuture::join)
                .collect(joining())).join();

        timeTaken();

        return result;

    }

    public String anyOf() {
        startTimer();

        CompletableFuture<String> db = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            log("response from db");
            return "Hello World";
        });

        CompletableFuture<String> restApi = CompletableFuture.supplyAsync(() -> {
            delay(2000);
            log("response from restApi");
            return "Hello World";
        });

        CompletableFuture<String> soapApi = CompletableFuture.supplyAsync(() -> {
            delay(3000);
            log("response from soapApi");
            return "Hello World";
        });

        List<CompletableFuture<String>> cfList = List.of(db, restApi, soapApi);
        CompletableFuture<Object> cfAllOf = CompletableFuture.anyOf(cfList.toArray(new CompletableFuture[cfList.size()]));
        String result = (String) cfAllOf.thenApply(v -> {
            if (v instanceof String) {
                return v;
            }
            return null;
        }).join();

        timeTaken();
        return result;
    }


    public String helloWorld_1() {
        return CompletableFuture.supplyAsync(() -> hws.helloWorld())  //runs this in a common fork-join pool
                .thenApply(String::toUpperCase)
                .join();
    }

    public CompletableFuture<String> complete(String input) {

        CompletableFuture<String> completableFuture = new CompletableFuture();
        completableFuture = completableFuture
                .thenApply(String::toUpperCase)
                .thenApply((result) -> result.length() + " - " + result);

        completableFuture.complete(input);

        return completableFuture;

    }

    public static void main(String[] args) {
        HelloWorldService helloWorldService = new HelloWorldService();
        CompletableFuture.supplyAsync(() -> helloWorldService.helloWorld()) //  runs this in a common fork-join pool
                .thenApply(String::toUpperCase)
                .thenAccept((result) -> {
                    log("result " + result);
                })
                .join();

        log("Done!");
        delay(2000);
    }
}