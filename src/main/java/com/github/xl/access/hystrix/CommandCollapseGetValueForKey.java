package com.github.xl.access.hystrix;

import com.netflix.hystrix.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Liang Xu E-Mail: xuliang5@xiaomi.com Date: 2018/11/07 14:28
 *
 * <BatchReturnType>     The type returned from the {@link HystrixCommand} that will be invoked on batch executions.
 * <ResponseType>        The type returned from this command.
 * <RequestArgumentType> The type of the request argument. If multiple arguments are needed, wrap them in another object or a Tuple.
 */
public class CommandCollapseGetValueForKey extends HystrixCollapser<List<String>, String, Integer> {

    private final Integer key;

    public CommandCollapseGetValueForKey(Integer key) {
        super(Setter.withCollapserKey(HystrixCollapserKey.Factory.asKey("collapse"))
                .andScope(Scope.REQUEST)
                .andCollapserPropertiesDefaults(HystrixCollapserProperties.Setter()
                        .withMaxRequestsInBatch(100) // Integer.MAX_VALUE as default
                        .withTimerDelayInMilliseconds(20) // 10 as default
                        .withRequestCacheEnabled(true)
                ));
        this.key = key;
    }

    @Override
    public Integer getRequestArgument() {
        return key;
    }

    @Override
    protected HystrixCommand<List<String>> createCommand(Collection<CollapsedRequest<String, Integer>> collapsedRequests) {
        return new BatchCommand(collapsedRequests);
    }

    @Override
    protected void mapResponseToRequests(List<String> batchResponse, Collection<CollapsedRequest<String, Integer>> collapsedRequests) {
        int count = 0;
        for (CollapsedRequest<String, Integer> request : collapsedRequests) {
            request.setResponse(batchResponse.get(count++));
        }
    }

    private static final class BatchCommand extends HystrixCommand<List<String>> {

        private final Collection<CollapsedRequest<String, Integer>> requests;

        private BatchCommand(Collection<CollapsedRequest<String, Integer>> requests) {
            super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
                    .andCommandKey(HystrixCommandKey.Factory.asKey("GetValueForKey")));
            this.requests = requests;
        }

        @Override
        protected List<String> run() {
            ArrayList<String> response = new ArrayList<>();
            for (CollapsedRequest<String, Integer> request : requests) {
                // artificial response for each argument received in the batch
                response.add("ValueForKey: " + request.getArgument());
            }
            return response;
        }
    }
}
