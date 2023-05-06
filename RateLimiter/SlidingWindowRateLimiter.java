package RateLimiter;

import java.nio.file.AccessDeniedException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SlidingWindowRateLimiter implements RateLimiter{
    static Logger logger= Logger.getLogger(SlidingWindowRateLimiter.class.getName());
    Queue<Long> slidingWindow;
    int limit;
    int intervalWindow;


    SlidingWindowRateLimiter(){
        this.slidingWindow=new ConcurrentLinkedQueue<>();
    }

    @Override
    public boolean grantAccess(String userId) throws AccessDeniedException {
        LimitRate limitRateOfUser=UserRateLimitMapper.getUserLimit(userId);
        this.limit= limitRateOfUser.limit;
        this.intervalWindow=limitRateOfUser.rate;
        refreshSlidingQueue();
        if(slidingWindow.size()<limit){
            slidingWindow.add((System.currentTimeMillis()));
            logger.info("access granted");
            return true;
        }
        else{
            logger.log(Level.WARNING,"access denied...");
            throw new AccessDeniedException("access denied Exception");
        }
    }

    private void refreshSlidingQueue(){
        logger.info("Refreshing the Sliding Window");
        long currentTime= System.currentTimeMillis();
        if(slidingWindow.isEmpty()){
            return;
        }
        while((currentTime-slidingWindow.peek())/1000>=intervalWindow){
            long calculatedTime=(currentTime-slidingWindow.peek())/1000;
            slidingWindow.poll();
            if(slidingWindow.isEmpty()) break;
        }
        logger.info("Sliding Window Refreshed..");
    }
}
