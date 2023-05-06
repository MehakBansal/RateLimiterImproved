package RateLimiter;

import java.nio.file.AccessDeniedException;

public interface RateLimiter {
    boolean grantAccess(String userId) throws AccessDeniedException;
}
