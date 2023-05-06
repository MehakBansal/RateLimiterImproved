package RateLimiter;

public class UserIdCreator implements IdCreator{
    String userId;

    UserIdCreator(int id){
        this.userId=String.valueOf(id);
    }
    @Override
    public String generateId() {
        return userId;
    }
}
