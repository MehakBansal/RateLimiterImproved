package RateLimiter;

import java.util.HashMap;

public  class UserRateLimitMapper {
    static HashMap<String,LimitRate> userRateLimitMap=new HashMap<String,LimitRate>();


    public static void addUserLimit(String id, int limit,int rate){
        userRateLimitMap.put(id,new LimitRate(limit,rate));
    }
    public static LimitRate getUserLimit(String id){
        return userRateLimitMap.get(id);
    }

}
